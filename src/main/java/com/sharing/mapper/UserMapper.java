package com.sharing.mapper;

import com.sharing.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 李福生
 * @date 2022-2-25
 * @time 下午 12:45
 */

@Mapper
@Repository
public interface UserMapper {

    User getUserByName(String username);

    List<String> getRoles(int id);

    int addUser(User user);


    User login(String account);
}
