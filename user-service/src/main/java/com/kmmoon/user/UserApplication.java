package com.kmmoon.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication(scanBasePackages = "com.kmmoon.*")
@EnableFeignClients
@EnableDiscoveryClient
@RefreshScope
public class UserApplication implements CommandLineRunner {

    public static void main(String[] args) {
        new SpringApplicationBuilder(UserApplication.class)
                .properties("spring.config.location=" +
                        "classpath:application.yml," +
                        "classpath:zipkin/application.yml"
                ).run();
    }

    @Override
    public void run(String... args) throws Exception {}
}
