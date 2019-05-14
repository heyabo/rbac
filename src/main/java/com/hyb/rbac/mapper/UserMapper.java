package com.hyb.rbac.mapper;

import com.hyb.rbac.handler.DateTypeHandler;
import com.hyb.rbac.repo.User;
import org.apache.ibatis.annotations.*;

public interface UserMapper {
    @Results(id = "userMap",value = {
            @Result(column = "is_admin",property = "isAdmin"),
            @Result(column = "created_time",property = "createdTime",typeHandler = DateTypeHandler.class),
            @Result(column = "updated_time",property = "updatedTime",typeHandler = DateTypeHandler.class)
    })
    @Select("select * from user where name=#{name}")
    User getOneByName(String name);

    @Insert("INSERT INTO user (name,email,password) VALUES (#{name},#{email},#{password} )")
    void addOne(User user);
    @Update("update user set name=#{name},email=#{email},password=#{password},is_admin=#{isAdmin}," +
            "updated_time=#{updatedTime,typeHandler=com.hyb.rbac.handler.DateTypeHandler},status=#{status} " +
            "where id=#{id}")
    int update(User user);
    @Delete("delete from user where id=#{id}")
    int deleteById(int id);
}
