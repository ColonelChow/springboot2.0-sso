package com.springboot.sso.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



/**
 * 自定义登录失败处理类--没起作用，艹
 */
@Component
public class MyAuthenticationFailHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        System.out.println("登录失败!!!");
        //设置状态码
        response.setStatus(500);
        response.setContentType("application/json;charset=UTF-8");
        //将登录失败信息以json格式返回
        //response.getWriter().write(JSON.toJSONString(ServerResponse.createByErrorMessage(exception.getMessage())));
    }
}
