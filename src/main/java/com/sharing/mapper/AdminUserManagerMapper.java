package com.sharing.mapper;

import com.sharing.pojo.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 李福生
 * @date 2022-4-12
 * @time 下午 03:07
 */

@Mapper
@Repository
public interface AdminUserManagerMapper {

    /**
     * 获取用户的账号信息list
     *
     * @param begin  开始的取值位置
     * @param number 需要取值的个数
     * @return 返回用户账号信息分页list
     */
    List<UserInfo> queryUserInfoByPage(int begin, int number);

    /**
     * 获取用户账号信息的分页总数
     *
     * @return 返回账号记录的总数量
     */
    int countUserInfoPageTotal();

    /**
     * 将集合中id对应的账号冻结
     *
     * @param idList  账号id
     * @param enabled 账号状态码
     * @return 返回更新结果
     */
    int updateUserAccountEnabled(List<Integer> idList, int enabled);


}
