package com.hnguigu.xbb.cart.service.impl;

import cn.hutool.db.nosql.redis.RedisDS;
import com.alibaba.fastjson.JSON;
import com.hnguigu.xbb.cart.service.CartService;
import com.hnguigu.xbb.common.exception.XbbException;
import com.hnguigu.xbb.product.domain.Sku;
import com.hnguigu.xbb.product.dto.CartDTO;
import com.hnguigu.xbb.product.dto.cart.BuyerCart;
import com.hnguigu.xbb.product.dto.cart.BuyerItem;
import com.hnguigu.xbb.product.mapper.SkuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

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
 * @create: 2018-12-03 11:42
 **/
@Service("cartService")
@Transactional
public class CartServiceImpl implements CartService {

    @Autowired
    private SkuMapper skuMapper;

    @Override
    public CartDTO findBySkuId(Integer id) {
        return this.skuMapper.findBySkuId(id);
    }

    @Override
    public BuyerCart addCart(BuyerCart oldCart, Integer num, Integer skuId) {
        if (oldCart == null) {
            oldCart = new BuyerCart();
        }
        CartDTO cartDTO = this.skuMapper.findBySkuId(skuId);
        if (cartDTO == null) {
            throw new XbbException("添加购物车:商品不存在");
        }
        BuyerItem buyerItem = new BuyerItem();
        buyerItem.setCartDTO(cartDTO);
        if (num != null && num != 0) {
            buyerItem.setAmount(num);
        }
        oldCart.addProduct(buyerItem);
        return oldCart;
    }

    @Override
    public void saveCartListToRedis(String username, BuyerCart newCart) {
        Jedis jedis = RedisDS.create().getJedis();
        jedis.set("CART:" + username, JSON.toJSONString(newCart));
    }

    @Override
    public BuyerCart findCartListFromRedis(String username) {
        Jedis jedis = RedisDS.create().getJedis();
        return JSON.parseObject(jedis.get("CART:" + username), BuyerCart.class);
    }

    @Override
    public void synchronousSoppingCart(String username, BuyerCart buyerCart) {
        BuyerCart oldBuyerCart = findCartListFromRedis(username);
        if (oldBuyerCart == null) {
            oldBuyerCart = new BuyerCart();
        }
        for (BuyerItem item : buyerCart.getItems()) {
            if (null != item) {
                oldBuyerCart.addProduct(item);
            }
        }
        saveCartListToRedis(username, oldBuyerCart);
    }

    @Override
    public void delCartToRedis(Integer id, String userName) {
        BuyerCart buyerCart = this.findCartListFromRedis(userName);

        Iterator<BuyerItem> redisItems = buyerCart.getItems().iterator();
        while (redisItems.hasNext()) {
            Integer oldId = redisItems.next().getCartDTO().getId();
            if (oldId.equals(id)) {
                redisItems.remove();
            }
        }
        this.saveCartListToRedis(userName, buyerCart);
    }

    @Override
    public void delCartTORedisByIds(Integer[] ids, String userName) {
        BuyerCart buyerCart = this.findCartListFromRedis(userName);

        Iterator<BuyerItem> redisItems = buyerCart.getItems().iterator();
        while (redisItems.hasNext()) {
            Integer oldId = redisItems.next().getCartDTO().getId();
            for (Integer id : ids) {
                if (oldId.equals(id)) {
                    redisItems.remove();
                }
            }

        }
        this.saveCartListToRedis(userName, buyerCart);
    }

    @Override
    public BuyerCart getCartBySkuId(Integer[] skuIds, String username) {
        BuyerCart buyerCart = this.findCartListFromRedis(username);
        BuyerCart getCart = new BuyerCart();
        for (BuyerItem item : buyerCart.getItems()) {
            if (item == null) {
                continue;
            }
            for (Integer skuId : skuIds) {
                if (item.getCartDTO().getId().equals(skuId)) {
                    getCart.addProduct(item);
                }
            }
        }
        return getCart;
    }

}
