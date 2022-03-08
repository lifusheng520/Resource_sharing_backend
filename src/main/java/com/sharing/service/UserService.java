package com.sharing.service;

import com.sharing.pojo.User;

import java.util.List;

/**
 * @author 李福生
 * @date 2022-2-25
 * @time 下午 12:45
 */
public interface UserService {

    /**
     * 查询用户
     * @return
     */
    User getUserByName(String username);

    /**
     *  获取用户权限
     * @return
     */
    List<String> getRoles(int id);


    /**
     * 用户注册
     * @param user
     * @return
     */
    boolean register(User user);



}
