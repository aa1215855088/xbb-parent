package com.hnguigu.xbb.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hnguigu.xbb.common.constants.SkuConstants;
import com.hnguigu.xbb.common.exception.XbbException;
import com.hnguigu.xbb.common.jedis.JedisClient;
import com.hnguigu.xbb.common.util.GeneratingOrderUtil;
import com.hnguigu.xbb.product.domain.Sku;
import com.hnguigu.xbb.common.vo.QueryProductVO;
import com.hnguigu.xbb.product.domain.Product;
import com.hnguigu.xbb.product.mapper.ProductMapper;
import com.hnguigu.xbb.product.mapper.SkuMapper;
import com.hnguigu.xbb.product.service.ProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
 * @create: 2018-11-01 16:34
 **/
@Service("productService")
@Transactional
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private SkuMapper skuMapper;

    @Autowired(required = false)
    @Qualifier("jedisClientPool")
    private JedisClient jedisClient;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Value("${PRODUCT_ITEM}")
    public String PRODUCT_ITEM;

    @Override
    public List<Product> selectPage(Integer pageNum, Integer pageSize, QueryProductVO product) {
        return this.productMapper.selectPage(new Page<Product>(pageNum, pageSize), new EntityWrapper<Product>()
                .like(StringUtils.isNotBlank(product.getName()), "name", product.getName())
                .eq(product.getBrandId() != null, "type_id", product.getBrandId())
                .eq(product.getIsShow() != null, "is_show", product.getIsShow()));
    }

    @Override
    public List<Product> findAll() {
        return this.productMapper.findAll();
    }

    @Override
    public List<Product> selectByMap(Map<String, Object> map) {
        return this.productMapper.selectByMap(map);
    }

    @Override
    public PageInfo<Product> findAllByMap(Map<String, Object> map, Integer pageNum, Integer pageSize) {
        //mybatis分页插件
        PageHelper.startPage(pageNum, pageSize);
        List<Product> productList = this.productMapper.findAllByMap(map);
        PageInfo<Product> pageInfo = new PageInfo<>(productList);
        return pageInfo;
    }

    @Override
    public void add(Product product) {

        product.setNo(GeneratingOrderUtil.getDate());

        if (product.getIsCommend() == null) {
            product.setIsCommend(0);
        }
        if (product.getIsHot() == null) {
            product.setIsHot(0);
        }
        if (product.getIsNew() == null) {
            product.setIsNew(0);
        }
        //添加商品
        if (this.productMapper.save(product) != 1) {
            throw new XbbException("添加商品失败!");
        }
        //初始化库存表
        String[] colors = product.getColor().split(",");
        String[] sizes = product.getSize().split(",");
        for (String color : colors) {
            if (StringUtils.isNotBlank(color)) {
                for (String size : sizes) {
                    if (StringUtils.isNotBlank(size)) {
                        Sku sku = new Sku();
                        sku.setSize(size);
                        sku.setColorId(Integer.valueOf(color));
                        sku.setProductId(product.getId());
                        sku.setMarketPrice(SkuConstants.MARKET_PRICE);
                        sku.setSkuPrice(SkuConstants.SKU_PRICE);
                        sku.setDeliveFee(SkuConstants.DELIVE_FEE);
                        sku.setCreateTime(new Date());
                        sku.setSkuUpperLimit(SkuConstants.SKU_UPPER_LIMIT);
                        sku.setStockInventory(SkuConstants.STOCK_INVENTORY);
                        this.skuMapper.save(sku);
                    }
                }
            }
        }
    }

    @Override
    public boolean checkName(String name) {
        List<Product> products = this.productMapper.selectList(new EntityWrapper<Product>().
                eq("name", name));

        if (products.size() == 0) {
            return false;
        }
        return true;
    }

    @Override
    public Product findProductById(Integer productId) {
        return this.productMapper.selectById(productId);
    }

    @Override
    public void itemUpShelf(Integer[] productIds) {
        for (Integer id : productIds) {
            if (null != id) {
                Product product = new Product();
                product.setId(id);
                product.setIsShow(1);
                product.setCheckTime(new Date());
                if (this.productMapper.updateById(product) != 1) {
                    throw new XbbException("商品上架失败!");
                }
            }
        }

        //发送消息生成静态页面
        sendStaticPageMessage(productIds);
        //发送消息同步索引库
        sendAddIndexMessage(productIds);
    }

    @Override
    public void itemDownShelf(Integer[] productIds) {
        for (Integer id : productIds) {
            if (null != id) {
                Product product = new Product();
                product.setId(id);
                //1:上架   0:下架
                product.setIsShow(0);
                product.setCheckTime(new Date());
                if (this.productMapper.updateById(product) != 1) {
                    throw new XbbException("商品下架失败!");
                }
            }

        }
        sendDelIndexMessage(productIds);
        sendDelStaticPageMessage(productIds);
    }

    @Override
    public void delProduct(Integer[] productIds) {
        for (Integer id : productIds) {
            if (null != id) {
                Product product = new Product();
                product.setId(id);
                //0:删除
                product.setIsDel(0);
                product.setCheckTime(new Date());
                if (this.productMapper.updateById(product) != 1) {
                    throw new XbbException("商品删除失败!");
                }
            }
        }
        sendDelIndexMessage(productIds);
        sendDelStaticPageMessage(productIds);
    }


    /**
     * 同步商品详情缓存
     *
     * @param id
     */
    private void deleteProductDetRedis(Integer id) {
        try {
            jedisClient.del(PRODUCT_ITEM + ":" + id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送消息生成静态页面
     *
     * @param ids
     */
    private void sendStaticPageMessage(Integer[] ids) {
        jmsTemplate.send("STATIC_PAGE_MESSAGE", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(JSON.toJSONString(ids));
            }
        });
    }

    /**
     * 发送消息生成静态页面
     *
     * @param ids
     */
    private void sendDelStaticPageMessage(Integer[] ids) {
        jmsTemplate.send("DEL_PAGE_MESSAGE", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(JSON.toJSONString(ids));
            }
        });
    }

    /**
     * 发送消息同步索引库
     *
     * @param ids
     */
    private void sendAddIndexMessage(Integer[] ids) {
        jmsTemplate.send("ADD_INDEX_MESSAGE", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(JSON.toJSONString(ids));
            }
        });
    }

    /**
     * 发送消息删除索引库
     *
     * @param ids
     */
    private void sendDelIndexMessage(Integer[] ids) {
        jmsTemplate.send("DEL_INDEX_MESSAGE", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(JSON.toJSONString(ids));
            }
        });
    }
}
