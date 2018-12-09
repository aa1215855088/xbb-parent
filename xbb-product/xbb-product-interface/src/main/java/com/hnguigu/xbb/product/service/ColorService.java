package com.hnguigu.xbb.product.service;

import java.util.List;

import com.hnguigu.xbb.product.domain.Color;

/**
 * Color的服务接口
 *
 * @author
 */
public interface ColorService {
    /**
     * 获得Color数据集,可以通过辅助工具Assist进行条件查询,如果没有条件则传入null
     *
     * @return
     */
    String find(Color value);


    List<Color> findAll();

    /**
     * 通过Color的id获得Color对象
     *
     * @param id
     * @return
     */
    String findOne(Integer id);

    /**
     * 将Color中属性值不为null的数据到数据库
     *
     * @param value
     * @return
     */
    String saveNotNull(Color value);

    /**
     * 通过Color的id更新Color中属性不为null的数据
     *
     * @param enti
     * @return
     */
    String updateNotNullById(Color enti);

    /**
     * 通过Color的id删除Color
     *
     * @param id
     * @return
     */
    String deleteById(Integer id);
}
