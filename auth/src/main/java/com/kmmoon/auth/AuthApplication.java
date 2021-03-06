package com.kmmoon.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@RequiredArgsConstructor
@SpringBootApplication
@EnableEurekaClient
@EnableResourceServer // API 서버 인증
@EnableAuthorizationServer      // 이 서비스가 OAuth2 인증 서버가 될 것이라고 스프링 클라우드에 알림
@EntityScan("com.kmmoon.*")
@EnableJpaRepositories("com.kmmoon.*")
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

}
