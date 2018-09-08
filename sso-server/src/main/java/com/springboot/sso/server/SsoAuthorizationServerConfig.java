package com.springboot.sso.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;


@Configuration
@EnableAuthorizationServer
public class SsoAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SsoUserDetailsService ssoUserDetailsService;

    //@Autowired
    //private RedisConnectionFactory redisConnection;

    /**
     * 客户端配置-令牌发给谁
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                //客户端ID
                .withClient("client1")
                .secret(new BCryptPasswordEncoder().encode("client1secrect"))
                //token过期时间
                .accessTokenValiditySeconds(30)
                //refresh过期时间
                .refreshTokenValiditySeconds(30)
                //授权模式和刷新令牌
                //.authorizedGrantTypes("password", "refresh_token")
                .authorizedGrantTypes("authorization_code", "refresh_token")
                //.redirectUris("http://127.0.0.1:8083/client1")
                //授权用户的操作权限
                .scopes("all","read","write")
                .autoApprove(true)
                .and()
                .withClient("merryyou2")
                .secret(new BCryptPasswordEncoder().encode("merryyousecrect2"))
                .authorizedGrantTypes("authorization_code", "refresh_token")
                //.redirectUris("http://sso-tmall:8084/client2")
                //授权用户的操作权限
                .scopes("all","read","write")
                .autoApprove(true);
    }

    /**
     * 配置jwttokenStore
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(jwtTokenStore()).accessTokenConverter(jwtAccessTokenConverter())
                //配置userDetailsService每次认证的时候会去检验用户是否锁定，有效等
                //.authenticationManager(authenticationManager)
                //.userDetailsService(ssoUserDetailsService)
        ;
    }

    /**
     * springSecurity 授权表达式，访问tokenkey时需要经过认证
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("isAuthenticated()");
    }

    /**
     * JWTtokenStore
     * @return
     */
    @Bean
    public TokenStore jwtTokenStore() {
        //return new JdbcTokenStore();
        //return new RedisTokenStore(redisConnection);
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * 生成JTW token
     * @return
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey("123456");
        return jwtAccessTokenConverter;
    }


    @Bean
    public ResourceServerTokenServices defaultTokenServices() {
        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenEnhancer(jwtAccessTokenConverter());
        defaultTokenServices.setTokenStore(jwtTokenStore());
        return defaultTokenServices;
    }
}
