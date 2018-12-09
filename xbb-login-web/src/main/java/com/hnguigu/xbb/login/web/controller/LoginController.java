package com.hnguigu.xbb.login.web.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hnguigu.xbb.common.constants.ResponseStatusConstant;
import com.hnguigu.xbb.common.util.CookieUtil;
import com.hnguigu.xbb.common.util.ResponseResult;
import com.hnguigu.xbb.sso.service.LoginService;
import com.hnguigu.xbb.user.domian.User;
import com.hnguigu.xbb.user.dto.UserDTO;
import com.qq.connect.QQConnectException;
import com.qq.connect.oauth.Oauth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

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
 * @create: 2018-11-14 17:40
 **/
@Controller
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;

    private String returnUrl;

    @GetMapping("login")
    public String login(@RequestParam(required = false, defaultValue = "") String returnUrl) {
        this.returnUrl = returnUrl;
        return "login";
    }

    @GetMapping("qqLogin")
    public String qqLogin(HttpServletRequest request, HttpServletResponse response) throws QQConnectException,
            IOException {
        return "redirect:" + new Oauth().getAuthorizeURL(request);
    }

    @GetMapping("register")
    public String register() {
        return "register";
    }

    @PostMapping("login")
    @ResponseBody
    public ResponseResult login(String username, String password) throws UnsupportedEncodingException {
        UserDTO user = this.loginService.login(username, password);
        if (user == null) {
            return ResponseResult.fail("账号密码有误!");
        }
        if (StrUtil.isNotBlank(returnUrl)) {
            //解码
            user.setReturnUrl(URLDecoder.decode(returnUrl, "UTF-8"));
        }
        return ResponseResult.success(user);
    }

    @PostMapping("checkLogin")
    @ResponseBody
    @CrossOrigin
    public ResponseResult<User> checkLogin(@CookieValue(value = "token", required = false) String token,
                                           HttpServletRequest request) {
        ResponseResult<User> result = new ResponseResult<>();
        User user = this.loginService.checkLogin(token);
        if (user != null) {
            result.setData(user);
            result.setMessage("已登录");
            result.setStatus(ResponseStatusConstant.RESPONSE_STATUS_SUCCESS);
            return result;
        }
        result.setMessage("用户登录已过期!请重新登录。");
        result.setStatus(ResponseStatusConstant.RESPONSE_STATUS_FAIL);
        return result;
    }

    @GetMapping("logout")
    public String logout(@CookieValue("token") String token) {
        this.loginService.logout(token);
        return "redirect:login";
    }


}
