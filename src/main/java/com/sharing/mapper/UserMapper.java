package com.sharing.mapper;

import com.sharing.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

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

}
