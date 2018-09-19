package com.fhh.springboot.Service;

import com.fhh.springboot.Entity.User;

/**
 * 功能描述：（）
 * Created by biubiubiu小浩 on 2018-09-16 15:41
 */
public interface UserService {
    /**
     * @param user
     * @return
     */
    int addUser(User user);

    User findUserById(Integer id);
}
