package com.sharing.service;

import com.sharing.pojo.UserResource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 李福生
 * @date 2022-3-16
 * @time 下午 09:38
 */
public interface UserResourceService {

    /**
     * 通过md5获取用户资源文件信息
     *
     * @param md5 md5字符串
     * @return 返回资源文件对象md5相同的文件的集合
     */
    List<UserResource> getUserResourceByMd5(String md5);

    /**
     * 向数据库中添加一条用户上传资源的记录
     *
     * @param userResource 用户上传的资源信息
     * @return 添加的状态
     */
    int addUserResource(UserResource userResource);

    /**
     * 通过用户id查询用户资源集合
     *
     * @param user_id 用户id
     * @return 用户资源集合对象
     */
    List<UserResource> getUserResourceByUserId(int user_id);


    /**
     * 通过用户的id，获取分页查询资源列表
     *
     * @param user_id 用户id
     * @param begin   开始的位置
     * @param size    页面大小（个数）
     * @return 当前页面的内容list
     */
    List<UserResource> getUserResourceByPage(int user_id, int begin, int size);

    /**
     * 通过用户id，获取用户所拥有的资源数量
     *
     * @param user_id 用户id
     * @return 用户资源数
     */
    int getUserResourceNumber(int user_id);

    /**
     * 通过用户id，获取用户满足条件的资源数量
     *
     * @param user_id 用户id
     * @param like    查询条件
     * @return 返回满足条件的资源数量
     */
    int getUserResourceNumbersByCondition(int user_id, String like);

    /**
     * 通过关键字搜索查询用户的资源库
     *
     * @param user_id 用户id
     * @param key     搜索关键词
     * @return 返回一个与关键词相关的list结果集合
     */
    List<UserResource> getUserResourceBySearch(int user_id, String key, int begin, int size);

    /**
     * 删除选中的多项资源，通过资源的id（假删除，用户没有权限删除）
     *
     * @param resourceList 被选中的资源列表
     * @return 删除执行状态结果
     */
    int deleteUserResourceByList(List<UserResource> resourceList);

    /**
     * 更新用户资源信息，将资源信息修改
     *
     * @param userResource
     * @return
     */
    int updateUserResourceInfo(UserResource userResource);

    /**
     * 通过资源的id，更新资源的下载量
     *
     * @param id 资源id
     * @return 更新状态
     */
    int addUserResourceDownloads(int id);

    /**
     * 通过资源文件的id获取资源文件的原文件名
     *
     * @param id 资源对应的id
     * @return 返回资源的原文件名
     */
    String getUserResourceOriginFileName(int id);

}
