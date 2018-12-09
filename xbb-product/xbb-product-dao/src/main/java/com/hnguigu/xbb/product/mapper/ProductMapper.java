package com.hnguigu.xbb.product.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.hnguigu.xbb.product.dto.ItemSearchDTO;
import com.hnguigu.xbb.product.dto.ProductDTO;
import com.hnguigu.xbb.product.domain.Product;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

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
 * @create: 2018-11-01 16:04
 **/
public interface ProductMapper extends BaseMapper<Product> {
    /**
     * 分页查询
     *
     * @param rowBounds 分页条件
     * @param wrapper   查询条件
     * @return
     */
    @Override
    List<Product> selectPage(RowBounds rowBounds, @Param("ew") Wrapper<Product> wrapper);

    /**
     * @return
     */
    List<Product> findAll();

    @Override
    List<Product>
    selectByMap(Map<String, Object> map);

    /**
     * 有条件查询
     *
     * @return List
     */
    List<Product> findAllByMap(Map<String, Object> map);

    @Override
    Integer insert(Product product);

    int save(Product product);

    @Override
    Integer updateById(@Param("et") Product product);

    @Override
    Product selectById(Serializable serializable);

    @Override
    Product selectOne(Product product);

    @Override
    Integer update(Product product, @Param("ew") Wrapper<Product> wrapper);

    /**
     * 获取热卖的商品
     *
     * @return
     */
    List<Product> getHotProduct();

    List<ItemSearchDTO> findItemSearchAll();

    ItemSearchDTO findItemSearchById(Integer id);
}
