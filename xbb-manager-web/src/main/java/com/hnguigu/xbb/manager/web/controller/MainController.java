package com.hnguigu.xbb.manager.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
 * @create: 2018-11-01 11:11
 **/
@Controller
public class MainController {

    private static Logger log = LoggerFactory.getLogger(MainController.class);

    @GetMapping("/main")
    public String main() {
        return "main";
    }

    @GetMapping("/date")
    public String date() {
        return "date";
    }

    @GetMapping("/head")
    public String head() {
        return "head";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/left")
    public String left() {
        return "left";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/right")
    public String right() {
        return "right";
    }

    @GetMapping("/top")
    public String top() {
        return "top";
    }
}
