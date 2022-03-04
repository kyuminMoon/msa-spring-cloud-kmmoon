package com.kmmoon.auth.config;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.sql.DataSource;
import java.util.Arrays;

@RequiredArgsConstructor
@Configuration
@EnableAuthorizationServer    // OAuth2 권한 서버
public class Oauth2AuthorizationConfig extends AuthorizationServerConfigurerAdapter {
    private final DataSource dataSource;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    @Value("${spring.oauth2.jwt.signkey}") // depth가 존재하는 값은 .으로 구분해서 값을 매핑
    private String signKey;
/*
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.inMemory()
//                .withClient("clientId") // 클라이언트 아이디
//                .secret("{noop}clientSecret") // 클라이언트 시크릿
//                .redirectUris("http://localhost:8081/oauth2/callback")
//                .authorizedGrantTypes("authorization_code", "password")
//                .scopes("read", "write")    // 해당 클라이언트의 접근 범위
//                .accessTokenValiditySeconds(60 * 60 * 4)            // access token 유효 기간 (초 단위)
//                .refreshTokenValiditySeconds(60 * 60 * 24 * 120)    // refresh token 유효 기간 (초 단위)
//                .autoApprove(true);   // OAuth Approval 화면 나오지 않게 처리
        clients.jdbc(dataSource).passwordEncoder(passwordEncoder);
    }
*/

    /* token store로 JWTTokenStore를 사용하겠다 */
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /* JWT 디코딩 하기 위한 설정 */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        System.out.println(signKey);
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey(signKey);
        return jwtAccessTokenConverter;
    }

    /**
     * AuthorizationServerConfigurerAdapter 안에서 사용될 여러 컴포넌트 정의
     * 여기선 스프링에 토큰 스토어, 액세스 토큰 컨버터, 토큰 엔헨서, 기본 인증 관리자와 사용자 상세 서비스를 이용한다고 선언
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // 스프링 OAuth 의 TokenEnhancerChain 를 등록하면 여러 TokenEnhancer 후킹 가능
//        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
//        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(jwtTokenEnhancer, jwtAccessTokenConverter));
//
//        endpoints.tokenStore(tokenStore)                             // JWT, JWTTokenStoreConfig 에서 정의한 토큰 저장소
//                .accessTokenConverter(jwtAccessTokenConverter)       // JWT, 스프링 시큐리티 OAuth2 가 JWT 사용하도록 연결
//                .tokenEnhancer(tokenEnhancerChain)                   // JWT, endpoints 에 tokenEnhancerChain 연결
//                .authenticationManager(authenticationManager)
//                .userDetailsService(userDetailsService)
//                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
        endpoints.tokenStore(tokenStore())
                .authenticationManager(authenticationManager)
                .accessTokenConverter(jwtAccessTokenConverter())
                .userDetailsService(userDetailsService);
    }


    /* 클라이언트 대한 정보를 설정하는 부분 */
    /* jdbc(DataBase)를 이용하는 방식 */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource).passwordEncoder(passwordEncoder);
    }
}
