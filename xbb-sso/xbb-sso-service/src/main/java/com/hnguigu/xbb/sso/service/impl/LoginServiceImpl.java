package com.hnguigu.xbb.sso.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.HashUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.db.nosql.redis.RedisDS;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hnguigu.xbb.common.util.ResponseResult;
import com.hnguigu.xbb.sso.service.LoginService;
import com.hnguigu.xbb.user.domian.User;
import com.hnguigu.xbb.user.dto.UserDTO;
import com.hnguigu.xbb.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;


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
 * @create: 2018-11-15 08:24
 **/
@Service("loginService")
@Transactional
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserMapper userMapper;
    @Value("${SESSION_EXPIRE}")
    public Integer SESSION_EXPIRE;


    @Override
    public UserDTO login(String username, String password) {
        List<User> userList = this.userMapper.selectList(new EntityWrapper<User>()
                .eq("username", username));
        if (CollectionUtil.isEmpty(userList)) {
            return null;
        }
        User user = userList.get(0);
        //MD5加密
        if (!SecureUtil.md5(password).equals(user.getPassword())) {
            return null;
        }

        String token = IdUtil.fastUUID();
        Jedis jedis = RedisDS.create().getJedis();
        // 用户信息写入redis：key："SESSION:token" value："user"
        jedis.set("SESSION:" + token, user.toJson().toJSONString());
        //设置session过期时间
        jedis.expire("SESSION:" + token, SESSION_EXPIRE);
        UserDTO userDTO = new UserDTO();
        BeanUtil.copyProperties(user, userDTO);
        //在前端设置到cookie中
        userDTO.setToken(token);
        return userDTO;
    }

    @Override
    public User checkLogin(String token) {
        Jedis jedis = RedisDS.create().getJedis();
        if (!jedis.exists("SESSION:" + token)) {
            return null;
        }
        String userJsonStr = jedis.get("SESSION:" + token);
        User user = JSON.parseObject(userJsonStr, User.class);
        //重新设置过期时间
        jedis.expire("SESSION:" + token, SESSION_EXPIRE);
        return user;
    }

    @Override
    public int logout(String token) {
        Jedis jedis = RedisDS.create().getJedis();
        jedis.del("SESSION:" + token);
        return 1;
    }


}
