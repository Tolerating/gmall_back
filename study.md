哔哩哔哩视频看到：https://www.bilibili.com/video/BV1LK4y1b7Ds?p=8


# 每日疑惑
## 2020年8月26日

一.
```java
@SpringBootApplication
@MapperScan(basePackages = "com.atguigu.gmall.user.mapper")
public class GmallUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(GmallUserApplication.class, args);
    }

}
```
GmallUserApplication类中为何要加入`@MapperScan`?

二.
`@Autowired`的使用不是很明白？


## idea 配置mysql数据库插件（sql语句提醒等等）
Database --》加号 --》 Data Source --》 MySQL


## 通用mapper的整合（可以将单表的增删改查操作省去）
一、导入pom依赖
```xml
		<dependency>
			<groupId>tk.mybatis</groupId>
			<artifactId>mapper-spring-boot-starter</artifactId>
			<version>2.0.2</version>
			<exclusions>
			    <exclusion>
			        <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-jdbc</artifactId>
                </exclusion>
            </exclusions>
		</dependency>
```
> springBoot版本为：2.1.3

二、配置mapper，继承通用mapper
```java
package com.atguigu.gmall.user.mapper;

import com.atguigu.gmall.user.bean.UmsMember;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserMapper extends Mapper<UmsMember> {
    List<UmsMember> selectAllUser();
}

```

三、配置通用mapper的主键和主键返回策略
```java
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

public class UmsMember {

    @Id  
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
}
```

四、配置启动类扫描器MapperScan，使用通过mapper的tk....MapperScan
* `*Application.java`文件中配置
```java
package com.atguigu.gmall.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.atguigu.gmall.user.mapper")
public class GmallUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(GmallUserApplication.class, args);
    }

}
```

### 通用mapper的问题
* 实体类需要有相关的注解： `@table, @Id @Colunm`等等
* mapper扫描器需要使用tx...MapperScan


## idea 快捷键
```text
CTRL + ALT + L ; 格式化代码
Ctrl + Alt + O : 优化导入的类，可以对当前文件和整个包目录使用
CTRL + ENTER   : IntelliJ IDEA 根据光标所在问题，提供快速修复选择，光标放在的位置不同提示的结果也不同
Ctrl + Shift + / : 	代码块注释
Ctrl + / :	注释光标所在行代码
Ctrl + W : 选中光标处的单词
ALT + Insert : 快速生成代码（例如getter、setter）
Ctrl + Alt + B ; 将光标定位在接口名上，快速跳转到该接口的实现类中
```

## 通用mapper中的insert和insertSelective的区别：
* insert：全部插入数据库
* insertSelective：只将非空的有值的字段插入数据库
 


## 注解
```
@MapperScan(basePackages = "com.atguigu.gmall.user.mapper");

@RequestMapping("index");

@ResponseBody;

@Autowired;

@CrossOrigin ：返回体中携带跨域头
```

