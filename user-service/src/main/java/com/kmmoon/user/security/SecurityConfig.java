package com.kmmoon.user.security;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 허용되어야 할 경로들 swagger, not login ... etc
        web.ignoring().antMatchers("/v2/api-docs")//
                .antMatchers("/swagger-resources/**")//
                .antMatchers("/swagger-ui.html")//
                .antMatchers("/swagger-ui/index.html")//
                .antMatchers("/swagger-ui/**")//
                .antMatchers("/v3/api-docs/swagger-config")//
                .antMatchers("/v3/api-docs")//
                .antMatchers("/configuration/**")//
                .antMatchers("/webjars/**")//
                .antMatchers("/public")
                .and()
                .ignoring()
                .antMatchers("/h2-console/**/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // CSRF 비활성화(교차 사이트 요청 위조)
        http.csrf().disable();

        // 세션쿠키 방식의 인증 메카니즘으로 인증처리를 하지 않음
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                // 허용 페이지
                .antMatchers("/h2-console/**/**").permitAll()
                .antMatchers("/actuator/**").permitAll()
                .antMatchers("/actuator").permitAll()
                // 다른건 다 불허
                .anyRequest().authenticated();

        // 사용자가 권한 없이 리소스에 액세스하려고 하는 경우
//        http.exceptionHandling().accessDeniedPage("/users/signup");

    }
}
