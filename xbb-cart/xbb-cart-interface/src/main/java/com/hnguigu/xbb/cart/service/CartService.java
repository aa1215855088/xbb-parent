package com.hnguigu.xbb.cart.service;

import com.hnguigu.xbb.product.dto.CartDTO;
import com.hnguigu.xbb.product.dto.cart.BuyerCart;

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
 * @create: 2018-12-03 11:41
 **/
public interface CartService {

    /**
     * 跟据skuId查询
     *
     * @param id
     * @return
     */
    CartDTO findBySkuId(Integer id);

    /**
     * 添加购物车
     *
     * @param oldCart 老购物车
     * @param num     数量
     * @param skuId   商品ID
     * @return
     */
    BuyerCart addCart(BuyerCart oldCart, Integer num, Integer skuId);

    /**
     * 保存购物车至redis中
     *
     * @param username
     * @param newCart
     */
    void saveCartListToRedis(String username, BuyerCart newCart);

    /**
     * 获取用户存储在redis中的购物车
     *
     * @param username
     * @return
     */
    BuyerCart findCartListFromRedis(String username);

    /**
     * 同步购物车
     *
     * @param username
     * @param buyerCart
     */
    void synchronousSoppingCart(String username, BuyerCart buyerCart);

    /**
     * 根据商品ID删除购物车
     *
     * @param id
     * @param userName
     */
    void delCartToRedis(Integer id, String userName);

    /**
     * 批量删除购物车
     * @param ids
     * @param userName
     */
    void delCartTORedisByIds(Integer[] ids, String userName);

    /**
     * 跟据SKUID查询购物车
     *
     * @param skuIds 商品最小单元ID
     * @return
     */
    BuyerCart getCartBySkuId(Integer[] skuIds, String username);
}
