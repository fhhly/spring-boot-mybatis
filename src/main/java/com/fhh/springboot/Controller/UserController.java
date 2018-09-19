package com.fhh.springboot.Controller;

import com.fhh.springboot.Entity.User;
import com.fhh.springboot.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 功能描述：（）
 * Created by biubiubiu小浩 on 2018-09-16 15:41
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/addUser")
    public String addUser(String userName,int age){
        User user = new User();
        user.setUserName(userName);
        user.setAge(age);
        int addUserCount = userService.addUser(user);
        if (addUserCount==1) {
            return "success add" + userName;
        }
        return  "false add";
    }

    @RequestMapping(value = "/findUser")
    public User findUser(int id){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                userService.findUserById(id);
            }
        };
        ExecutorService executorService = Executors.newFixedThreadPool(25);
        for (int i = 0;i<1000;i++){
            executorService.submit(runnable);
        }
      return userService.findUserById(id);
    }
}
