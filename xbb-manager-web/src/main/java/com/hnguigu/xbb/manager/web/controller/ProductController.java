package com.hnguigu.xbb.manager.web.controller;

import com.github.pagehelper.PageInfo;
import com.hnguigu.xbb.common.util.ObjectUtil;
import com.hnguigu.xbb.common.util.ResponseResult;
import com.hnguigu.xbb.common.vo.QueryProductVO;
import com.hnguigu.xbb.product.domain.*;
import com.hnguigu.xbb.product.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
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
 * @create: 2018-11-01 16:03
 **/
@Controller
@RequestMapping("/product")
public class ProductController {

    private static Logger log = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private FeatureService featureService;
    @Autowired
    private ColorService colorService;

    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(@RequestParam(value = "pageNum", defaultValue = "0") Integer pageNum,
                       @RequestParam(value = "pageSize", defaultValue = "8") Integer pageSize,
                       QueryProductVO queryProductVO, Model model) throws InvocationTargetException, IllegalAccessException {


        if (queryProductVO.getIsShow() == null) {
            queryProductVO.setIsShow(1);
            queryProductVO.setBrandId(0);
        }
        HashMap<String, Object> queryMap = ObjectUtil.convertToMap(queryProductVO);

        PageInfo<Product> pageInfo = this.productService.findAllByMap(queryMap, pageNum, pageSize);


        model.addAttribute("queryProductVO", queryProductVO);
        model.addAttribute("pageInfo", pageInfo);
        return "/product/list";
    }

    @GetMapping("add")
    public String add() {
        return "/product/add";
    }

    @PostMapping("add")
    @ResponseBody
    public ResponseResult add(Product product) {
        this.productService.add(product);
        return ResponseResult.success("添加商品成功!");
    }

    @PostMapping("checkName")
    @ResponseBody
    public ResponseResult checkName(String name) {
        //判断商品名称是否可用
        if (productService.checkName(name)) {
            return ResponseResult.fail("商品名称已存在,请重新输入");
        } else {
            return ResponseResult.success();
        }
    }

    @PostMapping("/del")
    @ResponseBody
    public ResponseResult del(@RequestParam("id") Integer[] ids) {

        this.productService.delProduct(ids);

        return ResponseResult.success("商品删除成功!");
    }

    @PostMapping("/itemUpShelf")
    @ResponseBody
    public ResponseResult itemUpShelf(@RequestParam("id") Integer[] ids) {

        this.productService.itemUpShelf(ids);

        return ResponseResult.success("商品上架成功!");
    }

    @PostMapping("/itemDownShelf")
    @ResponseBody
    public ResponseResult itemDownShelf(@RequestParam("id") Integer[] ids) {

        this.productService.itemDownShelf(ids);

        return ResponseResult.success("商品下架成功!");
    }


    @ModelAttribute("brands")
    public List<Brand> brands() {
        return this.brandService.findAll();
    }

    @ModelAttribute("types")
    public List<Type> types() {
        return this.typeService.findByShow();
    }

    @ModelAttribute("features")
    public List<Feature> features() {
        return this.featureService.findAll();
    }

    @ModelAttribute("colors")
    public List<Color> colors() {
        return this.colorService.findAll();
    }
}
