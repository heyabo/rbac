package com.hyb.rbac.service;

import com.hyb.rbac.common.util.JwtUtil;
import com.hyb.rbac.common.util.Md5Util;
import com.hyb.rbac.repo.Result;
import com.hyb.rbac.repo.User;
import com.nimbusds.jose.JOSEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {
    @Autowired
    private UserService userService;

    /**
     * 用户登录，获取token
     * @param user
     * @return
     */
    public Result log4jwt(User user){
        Result result=new Result();
        User user1 = userService.getUserByName(user.getName());
        if(user1.getPassword().equals(Md5Util.getMd5(user.getPassword()) )){
            try {
                Map<String, Object> map = new HashMap<>();
                map.put("uid",user.getId());
                result.setStatus(true);
                result.setData(JwtUtil.createToken(map));
            } catch (JOSEException e) {
                e.printStackTrace();
                result.setException(e);
            }
        }
        return result;
    }

}
