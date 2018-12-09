package com.hnguigu.xbb.product.domain;

import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Objects;

/**
 * 商品类型
 *
 * @author lixu
 * @Date [2014-3-28 下午04:38:53]
 */
@TableName("bbs_type")
public class Type implements Serializable {
    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private Integer parentId;
    private String note;
    private Integer isDisplay;

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

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getIsDisplay() {
        return isDisplay;
    }

    public void setIsDisplay(Integer isDisplay) {
        this.isDisplay = isDisplay;
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parentId=" + parentId +
                ", note='" + note + '\'' +
                ", isDisplay=" + isDisplay +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Type type = (Type) o;
        return Objects.equals(id, type.id) &&
                Objects.equals(name, type.name) &&
                Objects.equals(parentId, type.parentId) &&
                Objects.equals(note, type.note) &&
                Objects.equals(isDisplay, type.isDisplay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, parentId, note, isDisplay);
    }
}
