package com.sharing.mapper;

import com.sharing.pojo.UserInfo;
import com.sharing.pojo.UserResource;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

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


}
