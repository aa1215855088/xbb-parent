package com.hnguigu.xbb.product.dto.cart;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;
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
 * @create: 2018-12-02 15:42
 **/
public class BuyerCart implements Serializable {

    /**
     * 购物车结果集
     */
    private List<BuyerItem> items = new ArrayList<>();

    /**
     * 添加商品至购物车
     *
     * @param item 商品
     */
    public void addProduct(BuyerItem item) {
        if (items.size() == 0) {
            items.add(item);
            return;
        }
        for (BuyerItem buyerItem : items) {
            if (buyerItem.getCartDTO().getId().equals(item.getCartDTO().getId())) {
                buyerItem.setAmount(buyerItem.getAmount() + item.getAmount());
                return;
            }
        }
        items.add(item);
    }


    /**
     * 商品数量
     *
     * @return
     */
    public Integer getProductAmount() {
        Integer result = 0;
        for (BuyerItem item : items) {
            result += item.getAmount();
        }
        return result;
    }

    /**
     * 获取商品总金额
     *
     * @return
     */
    @JsonIgnore
    public Float getProductPrice() {
        Float result = 0f;
        //计算
        for (BuyerItem buyerItem : items) {
            result += buyerItem.getAmount() * buyerItem.getCartDTO().getPrice();
        }
        return result;
    }

    /**
     * 获取商品运费
     *
     * @return
     */
    @JsonIgnore
    public Float getFee() {
        Float result = 0f;
        //计算
        for (BuyerItem buyerItem : items) {
            result += buyerItem.getCartDTO().getDeliveFee();
        }
        return result;
    }

    /**
     * 获取商品总价
     *
     * @return
     */
    public Float getTotalPrice() {
        return getFee() + getProductPrice();
    }

    public List<BuyerItem> getItems() {
        return items;
    }

    public void setItems(List<BuyerItem> items) {
        this.items = items;
    }
}
