package com.sharing.service;

import com.sharing.pojo.User;
import com.sharing.pojo.UserInfo;

import java.util.List;

/**
 * @author 李福生
 * @date 2022-2-25
 * @time 下午 12:45
 */
public interface UserService {

    /**
     * 通过用户名获取用户的id
     *
     * @param username 用户名
     * @return 用户对应的id
     */
    int getUserIdByUsername(String username);

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

    /**
     * 通过用户名获取用户信息
     *
     * @return 用户信息
     */
    UserInfo getUserInfo(String username);

    /**
     * 更新用户信息
     *
     * @param info 用户信息
     * @return 用户结果
     */
    int updateUserInfo(UserInfo info);

    /**
     * 通过用户信息 更新头像文件名
     *
     * @param info 用户信息
     * @return 用户头像文件名称
     */
    int updateUserIcon(UserInfo info);

    /**
     * 通过id获取用户头像信息
     *
     * @param id 用户id
     * @return 用户头像的文件名
     */
    String getUserIconInfo(int id);

    /**
     * 更新用户邮箱验证码，根据id
     *
     * @param info 验证码信息
     * @return 更新状态
     */
    int updateEmailVerifyCode(UserInfo info);

    /**
     * 根据用户的id，查询用户接收到的验证码和发送时间
     *
     * @param id 用户id
     * @return 返回用户验证码和发送时间
     */
    UserInfo getVerifyCodeAndTime(int id);

    /**
     * 通过用户id更新用户的邮箱号
     *
     * @param info 用户info
     * @return 更新的结果
     */
    int updateUserEmail(UserInfo info);

    /**
     * 用户名查询用户邮箱
     *
     * @param username 用户名
     * @return 用户邮箱号
     */
    String getUserEmail(String username);


}
