package com.hyb.rbac.mapper;

import com.hyb.rbac.repo.AppAccessLog;
import org.apache.ibatis.annotations.Insert;

public interface AppAccessLogMapper {
    @Insert("INSERT INTO app_access_log (uid,target_url,query_params,data,ip,header) VALUES " +
            "(#{uid},#{targetUrl},#{queryParams},#{data},#{ip},#{header} )")
    void addOne(AppAccessLog appAccessLog);
}
