<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="../head.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>babasport-list</title>
</head>
<body>
<div class="box-positon">
    <div class="rpos">当前位置: 库存管理 - 列表</div>
    <div class="clear"></div>
</div>
<div class="body-box">
    <form method="post" id="tableForm">
        <table cellspacing="1" cellpadding="0" border="0" width="100%" class="pn-ltable">
            <thead class="pn-lthead">
            <tr>
                <th width="20"><input type="checkbox" onclick="Pn.checkbox('ids',this.checked)"/></th>
                <th>商品编号</th>
                <th>商品颜色</th>
                <th>商品尺码</th>
                <th>市场价格</th>
                <th>销售价格</th>
                <th>库 存</th>
                <th>购买限制</th>
                <th>运 费</th>
                <th>是否赠品</th>
                <th>操 作</th>
            </tr>
            </thead>
            <tbody class="pn-ltbody">

            <c:forEach items="${skuList}" var="sku">
                <tr bgcolor="#ffffff" onmouseover="this.bgColor='#eeeeee'" onmouseout="this.bgColor='#ffffff'">
                    <td></td>
                    <td>${sku.productNo}</td>
                    <td align="center">${sku.color.name}</td>
                    <td align="center">${sku.size}</td>
                    <td align="center"><input type="text" id="m52" value="${sku.marketPrice}" disabled="disabled"
                                              size="10"/></td>
                    <td align="center"><input type="text" id="p52" value="${sku.skuPrice}" disabled="disabled"
                                              size="10"/></td>
                    <td align="center"><input type="text" id="i52" value="${sku.stockInventory}" disabled="disabled"
                                              size="10"/></td>
                    <td align="center"><input type="text" id="l52" value="${sku.skuUpperLimit}" disabled="disabled"
                                              size="10"/></td>
                    <td align="center"><input type="text" id="f52" value="${sku.deliveFee}" disabled="disabled"
                                              size="10"/></td>
                    <td align="center">不是</td>
                    <td align="center"><a href="#" onclick="updateSku(this)" class="pn-opt">修改</a> | <a
                            href="#" onclick="save(this,${sku.id})" class="pn-opt">保存</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </form>
</div>
</body>
</html>
<script>
    function updateSku(obj) {
        $(obj).parents("tr").find("input").each(function () {
            $(this).attr("disabled", false)
        })
    }

    function save(obj, id) {
        $.ajax({
            type: "post",
            url: "${pageContext.request.contextPath}/sku/update",
            data: {
                id: id,
                marketPrice: $(obj).parents("tr").find("input").eq(0).val(),
                skuPrice: $(obj).parents("tr").find("input").eq(1).val(),
                stockInventory: $(obj).parents("tr").find("input").eq(2).val(),
                skuUpperLimit: $(obj).parents("tr").find("input").eq(3).val(),
                deliveFee: $(obj).parents("tr").find("input").eq(4).val(),
            },
            success: function (data) {
                $(obj).parents("tr").find("input").each(function () {
                    $(this).attr("disabled", true)
                });
                if (data.status == 1) {
                    layer.msg(data.message, {
                        icon: 1,
                        time: 2000
                    })
                } else {
                    layer.msg(data.message, {
                        icon: 2,
                        time: 2000
                    })
                }

            },
            error: function () {
                layer.msg("系统繁忙!请重试", {
                    icon: 2,
                    time: 2000
                })
            }
        })
    }
</script>
