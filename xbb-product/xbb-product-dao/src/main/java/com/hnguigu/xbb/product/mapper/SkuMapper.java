package com.hnguigu.xbb.product.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hnguigu.xbb.product.domain.Sku;
import com.hnguigu.xbb.product.dto.CartDTO;

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
 * @create: 2018-11-05 19:22
 **/
public interface SkuMapper extends BaseMapper<Sku> {

    List<Sku> findAll();

    /**
     * 保存商品
     *
     * @param sku
     * @return
     */
    int save(Sku sku);

    List<Sku> findByProductId(Integer id);


    /**
     * 根据商品ID查询商品库存大于0的商品
     *
     * @param id
     * @return
     */
    List<Sku> findSkuByProductIdAndInventoryNotNull(Integer id);

    CartDTO findBySkuId(Integer id);

    /**
     * 减库存
     * @param skuId
     * @param num
     */
    void InventoryReduction(Integer skuId, Integer num);
}
