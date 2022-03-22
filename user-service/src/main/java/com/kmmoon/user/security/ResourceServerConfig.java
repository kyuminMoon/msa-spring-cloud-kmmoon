package com.kmmoon.user.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

// 참고
// https://github.com/sbcoba/spring-boot-oauth2-sample/blob/master/src/main/java/com/example/DemoApplication.java
// https://gaemi606.tistory.com/entry/Spring-Boot-Spring-Security-OAuth2-5-%EC%84%9C%EB%B2%84-%EB%82%98%EB%88%84%EA%B8%B0
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{

//	@Value("${security.oauth2.jwt.signkey}") // depth가 존재하는 값은 .으로 구분해서 값을 매핑
//	private String signKey;
//
//	/* token store로 JWTTokenStore를 사용하겠다 */
//	@Bean
//	public TokenStore tokenStore() {
//		return new JwtTokenStore(jwtAccessTokenConverter());
//	}
//
//	/* JWT 디코딩 하기 위한 설정 */
//	@Bean
//	public JwtAccessTokenConverter jwtAccessTokenConverter() {
//		JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
//		jwtAccessTokenConverter.setSigningKey(signKey);
//		return jwtAccessTokenConverter;
//	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.headers().frameOptions().disable();
		http.authorizeRequests()
//				.antMatchers("/user/**").access("#oauth2.hasScope('read')")
				// 인증을 security.oauth2.resource.token-info-uri 에서 처리한다
				.antMatchers("/user/test").access("#oauth2.hasScope('read')")
				.anyRequest().authenticated();
	}



}
