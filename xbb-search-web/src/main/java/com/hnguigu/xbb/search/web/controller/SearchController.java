package com.hnguigu.xbb.search.web.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.hnguigu.xbb.common.vo.SearchVO;
import com.hnguigu.xbb.product.domain.Brand;
import com.hnguigu.xbb.product.domain.Feature;
import com.hnguigu.xbb.product.dto.ItemSearchDTO;
import com.hnguigu.xbb.product.dto.SearchListDTO;
import com.hnguigu.xbb.search.service.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
 * @create: 2018-11-25 12:31
 **/
@Controller
public class SearchController {

    private static Logger log = LoggerFactory.getLogger(SearchController.class);

    @Autowired
    private SearchService searchService;

    @GetMapping("/search")
    public String search(SearchVO searchVO, Model model) throws UnsupportedEncodingException {
        if (StrUtil.isBlank(searchVO.getKeywords())) {
            model.addAttribute("message", "没有查到“”关键字的商品");
            return "search";
        }
        try {
            SearchListDTO search = this.searchService.search(searchVO);
            model.addAttribute("searchTerm",searchVO);
            model.addAttribute("searchResultSet", search);
        } catch (Exception e) {
            log.error(e.getMessage());
            model.addAttribute("message", "系统异常请稍后重试");
            return "/error/exception";
        }

        return "search";
    }
}

