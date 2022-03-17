package com.sharing;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 李福生
 * @date 2022-2-25
 * @time 下午 12:45
 */

@SpringBootApplication
@MapperScan("com.sharing.mapper")
public class CampusresourcemanagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CampusresourcemanagerApplication.class, args);
    }

}
