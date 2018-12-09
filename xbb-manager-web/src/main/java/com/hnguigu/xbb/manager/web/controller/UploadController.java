package com.hnguigu.xbb.manager.web.controller;

import cn.hutool.core.util.StrUtil;
import com.hnguigu.xbb.common.exception.XbbUploadException;
import com.hnguigu.xbb.common.util.ResponseResult;
import com.hnguigu.xbb.product.service.UploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
 * @create: 2018-11-09 16:15
 **/
@Controller
@RequestMapping("upload")
public class UploadController {

    private static Logger log = LoggerFactory.getLogger(UploadController.class);
    @Autowired
    private UploadService uploadService;

    @PostMapping("uploadPic")
    @ResponseBody
    public ResponseResult uploadPic(@RequestParam("file") MultipartFile[] files) {
        try {
            StringBuilder urls = new StringBuilder();
            //图片上传
            for (MultipartFile file : files) {
                String url = this.uploadService.fastDFSUploadPic(file.getBytes(), file.getOriginalFilename(), file.getSize());
                urls.append(url);
                urls.append(",");
            }
            if (StrUtil.isBlank(urls)) {
                throw new XbbUploadException("图片上传失败");
            }
            return ResponseResult.success(urls.substring(0, urls.length() - 1), "图片上传成功");
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            return ResponseResult.fail("图片上传失败");
        }
    }
}
