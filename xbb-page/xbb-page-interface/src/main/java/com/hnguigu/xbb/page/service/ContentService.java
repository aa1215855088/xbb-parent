package com.hnguigu.xbb.page.service;

import com.hnguigu.xbb.product.domain.Product;
import com.hnguigu.xbb.product.domain.Sku;

import java.util.List;

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
 * @create: 2018-11-12 22:09
 **/
public interface ContentService {

    /**
     * 根据商品ID查询商品库存大于0的商品
     *
     * @param id
     * @return
     */
    List<Sku> findSkuByProductIdAndInventoryNotNull(Integer id);


    /**
     * 根据商品ID查询商品
     *
     * @param productId
     * @return 商品
     */
    Product findProductByProductId(Integer productId);
}
