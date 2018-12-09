package com.hnguigu.xbb.product.domain;

import java.io.Serializable;
import java.util.Objects;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.hnguigu.xbb.common.constants.Constants;



/**
 * 品牌
 *
 * @author lx
 */
@TableName("bbs_brand")
public class Brand implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private String description;
    @TableField("img_url")
    private String imgUrl;
    private Integer sort;
    private Integer isDisplay;

    //获取全路径
    public String getAllUrl() {
        return Constants.IMAGE_URL + imgUrl;
    }

    //页号
    private Integer pageNo = 1;
    //开始行
    private Integer startRow;
    //每页数
    private Integer pageSize = 10;


    public Integer getStartRow() {
        return startRow;
    }

    public void setStartRow(Integer startRow) {
        this.startRow = startRow;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        //计算一次开始行
        this.startRow = (pageNo - 1) * pageSize;
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        //计算一次开始行
        this.startRow = (pageNo - 1) * pageSize;
        this.pageNo = pageNo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getIsDisplay() {
        return isDisplay;
    }

    public void setIsDisplay(Integer isDisplay) {
        this.isDisplay = isDisplay;
    }

    @Override
    public String toString() {
        return "Brand [id=" + id + ", name=" + name + ", description="
                + description + ", imgUrl=" + imgUrl + ", sort=" + sort
                + ", isDisplay=" + isDisplay + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Brand brand = (Brand) o;
        return Objects.equals(id, brand.id) &&
                Objects.equals(name, brand.name) &&
                Objects.equals(description, brand.description) &&
                Objects.equals(imgUrl, brand.imgUrl) &&
                Objects.equals(sort, brand.sort) &&
                Objects.equals(isDisplay, brand.isDisplay) &&
                Objects.equals(pageNo, brand.pageNo) &&
                Objects.equals(startRow, brand.startRow) &&
                Objects.equals(pageSize, brand.pageSize);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, imgUrl, sort, isDisplay, pageNo, startRow, pageSize);
    }
}
