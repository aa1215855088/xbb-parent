<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE">
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7"/>
    <title>新巴巴，欢迎登录</title>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/webbase.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/pages-login.css"/>
</head>

<body>
<div class="login-box">
    <!--head-->
    <div class="py-container logoArea">
        <a href="" class="logo"></a>
    </div>
    <!--loginArea-->
    <div class="loginArea">
        <div class="py-container login">
            <div class="loginform">
                <ul class="sui-nav nav-tabs tab-wraped">
                    <li>
                        <a href="#index" data-toggle="tab">
                            <h3>扫描登录</h3>
                        </a>
                    </li>
                    <li class="active">
                        <a href="#profile" data-toggle="tab">
                            <h3>账户登录</h3>
                        </a>
                    </li>
                </ul>
                <div class="tab-content tab-wraped">
                    <div id="index" class="tab-pane">
                        <p>二维码登录，暂为官网二维码</p>
                        <img src="${pageContext.request.contextPath}/img/wx_cz.jpg"/>
                    </div>
                    <div id="profile" class="tab-pane  active">
                        <form class="sui-form">
                            <div class="input-prepend"><span class="add-on loginname"></span>
                                <input id="username" type="text" placeholder="邮箱/用户名/手机号"
                                       class="span2 input-xfat">
                            </div>
                            <div class="input-prepend"><span class="add-on loginpwd"></span>
                                <input id="password" type="password" placeholder="请输入密码" class="span2 input-xfat">
                            </div>
                            <div class="setting">
                                <label class="checkbox inline">
                                    <input name="m1" type="checkbox" value="2" checked="">
                                    自动登录
                                </label>
                                <span class="forget">忘记密码？</span>
                            </div>
                            <div class="logined">
                                <a class="sui-btn btn-block btn-xlarge btn-danger" href="#"
                                   id="login">登&nbsp;&nbsp;录</a>
                            </div>
                        </form>
                        <div class="otherlogin">
                            <div class="types">
                                <ul>
                                    <li><a href="${pageContext.request.contextPath}/qqLogin"><img
                                            src="${pageContext.request.contextPath}/img/qq.png" width="35px"
                                            height="35px"/></a></li>
                                    <li><a href=""><img src="${pageContext.request.contextPath}/img/sina.png"/></a></li>
                                    <li><a href=""><img src="${pageContext.request.contextPath}/img/ali.png"/></a></li>
                                    <li><a href=""><img src="${pageContext.request.contextPath}/img/weixin.png"/></a>
                                    </li>
                                </ul>
                            </div>
                            <span class="register"><a href="${pageContext.request.contextPath}/register"
                                                      target="_blank">立即注册</a></span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--foot-->
    <div class="py-container copyright">
        <ul>
            <li>关于我们</li>
            <li>联系我们</li>
            <li>联系客服</li>
            <li>商家入驻</li>
            <li>营销中心</li>
            <li>手机品优购</li>
            <li>销售联盟</li>
            <li>品优购社区</li>
        </ul>
        <div class="address">地址：北京市昌平区建材城西路金燕龙办公楼一层 邮编：100096 电话：400-618-4000 传真：010-82935100</div>
        <div class="beian">京ICP备08001421号京公网安备110108007702
        </div>
    </div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/jquery/jquery.min.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/js/plugins/jquery.easing/jquery.easing.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/sui/sui.min.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/js/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/pages/login.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/layer/layer.js"></script>

</body>
</html>
<script>

    $("#login").on("click", function () {
        if ($("#username").val().trim() == "") {
            layer.msg("请输入账号!");
            return;
        }
        if ($("#password").val().trim() == "") {
            layer.msg("请输入密码!");
            return;
        }
        $.ajax({
            type: "post",
            url: "${pageContext.request.contextPath}/login",
            data: {
                username: $("#username").val(),
                password: $("#password").val()
            },
            success: function (data) {
                if (data.status == 1) {
                    //默认7天
                    setCookie("token", data.data.token, 7);
                    if (data.data.returnUrl == null) {
                        window.location = "http://localhost:8087/home"
                    } else {
                        window.location = data.data.returnUrl;
                    }
                } else {
                    layer.msg(data.message);
                }
            },
            error: function () {
                layer.msg("系统繁忙!请稍后重试。");
            }
        });
    })

    function setCookie(key, value, iDay) {
        var oDate = new Date();
        oDate.setDate(oDate.getDate() + iDay);
        document.cookie = key + '=' + value + ';expires=' + oDate;

    }
</script>
