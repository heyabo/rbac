package com.hyb.rbac.repo;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

public class User {

  @JsonIgnore
  private long id;
  private String name;
  @JsonIgnore
  private String password;
  private String email;
  @JsonIgnore
  private long isAdmin;
  @JsonIgnore
  private long status;
  @JsonInclude(value = JsonInclude.Include.NON_NULL)
  @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss",locale = "zh",timezone = "GMT+8")
  private Date updatedTime;
  @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss",locale = "zh",timezone = "GMT+8")
  private Date createdTime;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }


  public long getIsAdmin() {
    return isAdmin;
  }

  public void setIsAdmin(long isAdmin) {
    this.isAdmin = isAdmin;
  }


  public long getStatus() {
    return status;
  }

  public void setStatus(long status) {
    this.status = status;
  }


  public Date getUpdatedTime() {
    return updatedTime;
  }

  public void setUpdatedTime(Date updatedTime) {
    this.updatedTime = updatedTime;
  }


  public Date getCreatedTime() {
    return createdTime;
  }

  public void setCreatedTime(Date createdTime) {
    this.createdTime = createdTime;
  }

}
