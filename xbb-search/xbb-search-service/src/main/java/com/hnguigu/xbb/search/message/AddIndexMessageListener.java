package com.hnguigu.xbb.search.message;

import com.alibaba.fastjson.JSON;
import com.hnguigu.xbb.search.service.SearchService;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

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
 * @create: 2018-11-26 17:47
 **/
public class AddIndexMessageListener implements MessageListener {

    @Autowired
    private SearchService searchService;

    @Override
    public void onMessage(Message message) {
        ActiveMQTextMessage am = (ActiveMQTextMessage) message;

        try {
            String jsonStrIds = am.getText();
            Integer[] ids = JSON.parseArray(jsonStrIds).toArray(new Integer[0]);
            System.out.println("收到索引ID");
            this.searchService.addItems(ids);
            System.out.println("同步成功");
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
