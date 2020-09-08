package com.atguigu.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.PmsBaseCatalog1;
import com.atguigu.gmall.bean.PmsBaseCatalog2;
import com.atguigu.gmall.bean.PmsBaseCatalog3;
import com.atguigu.gmall.service.CatalogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@CrossOrigin
public class CatalogController {

    @Reference
    CatalogService catalogService;

    @RequestMapping("getCatalog1")
    @ResponseBody
    /**
     * @Description: 获取平台属性的一级分类
     * @CeateTime: 2020/9/8 21:21
     * @Param: []
     * @Return java.util.List<com.atguigu.gmall.bean.PmsBaseCatalog1>
     */
    public List<PmsBaseCatalog1> getCatalog1(){
        List<PmsBaseCatalog1> catalog1s = catalogService.getCatalog1();
        return catalog1s;
    }

    @RequestMapping("getCatalog2")
    @ResponseBody
    /**
     * @Description: 根据一级分类id获取平台属性的二级分类
     * @CeateTime: 2020/9/8 21:22
     * @Param: [catalog1Id]
     * @Return java.util.List<com.atguigu.gmall.bean.PmsBaseCatalog2>
     */
    public List<PmsBaseCatalog2> getCatalog2(String catalog1Id){
        List<PmsBaseCatalog2> catalog2s = catalogService.getCatalog2(catalog1Id);
        return catalog2s;
    }

    @RequestMapping("getCatalog3")
    @ResponseBody
    /**
     * @Description: 根据二级分类id获取平台属性的三级分类
     * @CeateTime: 2020/9/8 21:23
     * @Param: [catalog2Id]
     * @Return java.util.List<com.atguigu.gmall.bean.PmsBaseCatalog3>
     */
    public List<PmsBaseCatalog3> getCatalog3(String catalog2Id){
        List<PmsBaseCatalog3> catalog3s = catalogService.getCatalog3(catalog2Id);
        return catalog3s;
    }
}
