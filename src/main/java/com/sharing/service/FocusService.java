package com.sharing.service;

import com.sharing.pojo.Focus;

import java.util.List;

/**
 * 用户关注业务接口
 *
 * @author 李福生
 * @date 2022-3-30
 * @time 上午 11:43
 */
public interface FocusService {

    /**
     * 添加关注
     *
     * @param focus 添加的关注信息
     * @return 返回更新结果
     */
    int addFocus(Focus focus);

    /**
     * 通过用户id获取用户的关注列表
     *
     * @param user_id 用户id
     * @return 返回用户关注内容list
     */
    List<Focus> getUserFocusListById(int user_id);

    /**
     * 根据用户的id和关注的id，删除用户关注记录
     *
     * @param user_id   用户的id
     * @param focus_uid 被关注的用户id
     * @return 返回删除结果
     */
    int cancelFocus(int user_id, int focus_uid);


    /**
     * 通过用户id获取用户关注内容分页数据
     *
     * @param user_id 用户id
     * @param begin   开始取值的位置
     * @param number  需要取的个数
     * @return 返回用户关注内容list
     */
    List<Focus> getUserFocusPageListByUserId(int user_id, int begin, int number);

    /**
     * 通过用户id统计用户id的关注记录数量
     *
     * @param user_id 用户id
     * @return 返回用户关注数量
     */
    int countFocusNumberByUserId(int user_id);

}
