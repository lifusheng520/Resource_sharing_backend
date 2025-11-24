package com.sharing.mapper;

import com.sharing.pojo.UserAndResource;
import com.sharing.pojo.UserInfo;
import com.sharing.pojo.UserResource;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 资源详细信息的mapper
 *
 * @author 李福生
 * @date 2022-3-26
 * @time 下午 02:24
 */

@Mapper
@Repository
public interface ResourceDetailMapper {

    /**
     * 通过资源的id查询资源信息
     *
     * @param id 资源id
     * @return 资源信息
     */
    UserResource getResourceById(int id);

    /**
     * 通过id查询用户信息
     *
     * @param id 用户id
     * @return 用户信息UserInfo
     */
    UserInfo getUserInfoById(int id);

    /**
     * 通过用户id获取用户详细数据list
     *
     * @param user_id 用户id
     * @param begin   开始取值的位置
     * @param number  需要取值的个数
     * @return 返回用户和资源信息list
     */
    List<UserAndResource> getFocusResourceByUserId(int user_id, int begin, int number);

    /**
     * 通过用户id，统计用户所拥有的资源数量
     *
     * @param user_id 用户id
     * @return 返回用户拥有的资源数量
     */
    int countResourceNumberByUserId(int user_id);

    /**
     * 通过资源id，获取用户资源详细信息
     *
     * @param resource_id 资源id
     * @return 返回资源详细信息类对象
     */
    UserAndResource getUserAndResourceByResourceId(int resource_id);


}
