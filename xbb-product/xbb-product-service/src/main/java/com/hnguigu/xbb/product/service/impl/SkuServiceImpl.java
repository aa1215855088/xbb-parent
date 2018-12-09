package com.hnguigu.xbb.product.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.hnguigu.xbb.common.exception.XbbException;
import com.hnguigu.xbb.common.vo.UpdateSkuVO;
import com.hnguigu.xbb.product.domain.Color;
import com.hnguigu.xbb.product.domain.Sku;
import com.hnguigu.xbb.product.dto.SkuDTO;
import com.hnguigu.xbb.product.dto.UpdateSkuDTO;
import com.hnguigu.xbb.product.mapper.SkuMapper;
import com.hnguigu.xbb.product.service.SkuService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
 * @create: 2018-11-05 19:29
 **/
@Service("skuService")
@Transactional
public class SkuServiceImpl implements SkuService {
    @Autowired
    private SkuMapper skuMapper;

    @Override
    public List<Sku> findAll() {
        return this.skuMapper.findAll();
    }

    @Override
    public List<SkuDTO> findByProductId(Integer id) {
        List<SkuDTO> skuDTOS = new ArrayList<>();
        List<Sku> skuList = this.skuMapper.findByProductId(id);
        for (Sku sku : skuList) {
            Color color = new Color();
            color.setId(sku.getColor().getId());
            color.setName(sku.getColor().getName());
            SkuDTO skuDTO = new SkuDTO();
            skuDTO.setId(sku.getId());
            skuDTO.setDeliveFee(sku.getDeliveFee());
            skuDTO.setMarketPrice(sku.getMarketPrice());
            skuDTO.setSkuPrice(sku.getSkuPrice());
            skuDTO.setSkuUpperLimit(sku.getSkuUpperLimit());
            skuDTO.setStockInventory(sku.getStockInventory());
            skuDTO.setColor(color);
            skuDTO.setProductNo(sku.getProduct().getNo());
            skuDTO.setSize(sku.getSize());
            skuDTOS.add(skuDTO);
        }
        return skuDTOS;
    }

    @Override
    public void updateSku(UpdateSkuVO skuVO) {
        Sku sku = new Sku();
        sku.setId(skuVO.getId());
        sku.setMarketPrice(skuVO.getMarketPrice());
        sku.setStockInventory(skuVO.getStockInventory());
        sku.setSkuPrice(skuVO.getSkuPrice());
        sku.setSkuUpperLimit(skuVO.getSkuUpperLimit());
        sku.setDeliveFee(skuVO.getDeliveFee());
        if (this.skuMapper.updateById(sku) != 1) {
            throw new XbbException("修改失败");
        }

    }

    @Override
    public void updateSkuStockInventory(List<UpdateSkuDTO> skuDTOS) {
        for (UpdateSkuDTO skuDTO : skuDTOS) {
            this.skuMapper.InventoryReduction(skuDTO.getSkuId(),skuDTO.getNum());
        }
    }
}
