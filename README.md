哔哩哔哩视频看到：https://www.bilibili.com/video/BV1LK4y1b7Ds?p=17

![域名的配置](https://www.bilibili.com/video/BV1LK4y1b7Ds?p=9)

## 项目结构
```text
gmall-parent : 统一管理pom依赖
```
### `gmall-parent`新建为`maven`项目
```xml
<groupId>com.atguigu.gmall</groupId>
<artifactId>gmall-parent</artifactId>
<version>1.0-SNAPSHOT</version>
```

## Idea @AutoWiried 误报
Settings --> 搜索“inspect” --> spring --> code --> [AutoWiring for Bean Class]不打勾

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.atguigu</groupId>
    <artifactId>gmall</artifactId>
    <version>1.0-SNAPSHOT</version>


</project>
```