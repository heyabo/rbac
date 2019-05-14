package com.hyb.rbac.controller;

import com.hyb.rbac.repo.User;
import com.hyb.rbac.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping()
    public User getUser(@RequestParam String name){
        return userService.getUserByName(name);
    }
    @PostMapping()
    public void addUser(User user){
        userService.addUser(user);
    }
    @PutMapping()
    public void updateUser(User user){
        userService.updateUser(user);
    }
    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable("id") int id){
        userService.deleteUser(id);
    }
}
