package com.hnguigu.xbb.product.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import com.hnguigu.xbb.product.domain.Color;
import org.apache.ibatis.annotations.Param;

/**
 * BbsColor的Dao接口
 *
 * @author
 */
public interface ColorMapper extends BaseMapper<Color> {
    List<Color> findAll();
}