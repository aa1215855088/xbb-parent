package com.hnguigu.xbb.product.service;

import com.github.pagehelper.PageInfo;
import com.hnguigu.xbb.product.dto.ProductDTO;
import com.hnguigu.xbb.common.vo.QueryProductVO;
import com.hnguigu.xbb.product.domain.Product;

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
 * @create: 2018-11-01 16:06
 **/
public interface ProductService {
    /**
     * 有条件的分页查询
     *
     * @param pageNum  第几页
     * @param pageSize 每页大小
     * @param product  查询对象
     * @return
     */
    List<Product> selectPage(Integer pageNum, Integer pageSize, QueryProductVO product);

    /**
     * 查询所有
     *
     * @return
     */
    List<Product> findAll();

    List<Product> selectByMap(Map<String, Object> map);

    /**
     * 条件分页查询
     *
     * @param map      条件
     * @param pageNum  页数
     * @param pageSize 每页记录数
     * @return
     */
    PageInfo<Product> findAllByMap(Map<String, Object> map, Integer pageNum, Integer pageSize);

    /**
     * 添加商品
     *
     * @param product
     */
    void add(Product product);

    /**
     * 检查名字是否存在
     *
     * @param name 商品名称
     * @return true:存在 false:不存在
     */
    boolean checkName(String name);


    /**
     * 根据ID查询商品
     *
     * @param productId
     * @return product
     */
    Product findProductById(Integer productId);

    /**
     * 上架商品
     */
    void itemUpShelf(Integer[] productIds);

    /**
     * 下架商品
     */
    void itemDownShelf(Integer[] productIds);

    /**
     * 删除商品
     */
    void delProduct(Integer[] productIds);
}
