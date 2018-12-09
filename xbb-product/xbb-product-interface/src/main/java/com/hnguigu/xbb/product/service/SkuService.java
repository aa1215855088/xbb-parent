package com.hnguigu.xbb.product.service;

import com.hnguigu.xbb.common.vo.UpdateSkuVO;
import com.hnguigu.xbb.product.domain.Sku;
import com.hnguigu.xbb.product.dto.SkuDTO;
import com.hnguigu.xbb.product.dto.UpdateSkuDTO;

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
 * @create: 2018-11-05 19:25
 **/
public interface SkuService {

    List<Sku> findAll();

    /**
     * 根据商品ID查询库存
     *
     * @param id
     * @return
     */
    List<SkuDTO> findByProductId(Integer id);

    void updateSku(UpdateSkuVO skuVO);

    /**
     * 修改库存
     * @param skuDTOS
     */
    void updateSkuStockInventory(List<UpdateSkuDTO> skuDTOS);
}
