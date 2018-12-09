package com.hnguigu.xbb.product.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hnguigu.xbb.product.domain.Type;
import com.hnguigu.xbb.product.mapper.TypeMapper;
import com.hnguigu.xbb.product.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
 * @create: 2018-11-05 19:33
 **/
@Service("typeService")
@Transactional
public class TypeServiceImpl implements TypeService {
    @Autowired
    private TypeMapper typeMapper;

    @Override
    public List<Type> findAll() {
        return this.typeMapper.findAll();
    }

    @Override
    public List<Type> findByShow() {
        return this.typeMapper.selectList(new EntityWrapper<Type>().eq("is_display", 1));
    }


}
