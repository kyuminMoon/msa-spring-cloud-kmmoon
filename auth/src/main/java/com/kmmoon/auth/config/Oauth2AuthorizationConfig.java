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
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.sql.DataSource;
import java.util.Arrays;

@RequiredArgsConstructor
@Configuration
@EnableAuthorizationServer    // OAuth2 κΆν μλ²
public class Oauth2AuthorizationConfig extends AuthorizationServerConfigurerAdapter {
    private final DataSource dataSource;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

//    @Value("${security.oauth2.jwt.signkey}") // depthκ° μ‘΄μ¬νλ κ°μ .μΌλ‘ κ΅¬λΆν΄μ κ°μ λ§€ν
//    private String signKey;

//    // JWT λ°©μ
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        super.configure(endpoints);
//        endpoints.accessTokenConverter(jwtAccessTokenConverter()).userDetailsService(userDetailService);
//    }
//
//    @Bean
//    public JwtAccessTokenConverter jwtAccessTokenConverter() {
//        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//        converter.setSigningKey(signKey);
//        return converter;
//    }


    // JDBC λ°©μ
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(new JdbcTokenStore(dataSource)).authenticationManager(authenticationManager);
    }


    /* token storeλ‘ JWTTokenStoreλ₯Ό μ¬μ©νκ² λ€ */
//    @Bean
//    public TokenStore tokenStore() {
//        return new JwtTokenStore(jwtAccessTokenConverter());
//    }

    /* JWT λμ½λ© νκΈ° μν μ€μ  */
//    @Bean
//    public JwtAccessTokenConverter jwtAccessTokenConverter() {
//        System.out.println(signKey);
//        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
//        jwtAccessTokenConverter.setSigningKey(signKey);
//        return jwtAccessTokenConverter;
//    }

    /**
     * AuthorizationServerConfigurerAdapter μμμ μ¬μ©λ  μ¬λ¬ μ»΄ν¬λνΈ μ μ
     * μ¬κΈ°μ  μ€νλ§μ ν ν° μ€ν μ΄, μ‘μΈμ€ ν ν° μ»¨λ²ν°, ν ν° μν¨μ, κΈ°λ³Έ μΈμ¦ κ΄λ¦¬μμ μ¬μ©μ μμΈ μλΉμ€λ₯Ό μ΄μ©νλ€κ³  μ μΈ
     */
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        endpoints.tokenStore(new JdbcTokenStore(dataSource))
//                .authenticationManager(authenticationManager)
//                .accessTokenConverter(jwtAccessTokenConverter())
//                .userDetailsService(userDetailsService);
//    }


    /* ν΄λΌμ΄μΈνΈ λν μ λ³΄λ₯Ό μ€μ νλ λΆλΆ */
    /* jdbc(DataBase)λ₯Ό μ΄μ©νλ λ°©μ */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource).passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()").allowFormAuthenticationForClients();
    }
}
