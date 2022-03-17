package com.sharing.mapper;

import com.sharing.pojo.User;
import com.sharing.pojo.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * 用户dao
 *
 * @author 李福生
 * @date 2022-2-25
 * @time 下午 12:45
 */

@Mapper
@Repository
public interface UserMapper {

    /**
     * 根据用户名获取用户的id
     *
     * @param username 用户名
     * @return 用户id
     */
    int getUserIdByUsername(String username);

    /**
     * 通过用户名查询一个用户
     *
     * @param username 用户账号名
     * @return 如果有对应的用户账号信息，则返回用户信息，否则返回null
     */
    User getUserByName(String username);

    /**
     * 通过id查询用户对应的权限集
     *
     * @param id 用户id
     * @return 用户权限集合
     */
    List<String> getRoles(int id);

    /**
     * 添加一个用户账号
     *
     * @param user 用户账号信息
     * @return 添加状态
     */
    int addUser(User user);

    /**
     * 用过用户名查询数据库中的用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    UserInfo getUserInfo(String username);

    /**
     * 更新用户信息
     *
     * @param info 用户信息
     * @return 更新结果
     */
    int updateUserInfo(UserInfo info);

    /**
     * 根据用户id更新用户头像信息
     *
     * @param info 用户信息
     * @return 更新状态
     */
    int updateUserIconInfo(UserInfo info);

    /**
     * 通过用户id，查询用户头像信息
     *
     * @param id 用户id
     * @return 用户头像文件名
     */
    String getUserIcon(int id);

    /**
     * 更新邮箱验证码，根据id
     *
     * @param info 验证码信息
     * @return 更新状态
     */
    int updateEmailVerifyCode(UserInfo info);

    /**
     * 通过用户id从数据库中查询验证码和发送时间
     *
     * @param id 用户id
     * @return 用户id对应的验证码和发送时间
     */
    UserInfo getVerifyCodeAndTime(int id);

    /**
     * 通过用户id从数据库中的更新用户的邮箱号
     *
     * @param info 用户info
     * @return 更新影响数
     */
    int updateUserEmail(UserInfo info);

    /**
     * 通过用户名从数据库中查询用户邮箱
     *
     * @param username 用户名
     * @return 用户邮箱号
     */
    String getUserEmailByUsername(String username);

    /**
     * 通过用户名修改密码
     *
     * @param username 用户名
     * @param password 需要修改的密码
     * @return 修改结果
     */
    int updateUserPassword(String username, String password);

    int insertTime(Date date);

}
