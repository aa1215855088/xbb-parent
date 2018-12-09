package com.hnguigu.xbb.content.service.impl;

import cn.hutool.db.nosql.redis.RedisDS;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.hnguigu.xbb.product.domain.Product;
import com.hnguigu.xbb.product.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

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
 * @create: 2018-11-22 19:44
 **/
@Service(timeout = 2000)
@Transactional
public class ContentServiceImpl {
    @Autowired
    private ProductMapper productMapper;

    @Value("${HOME_HOT_PRODUCT}")
    public String HOME_HOT_PRODUCT;

    public List<Product> getHomeHotProduct() {
        Jedis jedis = RedisDS.create().getJedis();
        if (jedis.exists(HOME_HOT_PRODUCT)) {
            String homeHotProductJsonStr = jedis.get(HOME_HOT_PRODUCT);
            return JSON.parseObject(homeHotProductJsonStr, List.class);
        }
        List<Product> productHotList = this.productMapper.getHotProduct();
        jedis.set(HOME_HOT_PRODUCT, JSON.toJSONString(productHotList));
        return productHotList;
    }
}
