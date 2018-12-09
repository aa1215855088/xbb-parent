package com.hnguigu.xbb.cart.web.controller;


import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;

import com.alibaba.fastjson.JSON;
import com.hnguigu.xbb.cart.service.CartService;
import com.hnguigu.xbb.common.constants.ResponseStatusConstant;
import com.hnguigu.xbb.common.vo.CartVO;
import com.hnguigu.xbb.common.util.CookieUtil;
import com.hnguigu.xbb.common.util.ResponseResult;
import com.hnguigu.xbb.product.dto.cart.BuyerCart;
import com.hnguigu.xbb.product.dto.cart.BuyerItem;
import com.hnguigu.xbb.user.domian.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;

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
 * @create: 2018-12-02 15:22
 **/
@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("myCart")
    public String myCart(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("cartList", findBuyerCart(request, response));
        return "cart";
    }

    @PostMapping("addCart")
    @ResponseBody
    @CrossOrigin(origins = "http://locsalhost:8888")
    public String addCart(Integer skuId, Integer num,
                          HttpServletRequest request, HttpServletResponse response) {

        BuyerCart oldCart = findBuyerCart(request, response);

        BuyerCart newCart = this.cartService.addCart(oldCart, num, skuId);

        User user = checkLogin(request, response);
        //true:登录状态 将购物车信息添加至redis
        //false:未登录状态 将购物车信息添加至cookie中
        if (null != user) {
            this.cartService.saveCartListToRedis(user.getUsername(), newCart);
        } else {
            CookieUtil.setCookie(request, response, "cart", JSON.toJSONString(newCart), 3600 * 24, "UTF-8");
        }

        return "redirect:/myCart";
    }

    @DeleteMapping("/delCart/{id}")
    public String delCart(@PathVariable Integer id, HttpServletResponse response, HttpServletRequest request) {
        User user = checkLogin(request, response);
        if (null != user) {
            this.cartService.delCartToRedis(id, user.getUsername());
        } else {
            String cartJsonStr = CookieUtil.getCookieValue(request, "cart", "utf-8");
            BuyerCart buyerCart = JSON.parseObject(cartJsonStr, BuyerCart.class);
            Iterator<BuyerItem> items = buyerCart.getItems().iterator();
            while (items.hasNext()) {
                Integer oldId = items.next().getCartDTO().getId();
                if (oldId.equals(id)) {
                    items.remove();
                }
            }
            CookieUtil.setCookie(request, response, "cart", JSON.toJSONString(buyerCart), 3600 * 24, "UTF-8");
        }
        return "redirect:/myCart";
    }

    @PostMapping("getCartBySkuId")
    @ResponseBody
    public BuyerCart getCartBySkuId(@RequestBody CartVO cartVO,
                                    HttpServletResponse response,
                                    HttpServletRequest request) {
        if (cartVO.getSkuIds().length == 0 || StrUtil.isBlank(cartVO.getUsername())) {
            return null;
        }

        return this.cartService.getCartBySkuId(cartVO.getSkuIds(), cartVO.getUsername());
    }

    /**
     * 提取购物车
     *
     * @return 购物车
     */
    private BuyerCart findBuyerCart(HttpServletRequest request, HttpServletResponse response) {
        User user = checkLogin(request, response);
        if (null != user) {
            return this.cartService.findCartListFromRedis(user.getUsername());
        } else {
            String cartJsonStr = CookieUtil.getCookieValue(request, "cart", "utf-8");
            if (StrUtil.isBlank(cartJsonStr)) {
                return null;
            }
            return JSON.parseObject(cartJsonStr, BuyerCart.class);
        }
    }

    /**
     * 验证是否登录
     *
     * @param request
     * @param response
     * @return
     */
    private User checkLogin(HttpServletRequest request, HttpServletResponse response) {

        String token = CookieUtil.getCookieValue(request, "token", "utf-8");
        if (StrUtil.isBlank(token)) {
            return null;
        }
        //通过httpclient发送post请求判断是否登录
        String result = HttpRequest.post("http://localhost:8088/checkLogin").cookie("token=" + token).execute().body();

        ResponseResult responseResult = JSON.parseObject(result, ResponseResult.class);
        if (responseResult.getStatus() == ResponseStatusConstant.RESPONSE_STATUS_SUCCESS) {
            User user = JSON.parseObject(String.valueOf(responseResult.getData()), User.class);
            //同步购物车
            String cartJsonStr = CookieUtil.getCookieValue(request, "cart", "utf-8");
            BuyerCart buyerCart = JSON.parseObject(cartJsonStr, BuyerCart.class);
            if (buyerCart != null) {
                this.cartService.synchronousSoppingCart(user.getUsername(), buyerCart);
                CookieUtil.deleteCookie(request, response, "cart");
            }
            return user;
        }
        return null;
    }
}
