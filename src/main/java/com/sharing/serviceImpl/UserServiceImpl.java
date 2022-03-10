package com.sharing.serviceImpl;

import com.sharing.mapper.UserMapper;
import com.sharing.pojo.User;
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

}
