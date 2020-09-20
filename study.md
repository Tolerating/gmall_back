
## 每日疑惑
### 2020年8月26日

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


#### idea 配置mysql数据库插件（sql语句提醒等等）
Database --》加号 --》 Data Source --》 MySQL
### 20209月19日
springboot中接受文件用到的`MultipartFile`
```java
    @RequestMapping("fileUpload")
    @ResponseBody
    public String fileUpload(@RequestParam("file") MultipartFile multipartFile){
        // 将图片或视频上传至分布式的文件存储系统
        //将图片的存储路径返回给页面
        String imgUrl = PmsUploadUtil.uploadImage(multipartFile);
        System.out.println(imgUrl);
        return imgUrl;
    }
```


## springboot接收get和post请求
```java
    @RequestMapping("attrInfoList")
    @ResponseBody
    /**
     * @Description: get请求示例
     */
    public List<PmsBaseAttrInfo> attrInfoList(String catalog3Id){
        List<PmsBaseAttrInfo> pmsBaseAttrInfos = attrService.attrInfoList(catalog3Id);
        return pmsBaseAttrInfos;
    }
    
        @RequestMapping("saveAttrInfo")
        @ResponseBody
        /**
         * @Description: Post请求示例
         */
        public String saveAttrInfo(@RequestBody PmsBaseAttrInfo pmsBaseAttrInfo){
            String success = attrService.saveAttrInfo(pmsBaseAttrInfo);
            return success;
        }
```
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

## 分布式文件存储（fastdfs）的安装(按顺序执行以下步骤)
```text
// 安装依赖
yum install gcc-c++ -y
yum -y install libevent
yum install perl*
yum -y install zlib zlib-devel pcre pcre-devel gcc gcc-c++ openssl openssl-devel libevent libevent-devel perl unzip net-tools wget
```
### 1.依赖库
`Libfastcommon`
```text
1 上传压缩包文件libfastcommonV1.0.7.tar.gz 到 /usr/local目录下，并解压。
2 tar -zxvf libfastcommonV1.0.7.tar.gz
3 cd libfastcommon-1.0.7
4 ./make.sh
5 ./make.sh install
6 cp /usr/lib64/libfastcommon.so /usr/lib/

```
> 1.9注意：libfastcommon安装好后会自动将库文件拷贝至/usr/lib64下，由于FastDFS程序引用usr/lib目录所以需要将/usr/lib64下的库文件拷贝至/usr/lib下。
`cp /usr/lib64/libfastcommon.so /usr/lib/`

### 2.`fastdfs`软件（`tracker`和`storage`组成）
> `tracker`管理多个`storage`
* 2.1安装`tracker`
```text
1上传资料FastDFS_v5.05.tar.gz到 /usr/local 目录下
2解压编译安装
    2.1	tar -zxvf FastDFS_v5.05.tar.gz
    2.2	cd FastDFS
    2.3	./make.sh
    2.4	./make.sh install
    2.5	安装成功之后，将安装目录下的conf下的文件拷贝到/etc/fdfs/下。
    2.6	cd conf
    2.7	cp  *  /etc/fdfs/
3修改配置文件
    3.1	vi /etc/fdfs/tracker.conf
    # 软件数据存储目录
    base_path=/opt/fastdfs
4创建fastdfs文件夹
5mkdir /opt/fastdfs
```
> 依赖：`Gcc、libevent、perl`<br>


* 配置`storage`
> 安装`tracker`的时候已经安装了
```text
mkdir /opt/fastdfs/fdfs_storage
vi /etc/fdfs/storage.conf

# 软件数据存储目录
base_path=/opt/fastdfs

# Storage存储文件目录
store_path0=/opt/fastdfs/fdfs_storage

# Storage的tracker的ip地址（tracker的本机ip地址）
tracker_server=192.168.3.70:22122

```
### 3.配置tracker和storage的启动服务
* `tracker`
```text
mkdir  /usr/local/fdcdfs   
拷贝安装目录下stop.sh 和restart.sh 到/usr/local/fdfs/
cp restart.sh  /usr/local/fdfs/
cp stop.sh  /usr/local/fdfs/
# 修改启动脚本
vi /etc/init.d/fdfs_trackerd
```
```text
#!/bin/bash
#
# fdfs_trackerd Starts fdfs_trackerd
#
#
# chkconfig: 2345 99 01
# description: FastDFS tracker server
### BEGIN INIT INFO
# Provides: $fdfs_trackerd
### END INIT INFO

# Source function library.
. /etc/init.d/functions

PRG=/usr/bin/fdfs_trackerd
CONF=/etc/fdfs/tracker.conf

if [ ! -f $PRG ]; then
  echo "file $PRG does not exist!"
  exit 2
fi

if [ ! -f /usr/local/fdfs/stop.sh ]; then
  echo "file /usr/local/bin/stop.sh does not exist!"
  exit 2
fi

if [ ! -f /usr/local/fdfs/restart.sh ]; then
  echo "file /usr/local/bin/restart.sh does not exist!"
  exit 2
fi

if [ ! -f $CONF ]; then
  echo "file $CONF does not exist!"
  exit 2
fi

CMD="$PRG $CONF"
RETVAL=0

start() {
 	echo -n $"Starting FastDFS tracker server: "
	$CMD &
	RETVAL=$?
	echo
	return $RETVAL
}
stop() {
        /usr/local/fdfs/stop.sh $CMD
	RETVAL=$?
	return $RETVAL
}
rhstatus() {
	status fdfs_trackerd
}
restart() {
        /usr/local/fdfs/restart.sh $CMD &
}

case "$1" in
  start)
  	start
	;;
  stop)
  	stop
	;;
  status)
  	rhstatus
	;;
  restart|reload)
  	restart
	;;
  condrestart)
  	restart
	;;
  *)
	echo $"Usage: $0 {start|stop|status|restart|condrestart}"
	exit 1
esac

exit $?
```
修改完成后注册服务：`chkconfig  --add  fdfs_trackerd`<br>

* `storage`
```text
vi  /etc/init.d/fdfs_storaged
```
```text
#!/bin/bash
#
# fdfs_storaged Starts fdfs_storaged
#
#
# chkconfig: 2345 99 01
# description: FastDFS storage server
### BEGIN INIT INFO
# Provides: $fdfs_storaged
### END INIT INFO

# Source function library.
. /etc/init.d/functions

PRG=/usr/bin/fdfs_storaged
CONF=/etc/fdfs/storage.conf

if [ ! -f $PRG ]; then
  echo "file $PRG does not exist!"
  exit 2
fi

if [ ! -f /usr/local/fdfs/stop.sh ]; then
  echo "file /usr/local/bin/stop.sh does not exist!"
  exit 2
fi

if [ ! -f /usr/local/fdfs/restart.sh ]; then
  echo "file /usr/local/bin/restart.sh does not exist!"
  exit 2
fi

if [ ! -f $CONF ]; then
  echo "file $CONF does not exist!"
  exit 2
fi

CMD="$PRG $CONF"
RETVAL=0

start() {
 	echo -n $"Starting FastDFS storage server: "
	$CMD &
	RETVAL=$?
	echo
	return $RETVAL
}
stop() {
        /usr/local/fdfs/stop.sh $CMD
	RETVAL=$?
	return $RETVAL
}
rhstatus() {
	status fdfs_storaged
}
restart() {
        /usr/local/fdfs/restart.sh $CMD &
}

case "$1" in
  start)
  	start
	;;
  stop)
  	stop
	;;
  status)
  	rhstatus
	;;
  restart|reload)
  	restart
	;;
  condrestart)
  	restart
	;;
  *)
	echo $"Usage: $0 {start|stop|status|restart|condrestart}"
	exit 1
esac

exit $?
```
修改完成后注册服务：`chkconfig  --add  fdfs_storaged`<br>

### 4.上传图片测试
```text
# 修改/etc/fdfs/client.conf
1 vi /etc/fdfs/client.conf
    base_path=/opt/fastdfs
    tracker_server=192.168.3.70:22122
# 上传文件
/usr/bin/fdfs_test  /etc/fdfs/client.conf  upload  /root/winteriscoming.jpg

```
### 5.`FastDFS-nginx-module`
`fastdfs`整合`nginx`的插件
* 安装`nginx`整合插件`fastdfs-nginx-module`
```text
1 上传fastdfs-nginx-module_v1.16.tar.gz上传到 /usr/local，并解压
2 tar -zxvf fastdfs-nginx-module_v1.16.tar.gz
# 编辑配置文件：修改config文件将/usr/local/路径改为/usr/
3 vi fastdfs-nginx-module/src/config
    CORE_INCS="$CORE_INCS /usr/include/fastdfs /usr/include/fastcommon/"
    CORE_LIBS="$CORE_LIBS -L/usr/lib -lfastcommon -lfdfsclient"

# 将FastDFS-nginx-module/src下的mod_fastdfs.conf拷贝至/etc/fdfs/下
4 cp fastdfs-nginx-module/src/mod_fastdfs.conf /etc/fdfs/

5 vi /etc/fdfs/mod_fastdfs.conf
    base_path=/opt/fastdfs
    # tracker本机的ip地址
    tracker_server=192.168.3.70:22122
    url_have_group_name=true
    # 指定storage的文件存储路径
    store_path0=/opt/fastdfs/fdfs_storage
# 将libfdfsclient.so拷贝至/usr/lib下
6 cp /usr/lib64/libfdfsclient.so /usr/lib/
```
### 6.`nginx`
做`web`服务器，提供`http`请求服务
> 依赖：`pcre-devel、zlib-devel`
```text
# 创建nginx/client目录
1 mkdir -p /var/temp/nginx/client
# 上传nginx压缩包到/usr/local目录下，解压
2 tar -zxvf nginx-1.12.2.tar.gz
# 添加fastdfs-nginx-module模块
3 cd nginx-1.12.2    
./configure \
--prefix=/usr/local/nginx \
--pid-path=/var/run/nginx/nginx.pid \
--lock-path=/var/lock/nginx.lock \
--error-log-path=/var/log/nginx/error.log \
--http-log-path=/var/log/nginx/access.log \
--with-http_gzip_static_module \
--http-client-body-temp-path=/var/temp/nginx/client \
--http-proxy-temp-path=/var/temp/nginx/proxy \
--http-fastcgi-temp-path=/var/temp/nginx/fastcgi \
--http-uwsgi-temp-path=/var/temp/nginx/uwsgi \
--http-scgi-temp-path=/var/temp/nginx/scgi \
--add-module=/usr/local/fastdfs-nginx-module/src/

# 编译
4 make

# 安装(安装路径为--prefix指定的)
5 make install

# 编辑nginx.conf
6 vi /usr/local/nginx/conf/nginx.conf
  server_name  192.168.3.70;
  location /group1/M00 {
      #root   html;
      #index  index.html index.htm;
      ngx_fastdfs_module;
  }
# 启动nginx
7 /usr/local/nginx/sbin/nginx

# 设置开机启动
8 vi /etc/rc.d/rc.local
  添加  /usr/local/nginx/sbin/nginx 
# centos7永久关闭防火墙
    systemctl stop firewalld.service
9   systemctl disable firewalld.service //开机禁用防火墙

```
#### 出现的问题
1.每次电脑开机后重新启动`nginx`后都会报错：`nginx.pid`找不到
* 报错信息：`nginx: [emerg] open() "/var/run/nginx/nginx.pid" failed (2: No such file or directory)`
* 报错原因：原因就是每次重新启动，系统都会自动删除文件，
**解决办法**
```text
# 更改pid文件存储的位置

1 打开nginx.conf
2 将 pid   logs/nginx.pid  的注释打开
3 在nginx的根目录下新建logs文件夹
4 在sbin/路径下执行： ./nginx -c /usr/local/nginx/conf/nginx.conf

```
2.`nginx`开机未启动
因为`/etc/rc.d/rc.local`没有执行权限，只需给其附加上可执行权限即可
`chmod +x /etc/rc.d/rc.local`

> `/etc/rc.d/rc.local`，里面的命令写为执行文件的绝对路径
### 7.fastdfs客户端安装
> fastdfs没有在中心仓库中提供获取的依赖坐标，需要我们在git仓库中下载，然后打成jar包，安装到本地仓库中
[GitHub官方地址](https://github.com/happyfish100/fastdfs-client-java)

1.修改pom文件中的版本
```text
<groupId>org.csource</groupId>
<artifactId>fastdfs-client-java</artifactId>
<version>1.27</version>
```
2.安装到本地maven仓库中
```text
# 在项目的根目录下运行
mvn install
```
3.导入依赖
```text
<groupId>org.csource</groupId>
<artifactId>fastdfs-client-java</artifactId>
<version>1.27</version>
```
4.写一个测试程序
```java
@RunWith(SpringRunner.class)
@SpringBootTest
class GmallUesrServiceApplicationTests {
    @Test
    public void textFileUpload() throws IOException, MyException {
    // 获取tracker配置文件的路径(tracker.conf新建在resource中)
    String file = GmallUesrServiceApplicationTests.class.getResource("/tracker.conf").getFile();
    
        //tracker的配置文件路径
        ClientGlobal.init(file);
        TrackerClient trackerClient=new TrackerClient();
        TrackerServer trackerServer=trackerClient.getTrackerServer();
        StorageClient storageClient=new StorageClient(trackerServer,null);
        String orginalFilename="e://victor.jpg";
        String[] upload_file = storageClient.upload_file(orginalFilename, "jpg", null);
        for (int i = 0; i < upload_file.length; i++) {
            //文件上传成功后返回的是一个数组，需要进行拼接
            String s = upload_file[i];
            System.out.println("s = " + s);
        }
    
    }
}
```
**tracker.conf**
```text
tracker_server=192.168.3.70:22122

# 连接超时时间，针对socket套接字函数connect，默认为30秒
connect_timeout=30000

# 网络通讯超时时间，默认是60秒
network_timeout=60000

```

### 功能文件目录
```text
Opt/fastdfs 软件数据存储目录
Usr/local/fdfs 启动文件目录
Etc/fdfs 配置文件目录
Usr/bin/fdfs_trackerd 启动配置
Etc/init.d/fdfs_trackerd 启动服务脚本

```
## 接收图片
```java
import org.springframework.web.multipart.MultipartFile;
    @RequestMapping("fileUpload")
    @ResponseBody
    public String fileUpload(@RequestParam("file") MultipartFile multipartFile){
        return "success"
    }
```

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
 

## 将项目改造成dubbo的分布式架构
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
1. 将`gmall-user-service`项目中Spring的@Service改成dubbo的@Service
2. 配置dubbo的配置文件(`gmall-user-service`的)
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
1. 将controller中的@Autowired改为@Reference（注意是dubbo的）
2. 配置dubbo的配置文件(`gmall-user-web`的)
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
# 设置是否检查服务存在
dubbo.consumer.check=false
```
### dubbo配置的注意事项
1. spring的Service改为dubbo的Service
2. 将controller层的autowired改为reference
3. dubbo在进行dubbo协议通讯时，需要实现序列化接口（封装的数据对象【bean】）
4. dubbo的消费者（consumer）在三秒内之内每隔一秒进行一次重新访问，默认一秒超时，三次访问之后会直接抛超时异常，我们在开发阶段，可以将consumer设置的超时时间延长，方便断点测试
```properties
#设置超时时间
dubbo.consumer.timeout=600000
# 设置是否检查服务存在
dubbo.consumer.check=false
```

## idea设置单行注释插入到光标前面
* 依次打开`file --> settings --> editor --> code style --> java --> code generation`
> 将comment code里的勾都去掉

## 注解
```
@MapperScan(basePackages = "com.atguigu.gmall.user.mapper");

@RequestMapping("index");

@Reference

@ResponseBody;

@Autowired;

@CrossOrigin ：返回体中携带跨域头

@RequestParam("")
```

