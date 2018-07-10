package com.yff.xim;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.yff.xim.module.user.mapper"})
public class XImApplication {

    public static void main(String[] args) {
        SpringApplication.run(XImApplication.class, args);
    }
}
