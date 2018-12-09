package com.hnguigu.xbb.sso.service;

import com.hnguigu.xbb.common.util.ResponseResult;
import com.hnguigu.xbb.user.domian.User;
import com.hnguigu.xbb.user.dto.UserDTO;

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
 * @create: 2018-11-15 08:23
 **/
public interface LoginService {

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    UserDTO login(String username, String password);

    /**
     * 根据Token判断是否登录
     *
     * @param token 令牌
     * @return
     */
    User checkLogin(String token);

    /**
     * 注销
     *
     * @param token
     * @return
     */
    int logout(String token);
}
