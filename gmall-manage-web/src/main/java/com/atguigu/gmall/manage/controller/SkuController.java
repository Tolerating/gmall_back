package com.atguigu.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.PmsSkuInfo;
import com.atguigu.gmall.service.SkuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@Controller
@CrossOrigin
public class SkuController {
    @Reference
    SkuService skuService;

    @RequestMapping("saveSkuInfo")
    @ResponseBody
    /**
     * @Description: 保存sku属性
     * @CeateTime: 2020/9/19 22:21
     * @Param: [pmsSkuInfo]
     * @Return java.lang.String
     */
    public String saveSkuInfo(@RequestBody PmsSkuInfo pmsSkuInfo){
        String skuDefaultImg = pmsSkuInfo.getSkuDefaultImg();
        if (StringUtils.isEmpty(skuDefaultImg)){
            pmsSkuInfo.setSkuDefaultImg(pmsSkuInfo.getSkuImageList().get(0).getImgUrl());
        }
        skuService.saveSkuInfo(pmsSkuInfo);
        return "success";
    }
}

