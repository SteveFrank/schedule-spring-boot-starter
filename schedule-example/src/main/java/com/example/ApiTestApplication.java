package com.example;

import com.starter.schedule.annotation.EnableDcsScheduling;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author frankq
 * @date 2021/9/9
 */
@SpringBootApplication
@EnableDcsScheduling
public class ApiTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiTestApplication.class, args);
    }

}
