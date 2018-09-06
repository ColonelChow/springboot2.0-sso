package com.springboot.sso.client;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

public class ClientSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 关闭csrf保护功能（跨域访问）
                .csrf().disable()
                .logout().
                logoutSuccessUrl("http://127.0.0.1:8082/sso-server");
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
