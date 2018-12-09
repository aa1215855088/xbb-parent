package com.hnguigu.xbb.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.hnguigu.xbb.common.jedis.JedisClient;
import com.hnguigu.xbb.product.domain.Brand;
import com.hnguigu.xbb.product.mapper.BrandMapper;
import com.hnguigu.xbb.product.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
 * @create: 2018-11-05 19:03
 **/
@Service("brandServie")
@Transactional
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandMapper brandMapper;
    @Autowired(required = false)
    @Qualifier("jedisClientPool")
    private JedisClient jedisClient;

    @Value("${PRODUCT_BRAND}")
    private String PRODUCT_BRAND;

    @Override
    public List<Brand> findAll() {
        List<Brand> brandList = null;
        if (jedisClient.exists(PRODUCT_BRAND)) {
            String brandJsonStr = jedisClient.get(PRODUCT_BRAND);
            brandList = JSON.parseObject(brandJsonStr, List.class);
            return brandList;
        }
        brandList = this.brandMapper.findAll();
        String brandJsonStr = JSON.toJSON(brandList).toString();
        jedisClient.set(PRODUCT_BRAND, brandJsonStr);
        return brandList;
    }

}
