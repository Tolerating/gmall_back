哔哩哔哩视频看到：https://www.bilibili.com/video/BV1LK4y1b7Ds?p=80（已看完）

**寒假再接着学**

[域名的配置](https://www.bilibili.com/video/BV1LK4y1b7Ds?p=9)

* gmall-user-service 用户服务的service层 8070
* gmall-user-web 用户服务的web层 8080

* gmall-manage-service 后台管理服务的service层 8071
* gmall-manage-web 后台管理服务的web层 8081

* gmall-item-service 前台的商品详情服务的service层 8072
* gmall-item-web 前台的商品详情服务的web层 8082

## 项目结构
```text
gmall-parent : 统一管理pom依赖
gmall-api   : 管理数据库实体类（bean）和service接口
gmall-common-util   : 通用型第三方包，是所有应用工程需要引入的包

```
> 注意：当新建一个模块时，maven的groupid应与其他模块保持一致，例如本项目的统一为com.atguigu.gmall
### `gmall-parent`新建为`maven`项目
```xml
<groupId>com.atguigu.gmall</groupId>
<artifactId>gmall-parent</artifactId>
<version>1.0-SNAPSHOT</version>
```


## 技术栈

 技术栈|版本|描述
 ----|---|----
 zookeeper|3.4.14|分布式的架构
 dubbo|2.6.5| 分布式架构间的通讯
 fastdfs|5.0.5| 分布式文件系统
 


## Idea @AutoWiried 误报
Settings --> 搜索“inspect” --> spring --> code --> [AutoWiring for Bean Class]不打勾

## 5.抽取util工程
1.项目中的通用框架，是所有应用工程需要引入的包
* 例如spring boot、common-langs、common-beautils

2.基于soa的架构理念，项目分为web前端的controller
* Jsp thymeleaf

3.基于soa的架构理念，项目分为web后端的service
* Mybatis、mysql、redis

## 6.soa面向服务（以dubbo为基础）
1. dubbo的soa工作原理，和springcloud类似
2. dubbo和springcloud的区别在于dubbo由自己的dubbo协议通讯，springcloud是由http协议（rest风格）
3. dubbo有一个注册中心的客户端在时时同步注册中心的服务信息
4. dubbo有一个java web的监控中心，负责监控服务的注册信息，甚至可以设置负载均衡


## 解决前后端跨域问题
在controller层加入`@CrossOrigin`的跨域注解，自动在响应头中加入`Access-Control-Allow-Origin`