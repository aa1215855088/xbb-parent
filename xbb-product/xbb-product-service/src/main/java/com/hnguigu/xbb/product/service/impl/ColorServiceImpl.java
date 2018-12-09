package com.hnguigu.xbb.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.hnguigu.xbb.common.jedis.JedisClient;
import com.hnguigu.xbb.product.domain.Brand;
import com.hnguigu.xbb.product.domain.Color;
import com.hnguigu.xbb.product.mapper.ColorMapper;
import com.hnguigu.xbb.product.service.ColorService;
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
 * @create: 2018-11-05 19:26
 **/
@Service("colorService")
@Transactional

public class ColorServiceImpl implements ColorService {
    @Autowired
    private ColorMapper colorMapper;

    @Autowired(required = false)
    @Qualifier("jedisClientPool")
    private JedisClient jedisClient;

    @Value("${PRODUCT_COLOR}")
    private String PRODUCT_COLOR;

    @Override
    public String find(Color value) {
        return null;
    }

    @Override
    public List<Color> findAll() {
        List<Color> colorList = null;
        if (jedisClient.exists(PRODUCT_COLOR)) {
            String brandJsonStr = jedisClient.get(PRODUCT_COLOR);
            colorList = JSON.parseObject(brandJsonStr, List.class);
            return colorList;
        }
        colorList = this.colorMapper.findAll();
        String brandJsonStr = JSON.toJSON(colorList).toString();
        jedisClient.set(PRODUCT_COLOR, brandJsonStr);
        return colorList;
    }

    @Override
    public String findOne(Integer id) {
        return null;
    }

    @Override
    public String saveNotNull(Color value) {
        return null;
    }

    @Override
    public String updateNotNullById(Color enti) {
        return null;
    }

    @Override
    public String deleteById(Integer id) {
        return null;
    }
}
