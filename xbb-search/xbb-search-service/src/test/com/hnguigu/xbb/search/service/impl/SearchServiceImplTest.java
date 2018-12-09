package com.hnguigu.xbb.search.service.impl;

import com.hnguigu.xbb.product.dto.ItemSearchDTO;
import com.hnguigu.xbb.product.mapper.ProductMapper;
import com.hnguigu.xbb.search.service.SearchService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.management.Query;
import java.lang.ref.PhantomReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

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
 * @create: 2018-11-25 19:57
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/META-INF/spring/*.xml")
public class SearchServiceImplTest {
    @Autowired
    private SearchService searchService;
    @Autowired
    private SolrTemplate solrTemplate;
    @Autowired
    private ProductMapper productMapper;

    @Test
    public void search() {
        List<ItemSearchDTO> itemSearchAll = this.productMapper.findItemSearchAll();
        this.solrTemplate.saveBeans(itemSearchAll);
        this.solrTemplate.commit();
    }
}
