package com.hnguigu.xbb.search.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.hnguigu.xbb.common.vo.SearchVO;
import com.hnguigu.xbb.product.domain.Brand;
import com.hnguigu.xbb.product.domain.Feature;
import com.hnguigu.xbb.product.domain.Product;
import com.hnguigu.xbb.product.domain.Type;
import com.hnguigu.xbb.product.dto.ItemSearchDTO;
import com.hnguigu.xbb.product.dto.SearchListDTO;
import com.hnguigu.xbb.product.mapper.ProductMapper;
import com.hnguigu.xbb.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.dc.pr.PRError;

import java.util.*;

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
 * @create: 2018-11-25 12:41
 **/
@Service("searchService")
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SolrTemplate solrTemplate;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public SearchListDTO search(SearchVO searchVO) {
        return initSearchTerm(searchList(searchVO));
    }

    /**
     * 查询列表
     * @param searchVO
     * @return
     */
    private SearchListDTO searchList(SearchVO  searchVO) {
        SearchListDTO searchListDTO = new SearchListDTO();
        //替换关键字中的空格
        String keywords = searchVO.getKeywords().replace(" ","");
        HighlightQuery query = new SimpleHighlightQuery();
        //高亮域
        HighlightOptions highlightOptions = new HighlightOptions().addField("xbb_name");
        highlightOptions.setSimplePrefix("<em style='color:red'>");
        highlightOptions.setSimplePostfix("</em>");
        query.setHighlightOptions(highlightOptions);

        //1:关键字查询
        query.addCriteria(new Criteria("xbb_keywords").is(keywords));

        //2:按商品分类过滤
        if (StrUtil.isNotBlank(searchVO.getType())) {
            FilterQuery filterQuery = new SimpleFilterQuery(new Criteria("xbb_type").is(searchVO.getType()));
            query.addFilterQuery(filterQuery);
        }
        //3:按商品品牌过滤
        if(StrUtil.isNotBlank(searchVO.getBrand())){
            FilterQuery filterQuery = new SimpleFilterQuery(new Criteria("xbb_brand").is(searchVO.getBrand()));
            query.addFilterQuery(filterQuery);
        }
        //4:按商品材质过滤
        if(StrUtil.isNotBlank(searchVO.getFeature())){
            FilterQuery filterQuery = new SimpleFilterQuery(new Criteria("xbb_feature").is(searchVO.getFeature()));
            query.addFilterQuery(filterQuery);
        }
        //5:按价格过滤
        if(StrUtil.isNotBlank(searchVO.getPrice())){
            String[]  price = searchVO.getPrice().split("-");
            //如果最低价格不等于0
            if(!price[0].equals("0")){
                FilterQuery filterQuery=new SimpleFilterQuery();
                Criteria filterCriteria=new Criteria("xbb_price").greaterThanEqual(Double.valueOf(price[0]));
                filterQuery.addCriteria(filterCriteria);
                query.addFilterQuery(filterQuery);
            }
            //如果最高价格不等于*
            if(!price[1].equals("*")){
                FilterQuery filterQuery=new SimpleFilterQuery();
                Criteria filterCriteria=new Criteria("xbb_price").lessThanEqual(Double.valueOf(price[1]));
                filterQuery.addCriteria(filterCriteria);
                query.addFilterQuery(filterQuery);
            }
        }
        //6:排序
        if(StrUtil.isNotBlank(searchVO.getSort()) && StrUtil.isNotBlank(searchVO.getSortField())){
            if(searchVO.getSort().equals("ASC")){
                Sort sort=new Sort(Sort.Direction.ASC,searchVO.getSortField());
                query.addSort(sort);
            }
            if(searchVO.getSort().equals("DESC")){
                query.addSort(new Sort(Sort.Direction.DESC,searchVO.getSortField()));
            }
        }

        //7:分页
        if(null==searchVO.getPageNo()){
            searchVO.setPageNo(0);
        }
        if(null==searchVO.getPageSize()){
            searchVO.setPageSize(10);
        }
        query.setPageRequest(new PageRequest(searchVO.getPageNo(),searchVO.getPageSize()));

        //***********  获取高亮结果集  ***********
        //高亮页对象
        HighlightPage<ItemSearchDTO> page = solrTemplate.queryForHighlightPage(query, ItemSearchDTO.class);
        //高亮入口集合(每条记录的高亮入口)
        List<HighlightEntry<ItemSearchDTO>> entryList = page.getHighlighted();
        for (HighlightEntry<ItemSearchDTO> entry : entryList) {
            List<HighlightEntry.Highlight> highlights = entry.getHighlights();
            if (highlights.size() > 0 && highlights.get(0).getSnipplets().size() > 0) {
                ItemSearchDTO item = entry.getEntity();
                item.setName(highlights.get(0).getSnipplets().get(0));
            }
        }
        searchListDTO.setRows(page.getContent());
        searchListDTO.setTotal(page.getTotalElements());
        searchListDTO.setTotalPages(page.getTotalPages());
        return searchListDTO;
    }

    /**
     * 初始化搜索条件
     * @param searchList
     * @return
     */
    private SearchListDTO initSearchTerm(SearchListDTO searchList) {
        Set<Brand> brands=new HashSet<>();
        Set<Type> types=new HashSet<>();
        Set<Feature> features=new HashSet<>();
        for (ItemSearchDTO row : searchList.getRows()) {
            Feature feature=new Feature();
            Brand brand=new Brand();
            Type type=new Type();
            feature.setName(row.getFeature());
            brand.setName(row.getBrand());
            type.setName(row.getType());
            brands.add(brand);
            types.add(type);
            features.add(feature);
        }
        searchList.setBrands(brands);
        searchList.setFeatures(features);
        searchList.setTypes(types);
        return searchList;
    }


    @Override
    public void addItems(Integer[] ids) {
        List<ItemSearchDTO> items = new ArrayList<>();
        for (Integer id : ids) {
            if (null != id) {
                ItemSearchDTO item = this.productMapper.findItemSearchById(id);
                items.add(item);
            }
        }
        if (CollUtil.isNotEmpty(items)) {
            this.solrTemplate.saveBeans(items);
            this.solrTemplate.commit();
        }
    }

    @Override
    public void delItems(Integer[] ids) {
        List<String> idList = new ArrayList<>();
        for (Integer id : ids) {
            if (null != id) {
                idList.add(String.valueOf(id));
            }
        }


        if (CollUtil.isNotEmpty(idList)) {
            this.solrTemplate.deleteById(idList);
            this.solrTemplate.commit();
        }

    }
}
