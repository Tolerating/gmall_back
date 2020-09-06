哔哩哔哩视频看到：https://www.bilibili.com/video/BV1LK4y1b7Ds?p=43（已看完）

[域名的配置](https://www.bilibili.com/video/BV1LK4y1b7Ds?p=9)

* gmall-user-service 用户服务的service层 8070
* gmall-user-web 用户服务的web层 8080

* gmall-manage-service 后台管理服务的service层 8071
* gmall-manage-web 后台管理服务的web层 8081

## 项目结构
```text
gmall-parent : 统一管理pom依赖
gmall-api   : 管理数据库实体类（bean）和service接口
gmall-common-util   : 通用型第三方包，是所有应用工程需要引入的包

```
### `gmall-parent`新建为`maven`项目
```xml
<groupId>com.atguigu.gmall</groupId>
<artifactId>gmall-parent</artifactId>
<version>1.0-SNAPSHOT</version>
```

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
1.dubbo的soa工作原理，和springcloud类似
2.dubbo和springcloud的区别在于dubbo由自己的dubbo协议通讯，springcloud是由http协议（rest风格）
3.dubbo有一个注册中心的客户端在时时同步注册中心的服务信息
4.dubbo有一个java web的监控中心，负责监控服务的注册信息，甚至可以设置负载均衡

## 7. 将项目改造成dubbo的分布式架构
```xml
<!--1.将user项目拆分成user-service和user-web-->
<!--2.引入dubbo框架（引入到common-util中，因为web层和service层将来都需要使用dubbo进行通讯）-->
<project>
    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>dubbo</artifactId>
<!-- 2.7.1的版本        -->
    </dependency>
    <dependency>
        <groupId>com.101tec</groupId>
        <artifactId>zkclient</artifactId>
        <exclusions>
            <exclusion>
                <groupId>org.slf4j</groupId>
                <artifactId>slf4j-log4j12</artifactId>
            </exclusion>
        </exclusions>
    </dependency>
    <dependency>
        <groupId>com.alibaba.boot</groupId>
        <artifactId>dubbo-spring-boot-starter</artifactId>
        <version>0.2.1.RELEASE</version>
    </dependency>
    <dependency>
        <groupId>org.apache.curator</groupId>
        <artifactId>curator-framework</artifactId>
        <version>2.12.0</version>
    </dependency>
</project>
```
### 1.将服务提供者注册到zookeeper中
1.将`gmall-user-service`项目中Spring的@Service改成dubbo的@Service
2.配置dubbo的配置文件(`gmall-user-service`的)
```properties
# dubbo的配置
# dubbo中的服务名称
dubbo.application.name=user-service
# zookeeper注册中心的 地址
dubbo.registry.address=zookeeper://192.168.3.70:2181
# dubbo通讯协议名称
dubbo.protocol.name=dubbo
#zookeeper的通讯协议的名称
dubbo.registry.protocol=zookeeper
#dubo的服务的扫描路径
dubbo.scan.base-packages=com.atguigu.gmall

```
### 将gmall-user-web注册为服务消费者
1.将controller中的@Autowired改为@Reference（注意是dubbo的）
2.配置dubbo的配置文件(`gmall-user-web`的)
```properties
# 服务器端口
server.port=8080

# 日志级别
logging.level.root=info

# dubbo的配置
# dubbo中的服务名称
dubbo.application.name=user-web
# zookeeper注册中心的 地址
dubbo.registry.address=zookeeper://192.168.3.70:2181
# dubbo通讯协议名称
dubbo.protocol.name=dubbo
#zookeeper的通讯协议的名称
dubbo.registry.protocol=zookeeper
#dubo的服务的扫描路径
dubbo.scan.base-packages=com.atguigu.gmall
```
### dubbo配置的注意事项
1.spring的Service改为dubbo的Service
2.将controller层的autowired改为reference
3.dubbo在进行dubbo协议通讯时，需要实现序列化接口（封装的数据对象【bean】）
4.dubbo的消费者（consumer）在三秒内之内每隔一秒进行一次重新访问，默认一秒超时，三次访问之后会直接抛超时异常，我们在开发阶段，可以将consumer设置的超时时间延长，方便断点测试
```properties
#设置超时时间
dubbo.consumer.timeout=600000
# 设置是否检查服务存在
dubbo.consumer.check=false
```

## 解决前后端跨域问题
在controller层加入`@CrossOrigin`的跨域注解，自动在响应头中加入`Access-Control-Allow-Origin`