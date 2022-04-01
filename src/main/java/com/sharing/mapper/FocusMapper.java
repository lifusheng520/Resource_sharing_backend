package com.sharing.mapper;

import com.sharing.pojo.Focus;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 关注业务数据库操作接口
 *
 * @author 李福生
 * @date 2022-3-30
 * @time 上午 11:46
 */

@Mapper
@Repository
public interface FocusMapper {

    /**
     * 添加数据库关注记录
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
    List<Focus> getFocusListById(int user_id);

    /**
     * 根据用户的id和关注的id，删除用户关注记录
     *
     * @param user_id   用户的id
     * @param focus_uid 被关注的用户id
     * @return 返回删除结果
     */
    int deleteFocus(int user_id, int focus_uid);

    /**
     * 通过用户id获取用户关注内容分页数据
     *
     * @param user_id 用户id
     * @param begin   开始取值的位置
     * @param number  需要取的个数
     * @return 返回用户关注内容list
     */
    List<Focus> getFocusPageListByUserId(int user_id, int begin, int number);

    /**
     * 通过用户id统计用户id的关注记录数量
     *
     * @param user_id 用户id
     * @return 返回用户关注数量
     */
    int countFocusByUserId(int user_id);

}
