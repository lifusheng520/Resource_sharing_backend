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
     *
     * @return
     */
    User getUserByName(String username);

    /**
     * 获取用户权限
     *
     * @return
     */
    List<String> getRoles(int id);


    /**
     * 添加一个用户
     *
     * @param user 用户账号信息
     * @return 添加状态
     */
    int register(User user);


}
