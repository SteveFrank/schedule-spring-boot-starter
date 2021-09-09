package com.schedule.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author frankq
 * @date 2021/9/9
 */
@SpringBootApplication
public class ImcApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ImcApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(ImcApplication.class, args);
    }

}
