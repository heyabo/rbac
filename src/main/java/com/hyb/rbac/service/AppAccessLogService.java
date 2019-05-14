package com.hyb.rbac.service;

import com.hyb.rbac.repo.AppAccessLog;

public interface AppAccessLogService {
    void addAppAccessLog(AppAccessLog accessLog);
}
