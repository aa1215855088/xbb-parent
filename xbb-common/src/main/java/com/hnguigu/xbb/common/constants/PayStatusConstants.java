package com.hnguigu.xbb.common.constants;

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
 * @create: 2018-12-09 02:19
 **/
public final class PayStatusConstants {


    private PayStatusConstants() {

    }

    /**
     * 货到付款
     */
    public static final Integer CASH_ON_DELIVERY = 0;

    /**
     * 待付款
     */
    public static final Integer OBLIGATION = 1;
    /**
     * 已付款
     */
    public static final Integer PAID = 2;
    /**
     * 待退款
     */
    public static final Integer FOR_A_REFUND = 3;
    /**
     * 退款成功
     */
    public static final Integer REFUND_SUCCESS = 4;
    /**
     * 退款失败
     */
    public static final Integer REFUND_FAIL = 5;

}
