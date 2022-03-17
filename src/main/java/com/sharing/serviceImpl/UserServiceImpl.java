package com.sharing.serviceImpl;

import com.sharing.mapper.UserMapper;
import com.sharing.pojo.User;
import com.sharing.pojo.UserInfo;
import com.sharing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 李福生
 * @date 2022-2-25
 * @time 下午 12:45
 */

@Service
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public int getUserIdByUsername(String username) {
        return this.userMapper.getUserIdByUsername(username);
    }

    @Override
    public User getUserByName(String username) {
        return this.userMapper.getUserByName(username);
    }

    @Override
    public List<String> getRoles(int id) {
        return this.userMapper.getRoles(id);
    }

    @Override
    public int register(User user) {
        return this.userMapper.addUser(user);
    }

    @Override
    public UserInfo getUserInfo(String username) {

        // 根据id查询用户信息
        UserInfo userInfo = this.userMapper.getUserInfo(username);

        // 用户不存在直接返回
        if (userInfo == null)
            return null;

        // 查询用户权限信息
        List<String> roles = this.userMapper.getRoles(userInfo.getId());

        //设置用户的权限信息
        userInfo.setRoles(roles);

        return userInfo;
    }

    @Override
    public int updateUserInfo(UserInfo info) {
        return this.userMapper.updateUserInfo(info);
    }

    @Override
    public int updateUserIcon(UserInfo info) {
        return this.userMapper.updateUserIconInfo(info);
    }

    @Override
    public String getUserIconInfo(int id) {
        return this.userMapper.getUserIcon(id);
    }

    @Override
    public int updateEmailVerifyCode(UserInfo info) {
        return this.userMapper.updateEmailVerifyCode(info);
    }

    @Override
    public UserInfo getVerifyCodeAndTime(int id) {
        return this.userMapper.getVerifyCodeAndTime(id);
    }

    @Override
    public int updateUserEmail(UserInfo info) {
        return this.userMapper.updateUserEmail(info);
    }

    @Override
    public String getUserEmail(String username) {
        return this.userMapper.getUserEmailByUsername(username);
    }

    @Override
    public int updatePassword(String username, String password) {
        return this.userMapper.updateUserPassword(username, password);
    }


}
