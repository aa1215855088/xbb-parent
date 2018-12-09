package com.hnguigu.xbb.search.service;

import com.hnguigu.xbb.common.vo.SearchVO;
import com.hnguigu.xbb.product.dto.ItemSearchDTO;
import com.hnguigu.xbb.product.dto.SearchListDTO;
import org.springframework.data.solr.core.query.result.ScoredPage;

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
 * @create: 2018-11-25 12:41
 **/
public interface SearchService {

    /**
     * 关键字搜索
     *
     * @param searchVO
     * @return
     */
   SearchListDTO search(SearchVO searchVO);


    /**
     * 同步索引
     *
     * @param ids
     */
    void addItems(Integer[] ids);

    /**
     * 删除索引
     *
     * @param ids
     */
    void delItems(Integer[] ids);


}
