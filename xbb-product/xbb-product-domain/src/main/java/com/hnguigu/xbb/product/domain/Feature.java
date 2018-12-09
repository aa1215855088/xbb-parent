package com.hnguigu.xbb.product.domain;


import java.io.Serializable;
import java.util.Objects;

/**
 * 商品属性
 *
 * @author lixu
 * @Date [2014-3-28 下午04:38:53]
 */

public class Feature implements Serializable {
    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private String value;
    private Integer sort;
    private Integer isDel;

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    @Override
    public String toString() {
        return "Feature{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", sort=" + sort +
                ", isDel=" + isDel +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Feature feature = (Feature) o;
        return Objects.equals(id, feature.id) &&
                Objects.equals(name, feature.name) &&
                Objects.equals(value, feature.value) &&
                Objects.equals(sort, feature.sort) &&
                Objects.equals(isDel, feature.isDel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, value, sort, isDel);
    }
}
