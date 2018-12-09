package com.hnguigu.xbb.manager.web.controller;

import com.hnguigu.xbb.common.util.ResponseResult;
import com.hnguigu.xbb.common.vo.UpdateSkuVO;
import com.hnguigu.xbb.product.dto.SkuDTO;
import com.hnguigu.xbb.product.service.SkuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
 * @create: 2018-11-09 15:23
 **/
@Controller
@RequestMapping("/sku")
public class SkuController {
    private static Logger log = LoggerFactory.getLogger(SkuController.class);
    @Autowired
    private SkuService skuService;

    @GetMapping("list/{productId}")
    public String list(@PathVariable("productId") Integer id, Model model) {
        List<SkuDTO> skuList = this.skuService.findByProductId(id);
        model.addAttribute("skuList", skuList);
        return "sku/list";
    }

    @PostMapping("update")
    @ResponseBody
    public ResponseResult update(UpdateSkuVO skuVO) {
        this.skuService.updateSku(skuVO);
        return ResponseResult.success("修改成功");
    }
}

