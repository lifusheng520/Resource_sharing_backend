package com.sharing.service;

/**
 * 点赞支持相关的业务接口
 *
 * @author 李福生
 * @date 2022-4-3
 * @time 上午 11:42
 */
public interface SupportService {

    /**
     * 统计资源的点赞数
     *
     * @param resource_id 资源id
     * @return 返回资源的点赞数量
     */
    int countResourceSupportNumbers(int resource_id);

    /**
     * 判断用户对资源是否已经添加过点赞记录
     *
     * @param user_id     用户id
     * @param resource_id 资源id
     * @return 如果记录存在返回true， 否则返回false
     */
    boolean supportIsExist(int user_id, int resource_id);


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
