package com.hnguigu.xbb.order.web.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.hnguigu.xbb.common.constants.ResponseStatusConstant;
import com.hnguigu.xbb.common.vo.CartVO;
import com.hnguigu.xbb.common.util.CookieUtil;
import com.hnguigu.xbb.common.util.ResponseResult;
import com.hnguigu.xbb.common.vo.OrderVO;
import com.hnguigu.xbb.order.domain.Order;
import com.hnguigu.xbb.order.service.OrderService;
import com.hnguigu.xbb.product.dto.cart.BuyerCart;
import com.hnguigu.xbb.sso.service.LoginService;
import com.hnguigu.xbb.user.domian.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


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
 * @create: 2018-12-06 16:46
 **/
@Controller
@Scope("session")
@RequestMapping("order")
public class OrderController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private OrderService orderService;

    private User user = null;
    private BuyerCart buyerCart = null;

    @PostMapping("getOrderInfo")
    public String getOrderInfo(HttpServletRequest request, HttpServletResponse response, Model model,
                               Integer[] skuIds) {
        user = getLoginInfo(request, response);
        if (user == null) {
            return "redirect:http://localhost:8088/login?returnUrl=http%253A%252F%252Flocalhost%253A1234%252FmyCart";
        }
        buyerCart = getCartInfo(skuIds, user.getUsername());
        model.addAttribute("user", user);
        model.addAttribute("cartInfo", buyerCart);
        return "order";
    }

    @PostMapping("createOrder")
    @ResponseBody
    public ResponseResult<Order> createOrder(OrderVO orderVO) {
        Order order = this.orderService.createOrder(orderVO, user, buyerCart);
        ResponseResult<Order> responseResult = new ResponseResult<>();
        responseResult.setStatus(ResponseStatusConstant.RESPONSE_STATUS_SUCCESS);
        responseResult.setData(order);
        return responseResult;
    }

    @GetMapping("/payOrder/{id}")
    public String payOrder(@PathVariable Long id, Model model) {
        Order order = this.orderService.getOrderByIdAndStateIsSubmitOrder(id, user.getUsername());
        model.addAttribute("order", order);
        return "success";
    }

    /**
     * 获取登录信息
     *
     * @param request
     * @param response
     * @return
     */
    private User getLoginInfo(HttpServletRequest request, HttpServletResponse response) {

        String token = CookieUtil.getCookieValue(request, "token", "utf-8");

        if (StrUtil.isBlank(token)) {
            return null;
        }
        return this.loginService.checkLogin(token);
    }

    /**
     * 跟据ID查询购物
     *
     * @param skuIds
     * @return
     */
    private BuyerCart getCartInfo(Integer[] skuIds, String username) {

        String result = HttpRequest
                .post("http://localhost:1234/getCartBySkuId")
                .body(JSON.toJSONString(new CartVO(skuIds, username)))
                .execute()
                .body();
        System.out.println(result);
        return JSON.parseObject(result, BuyerCart.class);
    }
}
