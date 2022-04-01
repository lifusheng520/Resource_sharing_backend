package com.sharing.service;

import com.sharing.pojo.UserAndResource;

import java.util.List;

/**
 * 资源详情业务服务
 *
 * @author 李福生
 * @date 2022-3-26
 * @time 上午 09:56
 */
public interface ResourceDetailService {

    /**
     * 获取用户资源详细信息
     *
     * @param id 资源id
     * @return 返回用户和资源信息
     */
    UserAndResource getUserResourceDetail(int id);

    /**
     * 通过用户id获取用户详细数据list
     *
     * @param user_id 用户id
     * @param begin   开始取值的位置
     * @param number  需要取值的个数
     * @return 返回用户和资源信息list
     */
    List<UserAndResource> getFocusUserResourceByUserId(int user_id, int begin, int number);

    /**
     * 通过用户id，统计用户所拥有的资源数量
     *
     * @param user_id 用户id
     * @return 返回用户拥有的资源数量
     */
    int countUserResourceNumbers(int user_id);

}
