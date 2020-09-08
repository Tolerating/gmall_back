package com.atguigu.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.PmsProductInfo;
import com.atguigu.gmall.service.SpuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@CrossOrigin
public class SpuController {
    @Reference
    SpuService spuService;

    @RequestMapping("spuList")
    @ResponseBody
    /**
     * @Description: 根据三级分类id获取spu
     * @CeateTime: 2020/9/8 21:28
     * @Param: [catalog3Id]
     * @Return java.util.List<com.atguigu.gmall.bean.PmsProductInfo>
     */
    public List<PmsProductInfo> spuList(String catalog3Id){
        List<PmsProductInfo> pmsProductInfos = spuService.spuList(catalog3Id);
        return pmsProductInfos;
    }

    @RequestMapping("saveSpuInfo")
    @ResponseBody
    /**
     * @Description: 添加spu
     * @CeateTime: 2020/9/8 21:33
     * @Param: [pmsProductInfo]
     * @Return java.lang.String
     */
    public String saveSpuInfo(@RequestBody PmsProductInfo pmsProductInfo){
        return "success";
    }

    @RequestMapping("fileUpload")
    @ResponseBody
    public String fileUpload(@RequestParam("file") MultipartFile multipartFile){
        // 将图片或视频上传至分布式的文件存储系统

        //将图片的存储路径返回给页面
        String imageUrl = "https://pics4.baidu.com/feed/86d6277f9e2f0708314c6551ac55f89ea801f28a.jpeg?token=546b4d16fd90e879272edbc2740a0a95";
        return imageUrl;
    }
}
