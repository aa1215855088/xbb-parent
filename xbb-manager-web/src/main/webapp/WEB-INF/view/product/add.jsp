<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="../head.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>babasport-add</title>
    <link rel="stylesheet" href="//res.layui.com/layui/dist/css/layui.css" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/layer/mobile/need/layer.css" media="all">
    <style type="">
        .h2_ch a:hover, .h2_ch a.here {
            color: #FFF;
            font-weight: bold;
            background-position: 0px -32px;
        }

        .h2_ch a {
            float: left;
            height: 32px;
            margin-right: -1px;
            padding: 0px 16px;
            line-height: 32px;
            font-size: 14px;
            font-weight: normal;
            border: 1px solid #C5C5C5;
            background: url('/res/itcast/img/admin/bg_ch.gif') repeat-x scroll 0% 0% transparent;
        }

        a {
            color: #06C;
            text-decoration: none;
        }
    </style>
    <script type="text/javascript">
        $(function () {
            var tObj;
            $("#tabs a").each(function () {
                if ($(this).attr("class").indexOf("here") == 0) {
                    tObj = $(this)
                }
                $(this).click(function () {
                    var c = $(this).attr("class");
                    if (c.indexOf("here") == 0) {
                        return;
                    }
                    var ref = $(this).attr("ref");
                    var ref_t = tObj.attr("ref");
                    tObj.attr("class", "nor");
                    $(this).attr("class", "here");
                    $(ref_t).hide();
                    $(ref).show();
                    tObj = $(this);
                    if (ref == '#tab_2') {
                        var fck = new FCKeditor("productdesc");
                        fck.BasePath = "/res/fckeditor/";
                        fck.Height = 400;
                        fck.Config["ImageUploadURL"] = "/upload/uploadFck.do";
                        fck.ReplaceTextarea();
                    }
                });

            });


            $('#product-image').on('change', function () {
                //上传图片
                function uploadPic() {
                    //定义参数
                    var options = {
                        url: "${pageContext.request.contextPath}/upload/uploadPic",
                        dataType: "json",
                        type: "post",
                        success: function (data) {
                            var a = layer.load();
                            if (data.status == 1) {
                                layer.close(a);
                                layer.msg(data.message, {
                                    icon: 1,
                                    time: 2000
                                });
                                $('#allImgUrl').attr('src', window.URL.createObjectURL(data.data));
                                $("#path").val(data.data);
                            } else {
                                layer.close(a);
                                layer.msg(data.message, {
                                    icon: 2,
                                    time: 2000
                                });
                            }

                        },
                        error: function () {
                            layer.msg("系统繁忙!请重试", {
                                icon: 2,
                                time: 2000
                            });
                        }
                    };
                }
            });


        });


    </script>
</head>
<body>
<div class="box-positon">
    <div class="rpos">当前位置: 商品管理 - 添加</div>
    <form class="ropt">
        <input type="submit" onclick="this.form.action='${pageContext.request.contextPath}/product/list';" value="返回列表"
               class="return-button"/>
    </form>
    <div class="clear"></div>
</div>
<h2 class="h2_ch">
	<span id="tabs">
<a href="javascript:void(0);" ref="#tab_1" title="基本信息" class="here">基本信息</a>
<a href="javascript:void(0);" ref="#tab_2" title="商品描述" class="nor">商品描述</a>
<a href="javascript:void(3);" ref="#tab_3" title="商品参数" class="nor">包装清单</a>
	</span>
</h2>
<div class="body-box" style="float:right">
    <form id="jvForm" action="${pageContext.request.contextPath}/product/add" method="post"
          enctype="multipart/form-data">
        <table cellspacing="1" cellpadding="2" width="100%" border="0" class="pn-ftable">
            <tbody id="tab_1">
            <tr>
                <td width="20%" class="pn-flabel pn-flabel-h">
                    <span class="pn-frequired">*</span>
                    商品类型:
                </td>
                <td width="80%" class="pn-fcontent">
                    <select id="typeId" name="typeId">
                        <option value="">请选择</option>
                        <c:forEach items="${types}" var="type">
                            <option value="${type.id}">${type.name}</option>
                        </c:forEach>
                    </select>
                    <span style="position: absolute"></span>
                </td>
            </tr>
            <tr>
                <td width="20%" class="pn-flabel pn-flabel-h">
                    <span class="pn-frequired">*</span>
                    商品名称:
                </td>
                <td width="80%" class="pn-fcontent">
                    <input type="text" class="required" id="name" name="name" maxlength="100" size="100"/> <span
                        style="position: absolute"></span>
                </td>
            </tr>
            <tr>
                <td width="20%" class="pn-flabel pn-flabel-h">
                    商品品牌:
                </td>
                <td width="80%" class="pn-fcontent">
                    <select id="brandId" name="brandId">
                        <option value="">请选择品牌</option>
                        <c:forEach items="${brands}" var="brand">
                            <option value="${brand.id}">${brand.name }</option>
                        </c:forEach>
                    </select>
                    <span style="position: absolute"></span>
                </td>
            </tr>
            <tr>
                <td width="20%" class="pn-flabel pn-flabel-h">
                    商品毛重:
                </td>
                <td width="80%" class="pn-fcontent">
                    <input type="text" value="0.6" class="required" name="weight" maxlength="10"/>KG
                    <span style="position: absolute"></span>
                </td>
            </tr>
            <tr>
                <td width="20%" class="pn-flabel pn-flabel-h">
                    材质:
                </td>
                <td width="80%" class="pn-fcontent">
                    <c:forEach items="${features}" var="feature">
                        <input type="checkbox" value="${feature.id }" name="feature"/>${feature.name }
                    </c:forEach>
                    <span style="position: absolute"></span>
                </td>
            </tr>
            <tr>
                <td width="20%" class="pn-flabel pn-flabel-h">
                    <span class="pn-frequired">*</span>
                    颜色:
                </td>
                <td width="80%" class="pn-fcontent">
                    <c:forEach items="${colors}" var="color">
                        <input type="checkbox" value="${color.id}" name="color"/>${color.name}
                    </c:forEach>
                    <span style="position: absolute"></span>
                </td>
            </tr>
            <tr>
                <td width="20%" class="pn-flabel pn-flabel-h">
                    <span class="pn-frequired">*</span>
                    尺码:
                </td>
                <td width="80%" class="pn-fcontent">
                    <input type="checkbox" name="size" value="S"/>S
                    <input type="checkbox" name="size" value="M"/>M
                    <input type="checkbox" name="size" value="L"/>L
                    <input type="checkbox" name="size" value="XL"/>XL
                    <input type="checkbox" name="size" value="XXL"/>XXL
                    <span style="position: absolute"></span>
                </td>
            </tr>
            <tr>
                <td width="20%" class="pn-flabel pn-flabel-h">
                    状态:
                </td>
                <td width="80%" class="pn-fcontent">
                    <input type="checkbox" name="isNew" value="1"/>新品
                    <input type="checkbox" name="isCommend" value="1"/>推荐
                    <input type="checkbox" name="isHot" value="1"/>热卖
                </td>
            </tr>
            <tr>
                <td width="20%" class="pn-flabel pn-flabel-h">
                    <span class="pn-frequired">*</span>
                    上传商品图片(90x150尺寸):
                </td>
                <td width="80%" class="pn-fcontent">
                    注:该尺寸图片必须为90x150。
                </td>
            </tr>
            <tr>
                <td width="20%" class="pn-flabel pn-flabel-h"></td>
                <td width="80%" class="pn-fcontent">
                    <input type="text" name="image" id="image" hidden/>
                    <%--<div class="row cl">--%>
                    <%--<div class="formControls col-xs-8 col-sm-9">--%>
                    <%--<div class="uploader-list-container">--%>
                    <%--<div class="queueList">--%>
                    <%--<div id="dndArea" class="placeholder">--%>
                    <%--<div name="filePicker" id="filePicker-2"></div>--%>
                    <%--<p>或将照片拖到这里，最多可选5张</p>--%>
                    <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="statusBar" style="display:block;">--%>
                    <%--<div class="progress"><span class="text">0%</span> <span class="percentage"></span>--%>
                    <%--</div>--%>
                    <%--<div class="info"></div>--%>
                    <%--<div class="btns">--%>
                    <%--<div id="filePicker2"></div>--%>
                    <%--<div id="ctlBtn" class="uploadBtn">选择图片</div>--%>
                    <%--<input type="file" value="上传"/>--%>
                    <%--</div>--%>
                    <%--</div>--%>
                    <%--</div>--%>
                    <%--</div>--%>
                    <%--</div>--%>
                    <!--dom结构部分-->
                    <div class="row cl">
                        <div class="formControls col-xs-8 col-sm-9">
                            <div class="uploader-list-container">
                                <div class="queueList">
                                    <div id="dndArea" class="placeholder">
                                        <div name="filePicker" id="filePicker-2"></div>
                                        <p>或将照片拖到这里，最多可选5张</p>
                                    </div>
                                </div>
                                <div class="statusBar" style="display:none;">
                                    <div class="progress"><span class="text">0%</span> <span class="percentage"></span>
                                    </div>
                                    <div class="info"></div>
                                    <div class="btns">
                                        <div id="filePicker2"></div>
                                        <div class="uploadBtn">开始上传</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            </tbody>
            <tbody id="tab_2" style="display: none">
            <tr>
                <td>
                    <textarea rows="10" cols="10" id="productdesc" name="description"></textarea>
                </td>
            </tr>
            </tbody>
            <tbody id="tab_3" style="display: none">
            <tr>
                <td>
                    <textarea rows="15" cols="136" id="productList" name="packageList"></textarea>
                </td>
            </tr>
            </tbody>
            <tbody>
            <tr>
                <td class="pn-fbutton" colspan="2">
                    <input type="submit" class="layui-btn layui-btn-normal" value="保存商品"/> &nbsp; <input type="reset"
                                                                                                         class="layui-btn layui-btn-danger"
                                                                                                         value="重置商品"/>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
</body>
</html>
<script>
    jQuery.validator.addMethod("weightValue", function (value, element) {
        var weightValue = /^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,2})?$/;
        return this.optional(element) || (weightValue.test(value));
    }, "必须输入数字");

    var images = null;
    //保存发布
    $("#jvForm").validate({
        rules: {
            typeId: {
                required: true,
            },
            brandId: {
                required: true,
            },
            name: {
                required: true,
            },
            weight: {
                weightValue: true,
                required: true,
            },
            feature: {
                required: true,

            },
            size: {
                required: true,
            },
            color: {
                required: true,
            }
        },
        onkeyup: false,
        focusCleanup: false,
        success: "valid",
        errorPlacement: function (error, element) {
            if (element.is(":checkbox") || element.is(":radio") || element.is(":input") || element.is(":select")) {
                error.appendTo(element.parent().find("span"));
            } else {
                error.insertAfter(element);
            }
        },
        submitHandler: function (form) {
            if (images == null) {
                layer.alert('请上传商品展示缩略图! ', {title: '错误信息', icon: 0});
                return;
            }
            var index = layer.load(3);
            var sizes = "",
                colors = "",
                features = "";
            $("input[name=size]:checked").each(function (e, i) {
                sizes += $(this).val() + ","
            });
            $("input[name=color]:checked").each(function (e, i) {
                colors += $(this).val() + ","

            });
            $("input[name=feature]:checked").each(function (e, i) {
                features += $(this).val() + ","
            });
            $.ajax({
                type: "post",
                url: "${pageContext.request.contextPath}/product/add",
                data: {
                    typeId: $("#typeId").val(),
                    name: $("input[name=name]").val(),
                    brandId: $("#brandId").val(),
                    weight: $("input[name=weight]").val(),
                    isNew: $("input[name=isNew]").val(),
                    isHot: $("input[name=isHot]").val(),
                    size: sizes.substring(0, sizes.length - 1),
                    color: colors.substring(0, colors.length - 1),
                    feature: features.substring(0, features.length - 1),
                    image: images
                },
                success: function (data) {
                    if (data.status == 1) {
                        layer.msg(data.message, {
                            icon: 1,
                            time: 2000
                        }, function () {
                            window.location = "${pageContext.request.contextPath}/product/list";
                        })
                    } else {
                        layer.msg(data.message, {
                            icon: 2,
                            time: 2000
                        })
                    }
                },
                error: function () {
                    layer.msg("系统繁忙请重试", {
                        icon: 2,
                        time: 2000
                    })
                }
            });
        }
    });

    (function ($) {
        // 当domReady的时候开始初始化
        $(function () {
            var $wrap = $('.uploader-list-container'),

                // 图片容器
                $queue = $('<ul class="filelist"></ul>')
                    .appendTo($wrap.find('.queueList')),

                // 状态栏，包括进度和控制按钮
                $statusBar = $wrap.find('.statusBar'),

                // 文件总体选择信息。
                $info = $statusBar.find('.info'),

                // 上传按钮
                $upload = $wrap.find('.uploadBtn'),

                // 没选择文件之前的内容。
                $placeHolder = $wrap.find('.placeholder'),

                $progress = $statusBar.find('.progress').hide(),

                // 添加的文件数量
                fileCount = 0,

                // 添加的文件总大小
                fileSize = 0,

                // 优化retina, 在retina下这个值是2
                ratio = window.devicePixelRatio || 1,

                // 缩略图大小
                thumbnailWidth = 110 * ratio,
                thumbnailHeight = 110 * ratio,

                // 可能有pedding, ready, uploading, confirm, done.
                state = 'pedding',

                // 所有文件的进度信息，key为file id
                percentages = {},
                // 判断浏览器是否支持图片的base64
                isSupportBase64 = (function () {
                    var data = new Image();
                    var support = true;
                    data.onload = data.onerror = function () {
                        if (this.width != 1 || this.height != 1) {
                            support = false;
                        }
                    }
                    data.src = "data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///ywAAAAAAQABAAACAUwAOw==";
                    return support;
                })(),

                // 检测是否已经安装flash，检测flash的版本
                flashVersion = (function () {
                    var version;

                    try {
                        version = navigator.plugins['Shockwave Flash'];
                        version = version.description;
                    } catch (ex) {
                        try {
                            version = new ActiveXObject('ShockwaveFlash.ShockwaveFlash')
                                .GetVariable('$version');
                        } catch (ex2) {
                            version = '0.0';
                        }
                    }
                    version = version.match(/\d+/g);
                    return parseFloat(version[0] + '.' + version[1], 10);
                })(),

                supportTransition = (function () {
                    var s = document.createElement('p').style,
                        r = 'transition' in s ||
                            'WebkitTransition' in s ||
                            'MozTransition' in s ||
                            'msTransition' in s ||
                            'OTransition' in s;
                    s = null;
                    return r;
                })(),

                // WebUploader实例
                uploader;

            if (!WebUploader.Uploader.support('flash') && WebUploader.browser.ie) {

                // flash 安装了但是版本过低。
                if (flashVersion) {
                    (function (container) {
                        window['expressinstallcallback'] = function (state) {
                            switch (state) {
                                case 'Download.Cancelled':
                                    alert('您取消了更新！')
                                    break;

                                case 'Download.Failed':
                                    alert('安装失败')
                                    break;

                                default:
                                    alert('安装已成功，请刷新！');
                                    break;
                            }
                            delete window['expressinstallcallback'];
                        };

                        var swf = 'expressInstall.swf';
                        // insert flash object
                        var html = '<object type="application/' +
                            'x-shockwave-flash" data="' + swf + '" ';

                        if (WebUploader.browser.ie) {
                            html += 'classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" ';
                        }

                        html += 'width="100%" height="100%" style="outline:0">' +
                            '<param name="movie" value="' + swf + '" />' +
                            '<param name="wmode" value="transparent" />' +
                            '<param name="allowscriptaccess" value="always" />' +
                            '</object>';

                        container.html(html);

                    })($wrap);

                    // 压根就没有安转。
                } else {
                    $wrap.html('<a href="http://www.adobe.com/go/getflashplayer" target="_blank" border="0"><img alt="get flash player" src="http://www.adobe.com/macromedia/style_guide/images/160x41_Get_Flash_Player.jpg" /></a>');
                }

                return;
            } else if (!WebUploader.Uploader.support()) {
                alert('Web Uploader 不支持您的浏览器！');
                return;
            }

            // 实例化
            uploader = WebUploader.create({
                pick: {
                    id: '#filePicker-2',
                    label: '点击选择图片'
                },
                formData: {
                    uid: 123
                },
                dnd: '#dndArea',
                paste: '#uploader',
                swf: '${pageContext.request.contextPath}/res/webuploader/0.1.5/Uploader.swf',
                chunked: false,
                chunkSize: 512 * 1024,
                server: '${pageContext.request.contextPath}/upload/uploadPic',
                // runtimeOrder: 'flash',

                accept: {
                    title: 'Images',
                    extensions: 'gif,jpg,jpeg,bmp,png',
                    //mimeTypes: 'image/*'
                },

                // 禁掉全局的拖拽功能。这样不会出现图片拖进页面的时候，把图片打开。
                disableGlobalDnd: true,
                fileNumLimit: 5,
                fileSizeLimit: 25 * 1024 * 1024,    // 25 M
                fileSingleSizeLimit: 5 * 1024 * 1024    // 5 M
            });

            // 拖拽时不接受 js, txt 文件。
            uploader.on('dndAccept', function (items) {
                var denied = false,
                    len = items.length,
                    i = 0,
                    // 修改js类型
                    unAllowed = 'text/plain;application/javascript ';

                for (; i < len; i++) {
                    // 如果在列表里面
                    if (~unAllowed.indexOf(items[i].type)) {
                        denied = true;
                        break;
                    }
                }

                return !denied;
            });

            uploader.on('dialogOpen', function () {
                console.log('here');
            });

            // uploader.on('filesQueued', function() {
            //     uploader.sort(function( a, b ) {
            //         if ( a.name < b.name )
            //           return -1;
            //         if ( a.name > b.name )
            //           return 1;
            //         return 0;
            //     });
            // });

            // 添加“添加文件”的按钮，
            uploader.addButton({
                id: '#filePicker2',
                label: '继续添加'
            });

            uploader.on('ready', function () {
                window.uploader = uploader;
            });

            // 当有文件添加进来时执行，负责view的创建
            function addFile(file) {
                var $li = $('<li id="' + file.id + '">' +
                    '<p class="title">' + file.name + '</p>' +
                    '<p class="imgWrap"></p>' +
                    '<p class="progress"><span></span></p>' +
                    '</li>'),

                    $btns = $('<div class="file-panel">' +
                        '<span class="cancel">删除</span>' +
                        '<span class="rotateRight">向右旋转</span>' +
                        '<span class="rotateLeft">向左旋转</span></div>').appendTo($li),
                    $prgress = $li.find('p.progress span'),
                    $wrap = $li.find('p.imgWrap'),
                    $info = $('<p class="error"></p>'),

                    showError = function (code) {
                        switch (code) {
                            case 'exceed_size':
                                text = '文件大小超出';
                                break;

                            case 'interrupt':
                                text = '上传暂停';
                                break;

                            default:
                                text = '上传失败，请重试';
                                break;
                        }

                        $info.text(text).appendTo($li);
                    };

                if (file.getStatus() === 'invalid') {
                    showError(file.statusText);
                } else {
                    // @todo lazyload
                    $wrap.text('预览中');
                    uploader.makeThumb(file, function (error, src) {
                        var img;

                        if (error) {
                            $wrap.text('不能预览');
                            return;
                        }

                        if (isSupportBase64) {
                            img = $('<img src="' + src + '">');
                            $wrap.empty().append(img);
                        } else {
                            $.ajax('lib/webuploader/0.1.5/server/preview.php', {
                                method: 'POST',
                                data: src,
                                dataType: 'json'
                            }).done(function (response) {
                                if (response.result) {
                                    img = $('<img src="' + response.result + '">');
                                    $wrap.empty().append(img);
                                } else {
                                    $wrap.text("预览出错");
                                }
                            });
                        }
                    }, thumbnailWidth, thumbnailHeight);

                    percentages[file.id] = [file.size, 0];
                    file.rotation = 0;
                }

                file.on('statuschange', function (cur, prev) {
                    if (prev === 'progress') {
                        $prgress.hide().width(0);
                    } else if (prev === 'queued') {
                        $li.off('mouseenter mouseleave');
                        $btns.remove();
                    }

                    // 成功
                    if (cur === 'error' || cur === 'invalid') {
                        console.log(file.statusText);
                        showError(file.statusText);
                        percentages[file.id][1] = 1;
                    } else if (cur === 'interrupt') {
                        showError('interrupt');
                    } else if (cur === 'queued') {
                        percentages[file.id][1] = 0;
                    } else if (cur === 'progress') {
                        $info.remove();
                        $prgress.css('display', 'block');
                    } else if (cur === 'complete') {
                        $li.append('<span class="success"></span>');
                    }

                    $li.removeClass('state-' + prev).addClass('state-' + cur);
                });

                $li.on('mouseenter', function () {
                    $btns.stop().animate({height: 30});
                });

                $li.on('mouseleave', function () {
                    $btns.stop().animate({height: 0});
                });

                $btns.on('click', 'span', function () {
                    var index = $(this).index(),
                        deg;

                    switch (index) {
                        case 0:
                            uploader.removeFile(file);
                            return;

                        case 1:
                            file.rotation += 90;
                            break;

                        case 2:
                            file.rotation -= 90;
                            break;
                    }

                    if (supportTransition) {
                        deg = 'rotate(' + file.rotation + 'deg)';
                        $wrap.css({
                            '-webkit-transform': deg,
                            '-mos-transform': deg,
                            '-o-transform': deg,
                            'transform': deg
                        });
                    } else {
                        $wrap.css('filter', 'progid:DXImageTransform.Microsoft.BasicImage(rotation=' + (~~((file.rotation / 90) % 4 + 4) % 4) + ')');
                    }


                });

                $li.appendTo($queue);
            }

            // 负责view的销毁
            function removeFile(file) {
                var $li = $('#' + file.id);

                delete percentages[file.id];
                updateTotalProgress();
                $li.off().find('.file-panel').off().end().remove();
            }

            function updateTotalProgress() {
                var loaded = 0,
                    total = 0,
                    spans = $progress.children(),
                    percent;

                $.each(percentages, function (k, v) {
                    total += v[0];
                    loaded += v[0] * v[1];
                });

                percent = total ? loaded / total : 0;


                spans.eq(0).text(Math.round(percent * 100) + '%');
                spans.eq(1).css('width', Math.round(percent * 100) + '%');
                updateStatus();
            }

            function updateStatus() {
                var text = '', stats;

                if (state === 'ready') {
                    text = '选中' + fileCount + '张图片，共' +
                        WebUploader.formatSize(fileSize) + '。';
                } else if (state === 'confirm') {
                    stats = uploader.getStats();
                    if (stats.uploadFailNum) {
                        text = '已成功上传' + stats.successNum + '张照片，' +
                            stats.uploadFailNum + '张照片上传失败，<a class="retry" href="#">重新上传</a>失败图片或<a class="ignore" href="#">忽略</a>'
                    }

                } else {
                    stats = uploader.getStats();
                    text = '共' + fileCount + '张（' +
                        WebUploader.formatSize(fileSize) +
                        '），已上传' + stats.successNum + '张';

                    if (stats.uploadFailNum) {
                        text += '，失败' + stats.uploadFailNum + '张';
                    }
                }

                $info.html(text);
            }

            function setState(val) {
                var file, stats;

                if (val === state) {
                    return;
                }

                $upload.removeClass('state-' + state);
                $upload.addClass('state-' + val);
                state = val;

                switch (state) {
                    case 'pedding':
                        $placeHolder.removeClass('element-invisible');
                        $queue.hide();
                        $statusBar.addClass('element-invisible');
                        uploader.refresh();
                        break;

                    case 'ready':
                        $placeHolder.addClass('element-invisible');
                        $('#filePicker2').removeClass('element-invisible');
                        $queue.show();
                        $statusBar.removeClass('element-invisible');
                        uploader.refresh();
                        break;

                    case 'uploading':
                        $('#filePicker2').addClass('element-invisible');
                        $progress.show();
                        $upload.text('暂停上传');
                        break;

                    case 'paused':
                        $progress.show();
                        $upload.text('继续上传');
                        break;

                    case 'confirm':
                        $progress.hide();
                        $('#filePicker2').removeClass('element-invisible');
                        $upload.text('开始上传');

                        stats = uploader.getStats();
                        if (stats.successNum && !stats.uploadFailNum) {
                            setState('finish');
                            return;
                        }
                        break;
                    case 'finish':
                        stats = uploader.getStats();
                        if (stats.successNum) {
                            //alert('上传成功');
                        } else {
                            // 没有成功的图片，重设
                            state = 'done';
                            location.reload();
                        }
                        break;
                }

                updateStatus();
            }

            uploader.onUploadProgress = function (file, percentage) {
                var $li = $('#' + file.id),
                    $percent = $li.find('.progress span');

                $percent.css('width', percentage * 100 + '%');
                percentages[file.id][1] = percentage;
                updateTotalProgress();
            };

            uploader.onFileQueued = function (file) {
                fileCount++;
                fileSize += file.size;

                if (fileCount === 1) {
                    $placeHolder.addClass('element-invisible');
                    $statusBar.show();
                }

                addFile(file);
                setState('ready');
                updateTotalProgress();
            };

            uploader.onFileDequeued = function (file) {
                fileCount--;
                fileSize -= file.size;

                if (!fileCount) {
                    setState('pedding');
                }

                removeFile(file);
                updateTotalProgress();

            };
            // 文件上传成功
            uploader.on('uploadSuccess', function (file, data) {

                if (data.status == 1) {
                    if (images == null) {
                        images = data.data;
                    } else {
                        images += "," + data.data;
                    }
                    $("#image").val(images);
                } else {
                    layer.msg("上传失败:" + data.message)
                }

            });


            uploader.on('all', function (type) {
                var stats;
                switch (type) {
                    case 'uploadFinished':
                        setState('confirm');
                        break;

                    case 'startUpload':
                        setState('uploading');
                        break;

                    case 'stopUpload':
                        setState('paused');
                        break;

                }
            });

            uploader.onError = function (code) {
                if (code == "Q_TYPE_DENIED") {
                    layer.msg("文件类型不支持，仅支持gif,jpg,jpeg,bmp,png格式图片");
                } else if (code == "F_DUPLICATE") {
                    layer.msg("文件已选中，请勿重复上传");
                } else if (code = "F_EXCEED_SIZE") {
                    layer.msg("文件大小超出限制，单张图片不得超过5MB");
                } else {
                    layer.msg('Error: ' + code);
                }

            };

            $upload.on('click', function () {
                if ($(this).hasClass('disabled')) {
                    return false;
                }

                if (state === 'ready') {
                    uploader.upload();
                } else if (state === 'paused') {
                    uploader.upload();
                } else if (state === 'uploading') {
                    uploader.stop();
                }
            });

            $info.on('click', '.retry', function () {
                uploader.retry();
            });

            $info.on('click', '.ignore', function () {
                alert('已忽略');
            });

            $upload.addClass('state-' + state);
            updateTotalProgress();
        });

    })(jQuery);
    var images = null;
</script>