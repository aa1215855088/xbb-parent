package com.hnguigu.xbb.order.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.db.nosql.redis.RedisDS;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hnguigu.xbb.cart.service.CartService;
import com.hnguigu.xbb.common.constants.OrderStatusConstants;
import com.hnguigu.xbb.common.constants.PayStatusConstants;
import com.hnguigu.xbb.common.exception.XbbException;
import com.hnguigu.xbb.common.vo.OrderVO;
import com.hnguigu.xbb.order.domain.Detail;
import com.hnguigu.xbb.order.domain.Order;
import com.hnguigu.xbb.order.mapper.OrderMapper;
import com.hnguigu.xbb.order.service.DetailService;
import com.hnguigu.xbb.order.service.OrderService;
import com.hnguigu.xbb.product.dto.UpdateSkuDTO;
import com.hnguigu.xbb.product.dto.cart.BuyerCart;
import com.hnguigu.xbb.product.dto.cart.BuyerItem;
import com.hnguigu.xbb.product.service.SkuService;
import com.hnguigu.xbb.user.domian.User;
import com.sun.xml.internal.bind.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import sun.dc.pr.PRError;

import java.time.LocalDate;
import java.util.*;

/**
 * ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　┳┛　┗┳　┃
 * ┃　　　　　　　┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 * 　　┃　　　┃神兽保佑
 * 　　┃　　　┃代码无BUG！
 * 　　┃　　　┗━━━┓
 * 　　┃　　　　　　　┣┓
 * 　　┃　　　　　　　┏┛
 * 　　┗┓┓┏━┳┓┏┛
 * 　　　┃┫┫　┃┫┫
 * 　　　┗┻┛　┗┻┛
 *
 * @program: xbb-parent
 * @description:
 * @author: 徐子楼
 * @create: 2018-12-07 00:12
 **/
@Service("orderService")
public class OrderServiceImpl implements OrderService {

    @Value("${ORDER_ID_INIT}")
    private String ORDER_ID_INIT;
    @Value("${ORDER_ID}")
    private String ORDER_ID;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private DetailService detailService;
    @Autowired
    private CartService cartService;
    @Autowired
    private SkuService skuService;

    @Override
    public Order createOrder(OrderVO orderVO, User user, BuyerCart buyerCart) {
        if (orderVO == null) {
            throw new XbbException("orderVO为空");
        }
        if (user == null) {
            throw new XbbException("user为空");
        }
        if (buyerCart == null) {
            throw new XbbException("buyerCart为空");
        }
        Jedis jedis = RedisDS.create().getJedis();
        List<UpdateSkuDTO> skuDTOS = new ArrayList<>();
        jedis.set(ORDER_ID, ORDER_ID_INIT);
        //1:生成订单
        Order order = new Order();
        BeanUtil.copyProperties(orderVO, order);
        //redis单线程 生成的订单ID放心用
        Long orderId = jedis.incr(ORDER_ID);
        order.setId(orderId);
        order.setCreateDate(new DateTime());
        order.setTotalPrice(buyerCart.getTotalPrice());
        order.setIsPaiy(PayStatusConstants.CASH_ON_DELIVERY);
        order.setBuyerId(user.getUsername());
        order.setOrderState(OrderStatusConstants.SUBMIT_ORDER);
        //2 生成订单明细
        for (BuyerItem item : buyerCart.getItems()) {
            UpdateSkuDTO updateSkuDTO = new UpdateSkuDTO();
            updateSkuDTO.setSkuId(item.getCartDTO().getId());
            updateSkuDTO.setNum(item.getAmount());
            skuDTOS.add(updateSkuDTO);
            Detail detail = new Detail();
            detail.setOrderId(orderId);
            detail.setAmount(item.getAmount());
            detail.setProductId(item.getCartDTO().getId());
            detail.setPrice(item.getCartDTO().getPrice());
            detail.setSize(item.getCartDTO().getSize());
            detail.setProductName(item.getCartDTO().getName());
            detail.setColor(item.getCartDTO().getColor());
            this.detailService.save(detail);
        }
        //3:库存同步
        this.skuService.updateSkuStockInventory(skuDTOS);
        //4:购物车同步
        for (UpdateSkuDTO skuDTO : skuDTOS) {
            this.cartService.delCartToRedis(skuDTO.getSkuId(), user.getUsername());
        }
        return order;
    }

    @Override
    public Order getOrderById(Long id, String userName) {
        List<Order> orders = this.orderMapper.selectList(new EntityWrapper<Order>()
                .eq("id", id)
                .eq("buyer_id", userName));
        return orders.get(0);
    }

    @Override
    public Order getOrderByIdAndStateIsSubmitOrder(Long id, String username) {
        List<Order> orders = this.orderMapper.selectList(new EntityWrapper<Order>()
                .eq("id", id)
                .eq("buyer_id", username)
                .eq("state", OrderStatusConstants.SUBMIT_ORDER));
        return orders.get(0);
    }
}
