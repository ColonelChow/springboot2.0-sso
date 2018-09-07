package com.springboot.sso.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class SsoUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = new User(username, passwordEncoder.encode("123456"), AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
        return user ;



        /***此处查询数据库或者用户认证接口即可。*/
        /*
        String passwd = "";
        System.out.println("收到的账号"+username);
        if (CheckFormat.isEmail(username)){
            passwd = userService.selectPasswdByEmail(username);
        }else if (CheckFormat.isPhone(username)){
            passwd = userService.selectPasswdByPhone(username);
        }else {
            throw new RuntimeException("登录账号不存在");
        }
        System.out.println("查到的密码"+passwd);
        return new User(username, passwd, AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
        */
    }
}
