package com.hnguigu.xbb.manager.web.exception;

import com.hnguigu.xbb.common.exception.XbbException;
import com.hnguigu.xbb.common.exception.XbbUploadException;
import com.hnguigu.xbb.common.util.HttpServletUtils;
import com.hnguigu.xbb.common.util.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

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
 * @create: 2018-11-08 17:41
 **/
@RestControllerAdvice
public class RestCtrlExceptionHandler {

    private static Logger log = LoggerFactory.getLogger(RestCtrlExceptionHandler.class);

    //错误页面
    private static final String DEFAULT_ERROR_VIEW = "error";

    @ExceptionHandler(value = XbbException.class)
    public Object defaultErrorHandler(HttpServletRequest request, XbbException e) throws Exception {
        log.warn(e.getStackTrace().toString());
        if (HttpServletUtils.jsAjax(request)) {
            return ResponseResult.fail(e.getMessage());
        }
        return new ModelAndView(DEFAULT_ERROR_VIEW);
    }

    @ExceptionHandler(value = XbbUploadException.class)
    public Object uploadErrorHandler(HttpServletRequest request, XbbUploadException e) {
        log.warn(e.getStackTrace().toString());
        if (HttpServletUtils.jsAjax(request)) {
            return ResponseResult.fail("文件上传失败!");
        }
        return new ModelAndView(DEFAULT_ERROR_VIEW);
    }
}
