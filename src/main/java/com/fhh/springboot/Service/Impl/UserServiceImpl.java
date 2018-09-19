package com.fhh.springboot.Service.Impl;

import com.fhh.springboot.Dao.UserDao;
import com.fhh.springboot.Entity.User;
import com.fhh.springboot.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * 功能描述：（）
 * Created by biubiubiu小浩 on 2018-09-16 15:49
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    private static final RedisSerializer redisSerializer = new StringRedisSerializer();

    @Override
    public int addUser(User user) {
        return userDao.insert(user);
    }

    @Override
    public User findUserById(Integer id) {
        redisTemplate.setKeySerializer(redisSerializer);
        User user = (User) redisTemplate.opsForValue().get("user" + id);
        //高并发情况下，存在缓存穿透，此处用双重检测锁
        if (null == user) {
            synchronized (this){
                user = (User) redisTemplate.opsForValue().get("user" + id);
                if (null == user) {
                    System.out.println("查询数据库");
                    user = userDao.selectByPrimaryKey(id);
                    if (!ObjectUtils.isEmpty(user)) {
                        redisTemplate.opsForValue().set("user" + id, user);
                    }
                }else {
                    System.out.println("查询缓存");
                }
                if (ObjectUtils.isEmpty(user)) {
                    return new User("未查找到该用户！", 0, 0);
                }
            }
        }else {
            System.out.println("查询缓存");
        }
        return user;
    }
}
