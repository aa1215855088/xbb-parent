package com.hnguigu.xbb.common.util;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: zshop
 * @description: ${description}
 * @author: 徐子楼
 * @create: 2018-09-15 08:06
 **/
public class GeneratingOrderUtil {

    /**
     * 通过用户ID和当前时间生成
     *
     * @param loginName
     * @return
     */
    public synchronized static String generateOrder(String loginName) {
        return loginName + getDate();
    }


    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
        String date = format.format(new Date());
        return date;
    }

}
