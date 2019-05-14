package com.hyb.rbac.service;

import com.hyb.rbac.mapper.AppAccessLogMapper;
import com.hyb.rbac.repo.AppAccessLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppAccessLogServiceImpl  implements AppAccessLogService{
    @Autowired
    private AppAccessLogMapper accessLogMapper;
    @Override
    public void addAppAccessLog(AppAccessLog accessLog) {
        accessLogMapper.addOne(accessLog);
    }
}
