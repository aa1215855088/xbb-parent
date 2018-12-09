package com.hnguigu.xbb.page.service.impl;

import com.hnguigu.xbb.page.service.ContentService;
import com.hnguigu.xbb.product.domain.Product;
import com.hnguigu.xbb.product.domain.Sku;
import com.hnguigu.xbb.product.mapper.ProductMapper;
import com.hnguigu.xbb.product.mapper.SkuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
 * @create: 2018-11-12 22:13
 **/
@Service("contentService")
public class ContentServiceImpl implements ContentService {

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Sku> findSkuByProductIdAndInventoryNotNull(Integer id) {
        return this.skuMapper.findSkuByProductIdAndInventoryNotNull(id);
    }

    @Override
    public Product findProductByProductId(Integer productId) {

        return this.productMapper.selectById(productId);
    }
}
