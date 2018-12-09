<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="../head.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" href="//res.layui.com/layui/dist/css/layui.css" media="all">

    <title>babasport-list</title>
    <script type="text/javascript">

        function getTableForm() {
            return document.getElementById('tableForm');
        }


        function changePageNo() {
            $("#query").submit();
        }

        $(function () {
            $("#paginator").bootstrapPaginator({
                bootstrapMajorVersion: 3,
                currentPage:${pageInfo.pageNum},
                totalPages:${pageInfo.pages},
                itemTexts: function (type, page, current) {
                    switch (type) {
                        case "first":
                            return "首页";
                        case "prev":
                            return "上一页";
                        case "next":
                            return "下一页";
                        case "last":
                            return "尾页";
                        case "page":
                            return page
                    }
                },
                onPageClicked: function (event, origianEvent, type, page) {
                    $("#pageNum").val(page);
                    $("#query").submit();
                }
            })


        })

        function query() {

        }

        function moveEnd(obj) {
            obj.focus();
            var len = obj.value.length;
            if (document.selection) {
                var sel = obj.createTextRange();
                sel.moveStart('character', len);
                sel.collapse();
                sel.select();
            } else if (typeof obj.selectionStart == 'number' && typeof obj.selectionEnd == 'number') {
                obj.selectionStart = obj.selectionEnd = len;
            }
        }
    </script>
</head>
<body>
<div class="box-positon">
    <div class="rpos">当前位置: 商品管理 - 列表</div>
    <form class="ropt">
        <input class="add" type="button" value="添加"
               onclick="javascript:window.location.href='${pageContext.request.contextPath}/product/add'"/>
    </form>
    <div class="clear"></div>
</div>
<div class="body-box">
    <form id="query" action="${pageContext.request.contextPath}/product/list" method="post" style="padding-top:5px;">
        <input type="hidden" name="pageNum" value="${pageInfo.pageNum}" id="pageNum">
        名称: <input type="text" autofocus value="${queryProductVO.name}" onfocus="moveEnd(this)" name="name"/>
        <select onchange="changePageNo()" name="brandId">
            <option value="">请选择品牌</option>
            <c:forEach items="${brands}" var="brand">
                <c:choose>
                    <c:when test="${queryProductVO.brandId==brand.id}">
                        <option selected="selected" value="${brand.id}">${brand.name}</option>
                    </c:when>
                    <c:otherwise>
                        <option value="${brand.id}">${brand.name}</option>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </select>
        <select onchange="changePageNo()" id="isShow" name="isShow">
            <c:if test="${queryProductVO.isShow==1}">
                <option selected="selected" value="1">上架</option>
                <option value="0">下架</option>
            </c:if>
            <c:if test="${queryProductVO.isShow==0}">
                <option value="1">上架</option>
                <option selected="selected" value="0">下架</option>
            </c:if>
        </select>
        <input type="submit" class="query" value="查询"/>
    </form>
    <form method="post" id="tableForm">
        <input type="hidden" value="" name="pageNo"/>
        <input type="hidden" value="" name="queryName"/>
        <table cellspacing="1" cellpadding="0" width="100%" border="0" class="pn-ltable">
            <thead class="pn-lthead">
            <tr>
                <th width="20"><input type="checkbox" onclick="Pn.checkbox('ids',this.checked)"/></th>
                <th>商品编号</th>
                <th>商品名称</th>
                <th>图片</th>
                <th width="4%">新品</th>
                <th width="4%">热卖</th>
                <th width="4%">推荐</th>
                <th width="4%">上下架</th>
                <th width="12%">操作选项</th>
            </tr>
            </thead>
            <tbody class="pn-ltbody">
            <c:forEach items="${pageInfo.list}" var="product">
                <tr bgcolor="#ffffff" onmouseover="this.bgColor='#eeeeee'" onmouseout="this.bgColor='#ffffff'">
                    <td><input type="checkbox" name="ids" value="${product.id}"/></td>
                    <td>${product.no}</td>
                    <td align="center">${product.name}</td>
                    <td align="center"><img width="50" height="50" src="${product.image.split(",")[0]}"/></td>
                    <td align="center">${product.isNew==1?"是":"不是"}</td>
                    <td align="center">${product.isHot==1?"是":"不是"}</td>
                    <td align="center">${product.isCommend==1
                            ?"是":"不是"}</td>
                    <td align="center">${product.isShow==1?"上架":"下架"}</td>
                    <td align="center">
                        <a href="#" class="pn-opt">查看</a> | <a href="#" class="pn-opt">修改</a> | <a href="#"
                                                                                                   onclick="optDelete(${product.id},'${product.name}')"
                                                                                                   class="pn-opt">删除</a>
                        |
                        <a href="${pageContext.request.contextPath}/sku/list/${product.id}" class="pn-opt">库存</a>
                    </td>
                </tr>
            </c:forEach>

            </tbody>
        </table>
        <div class="page pb15 text-center">

            <ul id="paginator"></ul>

            </span></div>
        <div style="margin-top:15px;">

            <input class="del-button" type="button" value="删除" onclick="optDelete();"/>
            <input class="add" type="button" value="上架" onclick="itemUpShelf();"/>
            <input class="del-button" type="button" value="下架" onclick="itemDownShelf();"/>
        </div>
    </form>
</div>
</body>
<script>
    /*删除商品*/
    function optDelete(id, name) {

        var ids = "";
        var names = "";
        if (id != null) {
            names = "<br><a href=''>[" + name + "]</a><br>";
            ids = id
        }
        $("input[name=ids]:checked").each(function (i, e) {
            names += "<br><a href=''>[" + $(this).parents("tr").find("td").eq(2).text() + "]</a><br>";
            ids += $(this).val() + ",";
        });
        if (ids == "") {
            layer.msg("请至少选择一行您需要删除的商品");
            return;
        }
        layer.confirm('您确定要删除以下商品？' + names.substring(0, names.length - 1), {
            btn: ['确定', '取消']
        }, function () {
            $.ajax({
                type: "post",
                url: "${pageContext.request.contextPath}/product/del",
                data: {
                    id: ids
                },
                success: function (data) {

                    if (data.status == 1) {
                        layer.msg(data.message, {
                            icon: 1,
                            time: 2000
                        }, function () {
                            $("#query").submit();
                        })
                    } else {
                        layer.msg(data.message, {
                            icon: 2,
                            time: 2000
                        })
                    }
                }
                ,
                error: function () {
                    layer.msg("系统繁忙请重试")
                }
            })
        }, function () {

        });

    }

    /*上架商品*/
    function itemUpShelf() {
        if (Pn.checkedCount('ids') <= 0) {
            layer.msg("请至少选择一行您需要上架的商品");
            return;
        }
        if ($("#isShow").val() == 1) {
            layer.msg("请选择未上架的商品");
            return;
        }
        var ids = "";
        var names = "";
        $("input[name=ids]:checked").each(function (i, e) {
            names += "<br><a href=''>[" + $(this).parents("tr").find("td").eq(2).text() + "]</a><br>";
            ids += $(this).val() + ",";
        });
        layer.confirm('您确定要上架一下商品吗？' + names.substring(0, names.length - 1), {
            btn: ['确定', '取消'] //按钮
        }, function () {
            $.ajax({
                type: "post",
                url: "${pageContext.request.contextPath}/product/itemUpShelf",
                data: {
                    id: ids.substring(0, ids.length - 1)
                },
                success: function (data) {
                    if (data.status == 1) {
                        layer.msg(data.message, {
                            icon: 1,
                            time: 2000
                        }, function () {
                            $("#query").submit();
                        })
                    } else {
                        layer.msg(data.message, {
                            icon: 2,
                            time: 2000
                        })
                    }
                }
                ,
                error: function () {
                    layer.msg("系统繁忙请重试")
                }
            })
        }, function () {

        });

    }

    /*下架商品*/
    function itemDownShelf() {
        if (Pn.checkedCount('ids') <= 0) {
            layer.msg("请至少选择一行您需要下架的商品");
            return;
        }
        if ($("#isShow").val() == 0) {
            layer.msg("请您选择未下架的商品!");
            return;
        }
        var ids = "";
        var names = "";
        $("input[name=ids]:checked").each(function (i, e) {
            names += "<br><a href=''>[" + $(this).parents("tr").find("td").eq(2).text() + "]</a><br>";
            ids += $(this).val() + ",";
        });
        layer.confirm('您确定要下架这些商品吗？' + names.substring(0, names.length - 1), {
            btn: ['确定', '取消'] //按钮
        }, function () {
            $.ajax({
                type: "post",
                url: "${pageContext.request.contextPath}/product/itemDownShelf",
                data: {
                    id: ids.substring(0, ids.length - 1)
                },
                success: function (data) {
                    if (data.status == 1) {
                        layer.msg(data.message, {
                            icon: 1,
                            time: 2000
                        }, function () {
                            $("#query").submit();
                        })
                    } else {
                        layer.msg(data.message, {
                            icon: 2,
                            time: 2000
                        })
                    }
                }
                ,
                error: function () {
                    layer.msg("系统繁忙请重试")
                }
            })
        }, function () {

        });

    }
</script>
</html>