package com.shark.example.controller;

import com.shark.base.entity.ResponseDataEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RequestMapping(value = "/userId", method = RequestMethod.GET)
    public ResponseDataEntity<String> userId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();
        ResponseDataEntity<String> responseDataEntity = new ResponseDataEntity<>();
        responseDataEntity.setData(id);
        responseDataEntity.setReturnCode(1);
        return  responseDataEntity;
    }

    @RequestMapping(value = "/role1", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('role1')")
    public ResponseDataEntity<String> role1() {
        ResponseDataEntity<String> responseDataEntity = new ResponseDataEntity<>();
        responseDataEntity.setData("Role1");
        responseDataEntity.setReturnCode(1);
        return  responseDataEntity;
    }

    @RequestMapping(value = "/role2", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('role2')")
    public ResponseDataEntity<String> role2() {
        ResponseDataEntity<String> responseDataEntity = new ResponseDataEntity<>();
        responseDataEntity.setData("Role2");
        responseDataEntity.setReturnCode(1);
        return  responseDataEntity;
    }
}
