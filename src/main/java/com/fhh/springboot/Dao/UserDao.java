package com.fhh.springboot.Dao;

import com.fhh.springboot.Entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 功能描述：（）
 * Created by biubiubiu小浩 on 2018-09-16 15:24
 */
@Mapper
public interface UserDao {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}
