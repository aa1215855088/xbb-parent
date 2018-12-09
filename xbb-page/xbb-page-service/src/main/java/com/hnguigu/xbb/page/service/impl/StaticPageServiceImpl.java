package com.hnguigu.xbb.page.service.impl;

import com.hnguigu.xbb.common.exception.XbbException;
import com.hnguigu.xbb.page.service.StaticPageService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.ServletContext;
import java.io.*;
import java.util.Map;

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
 * @create: 2018-11-12 17:54
 **/

public class StaticPageServiceImpl implements StaticPageService, ServletContextAware {
    //声明
    //注入
    private Configuration conf;

    public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
        this.conf = freeMarkerConfigurer.getConfiguration();
    }

    private ServletContext servletContext;

    //获取路径
    private String getPath(String name) {
        return servletContext.getRealPath(name);
    }

    //声明
    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Override
    public void generateStaticPages(Map<String, Object> root, Integer id) {
        String path = this.getPath("/html/product/" + id + ".html");
        File file = new File(path);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        Writer out = null;

        try {
            Template template = conf.getTemplate("product.ftl");

            out = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");

            template.process(root, out);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != out) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }

    @Override
    public void delPages(Integer[] ids) {
        for (Integer id : ids) {
            String path = this.getPath("/html/product/" + id + ".html");
            File file = new File(path);
            if (!file.delete()) {
                throw new XbbException("同步" + id + ".html页面失败");
            }
        }
    }


}
