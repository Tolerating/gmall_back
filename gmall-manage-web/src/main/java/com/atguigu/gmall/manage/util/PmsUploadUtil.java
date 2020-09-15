package com.atguigu.gmall.manage.util;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class PmsUploadUtil {
    public static String uploadImage(MultipartFile multipartFile) {
        String imgUrl = "http://192.168.3.70";
        String file = PmsUploadUtil.class.getResource("/tracker.conf").getFile();
        try {
            ClientGlobal.init(file);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        TrackerClient trackerClient=new TrackerClient();
        TrackerServer trackerServer= null;
        try {
            trackerServer = trackerClient.getTrackerServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
        StorageClient storageClient=new StorageClient(trackerServer,null);
        //String originalFilename="e://victor.jpg";
        try {
            byte[] bytes = multipartFile.getBytes(); //获得上传图片的二进制对象
            String originalFilename = multipartFile.getOriginalFilename();
            int i = originalFilename.lastIndexOf(".");
            originalFilename.substring(i+1);
            String[] upload_file = storageClient.upload_file(bytes, "jpg", null);
            for (String uploadInfo : upload_file) {
                imgUrl += "/"+uploadInfo;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }

        return imgUrl;
    }
}
