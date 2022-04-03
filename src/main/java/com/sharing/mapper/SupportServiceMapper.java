package com.sharing.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author 李福生
 * @date 2022-4-3
 * @time 上午 11:47
 */

@Mapper
@Repository
public interface SupportServiceMapper {

    /**
     * 统计资源的点赞数
     *
     * @param resource_id 资源id
     * @return 返回资源的点赞数量
     */
    int countResourceSupportNumbers(int resource_id);

    /**
     * 统计用户对资源的点赞记录
     *
     * @param user_id     用户id
     * @param resource_id 资源id
     * @return 返回记录的数量
     */
    int countSupportRecord(int user_id, int resource_id);

    /**
     * 删除用户对资源的点赞记录
     *
     * @param user_id     用户id
     * @param resource_id 资源id
     * @return 返回更新状态
     */
    int deleteResourceSupportRecord(int user_id, int resource_id);

    /**
     * 添加一条用户的点赞记录
     *
     * @param user_id     用户id
     * @param resource_id 资源id
     * @return 返回更新状态
     */
    int addResourceSupportRecord(int user_id, int resource_id);
}
