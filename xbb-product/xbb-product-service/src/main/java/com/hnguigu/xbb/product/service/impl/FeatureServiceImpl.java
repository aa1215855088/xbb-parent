package com.hnguigu.xbb.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.hnguigu.xbb.common.jedis.JedisClient;
import com.hnguigu.xbb.product.domain.Feature;
import com.hnguigu.xbb.product.mapper.FeatureMapper;
import com.hnguigu.xbb.product.service.FeatureService;
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
 * @create: 2018-11-05 19:27
 **/
@Service("featureService")
@Transactional
public class FeatureServiceImpl implements FeatureService {

    @Autowired
    private FeatureMapper featureMapper;

    @Autowired(required = false)
    @Qualifier("jedisClientPool")
    private JedisClient jedisClient;

    @Value("${PRODUCT_FEATURE}")
    private String PRODUCT_FEATURE;

    @Override
    public List<Feature> findAll() {
        List<Feature> featureList = null;
        if (jedisClient.exists(PRODUCT_FEATURE)) {
            String brandJsonStr = jedisClient.get(PRODUCT_FEATURE);
            featureList = JSON.parseObject(brandJsonStr, List.class);
            return featureList;
        }
        featureList = this.featureMapper.findAll();
        String brandJsonStr = JSON.toJSON(featureList).toString();
        jedisClient.set(PRODUCT_FEATURE, brandJsonStr);
        return featureList;
    }
}
