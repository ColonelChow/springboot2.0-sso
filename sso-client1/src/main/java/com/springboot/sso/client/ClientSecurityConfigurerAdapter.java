package com.springboot.sso.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

public class ClientSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    @Value("${auth-server}/exit")
    private String logoutUrl;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 关闭csrf保护功能（跨域访问）
                .csrf().disable()
                .logout()
                .deleteCookies("remove")
                .logoutSuccessUrl(logoutUrl)
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated();
       /* http
                .antMatcher("/**")
                .logout()
                .deleteCookies("remove")
                .invalidateHttpSession(false)
                .logoutUrl("http://127.0.0.1:8082/sso-server/oauth/exit")
                .logoutSuccessUrl("http://127.0.0.1:8082/uaa")
                .and()
                .rememberMe();*/

    }
}
