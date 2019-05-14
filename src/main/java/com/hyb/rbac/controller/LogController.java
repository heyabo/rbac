package com.hyb.rbac.controller;

import com.hyb.rbac.repo.Result;
import com.hyb.rbac.repo.User;
import com.hyb.rbac.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/log")
public class LogController {
    @Autowired
    private AuthenticationService authenticationService;
    @PostMapping()
    public Result login4Jwt(User user){
        return authenticationService.log4jwt(user);
    }
}
