package com.springboot.sso.client.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @RequestMapping(value="/login", method = RequestMethod.GET)
    public void  login(){
        System.out.println("=======login========>");
    }
}
