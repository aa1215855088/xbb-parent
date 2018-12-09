package com.hnguigu.xbb.page.message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.hnguigu.xbb.page.service.ContentService;
import com.hnguigu.xbb.page.service.StaticPageService;
import com.hnguigu.xbb.product.domain.Color;
import com.hnguigu.xbb.product.domain.Product;
import com.hnguigu.xbb.product.domain.Sku;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.*;

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
 * @create: 2018-11-12 16:45
 **/
public class PageMessageListener implements MessageListener {

    @Autowired
    private StaticPageService staticPageService;
    @Autowired
    private ContentService contentService;

    @Override
    public void onMessage(Message message) {
        ActiveMQTextMessage am = (ActiveMQTextMessage) message;
        try {
            String jsonStrIds = am.getText();
            //数据
            Integer[] ids = JSON.parseArray(jsonStrIds).toArray(new Integer[0]);

            for (Integer id : ids) {
                Map<String, Object> root = new HashMap<>();

                Product product = this.contentService.findProductByProductId(id);

                List<Sku> skuList = this.contentService.findSkuByProductIdAndInventoryNotNull(id);
                Set<Color> colors = new HashSet<>();
                for (Sku sku : skuList) {
                    colors.add(sku.getColor());
                }
                root.put("colors", colors);
                root.put("product", product);
                root.put("skus", skuList);
                root.put("keywords", "");
                this.staticPageService.generateStaticPages(root, id);
            }

        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
