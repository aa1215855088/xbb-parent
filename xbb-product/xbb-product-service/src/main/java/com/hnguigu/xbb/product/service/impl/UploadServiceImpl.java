package com.hnguigu.xbb.product.service.impl;

import com.hnguigu.xbb.common.exception.XbbUploadException;
import com.hnguigu.xbb.common.util.FastDFSUtils;
import com.hnguigu.xbb.product.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
 * @create: 2018-11-10 14:29
 **/
@Service("uploadService")
public class UploadServiceImpl implements UploadService {

    @Value("${fastdfs.host}")
    private String fastDfsHost;

    @Override
    public String fastDFSUploadPic(byte[] pic, String name, long size) throws XbbUploadException {
        String storagePath = FastDFSUtils.uploadPic(pic, name, size);
        String path = fastDfsHost + storagePath;
        return path;
    }
}
