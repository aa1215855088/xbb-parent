package com.hnguigu.xbb.order.service;

import com.hnguigu.xbb.common.vo.OrderVO;
import com.hnguigu.xbb.order.domain.Order;
import com.hnguigu.xbb.product.dto.cart.BuyerCart;
import com.hnguigu.xbb.user.domian.User;

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
 * @create: 2018-12-06 17:18
 **/
public interface OrderService {


    /**
     * 创建订单
     *
     * @param orderVO
     * @param user
     * @param buyerCart
     * @return
     */
    Order createOrder(OrderVO orderVO, User user, BuyerCart buyerCart);

    /**
     * 根据订单ID获取订单
     *
     * @param id
     * @param userName
     * @return
     */
    Order getOrderById(Long id, String userName);

    /**
     * 根据订单ID查询代付款的订单
     *
     * @param id
     * @param username
     * @return
     */
    Order getOrderByIdAndStateIsSubmitOrder(Long id, String username);
}
