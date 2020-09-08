package com.atguigu.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.PmsBaseAttrInfo;
import com.atguigu.gmall.bean.PmsBaseAttrValue;
import com.atguigu.gmall.bean.PmsBaseSaleAttr;
import com.atguigu.gmall.service.AttrService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@CrossOrigin
public class AttrController {

    @Reference
    AttrService attrService;


    @RequestMapping("attrInfoList")
    @ResponseBody
    /**
     * @Description: 获取平台属性列表
     * @CeateTime: 2020/9/8 20:59
     * @Param: [catalog3Id]
     * @Return java.util.List<com.atguigu.gmall.bean.PmsBaseAttrInfo>
     */
    public List<PmsBaseAttrInfo> attrInfoList(String catalog3Id){
        List<PmsBaseAttrInfo> pmsBaseAttrInfos = attrService.attrInfoList(catalog3Id);
        return pmsBaseAttrInfos;
    }


    @RequestMapping("saveAttrInfo")
    @ResponseBody
    /**
     * @Description: 添加平台属性与值
     * @CeateTime: 2020/9/8 21:26
     * @Param: [pmsBaseAttrInfo]
     * @Return java.lang.String
     */
    public String saveAttrInfo(@RequestBody PmsBaseAttrInfo pmsBaseAttrInfo){
        String success = attrService.saveAttrInfo(pmsBaseAttrInfo);
        return success;
    }

    @RequestMapping("getAttrValueList")
    @ResponseBody
    /**
     * @Description: 根据平台属性id获取平台属性值
     * @CeateTime: 2020/9/8 21:25
     * @Param: [attrId]
     * @Return java.util.List<com.atguigu.gmall.bean.PmsBaseAttrValue>
     */
    public List<PmsBaseAttrValue> getAttrValueList(String attrId){
        List<PmsBaseAttrValue> pmsBaseAttrValues = attrService.getAttrValueList(attrId);
        return pmsBaseAttrValues;
    }

    @RequestMapping("baseSaleAttrList")
    @ResponseBody
    /**
     * @Description: 获取所有商品属性
     * @CeateTime: 2020/9/8 21:27
     * @Param: []
     * @Return java.util.List<com.atguigu.gmall.bean.PmsBaseSaleAttr>
     */
    public List<PmsBaseSaleAttr> baseSaleAttrList(){
        List<PmsBaseSaleAttr> pmsBaseSaleAttrList = attrService.baseSaleAttrList();
        return pmsBaseSaleAttrList;
    }
}
