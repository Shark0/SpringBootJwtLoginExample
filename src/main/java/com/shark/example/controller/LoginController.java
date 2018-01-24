package com.shark.example.controller;

import com.shark.base.entity.ResponseDataEntity;
import com.shark.base.entity.ResponseEntity;
import com.shark.base.util.ServletUtil;
import com.shark.example.entity.MemberEntity;
import com.shark.example.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
public class LoginController {

    @Autowired
    private MemberService memberService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResponseDataEntity<MemberEntity> login(HttpServletRequest request) {
        return memberService.request(ServletUtil.generateServiceParameters(request, MemberService.INPUT_STRING_ACCOUNT));
    }
}
