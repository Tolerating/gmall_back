package com.atguigu.gmall.uesr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.atguigu.gmall.uesr.mapper")
public class GmallUesrServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GmallUesrServiceApplication.class, args);
    }

}
