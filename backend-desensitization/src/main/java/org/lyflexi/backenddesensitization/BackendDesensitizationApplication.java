package org.lyflexi.backenddesensitization;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.lyflexi.backenddesensitization.mapper")
public class BackendDesensitizationApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendDesensitizationApplication.class, args);
    }

}
