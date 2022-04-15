package com.sharing.mapper;

import com.sharing.pojo.DeletedResource;
import com.sharing.pojo.UserResource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户资源jdbc操作类
 *
 * @author 李福生
 * @date 2022-3-16
 * @time 下午 09:49
 */

@Mapper
@Repository
public interface UserResourceMapper {
    /**
     * 通过md5获取用户资源文件信息
     *
     * @param discipline 文件科目
     * @param md5        md5字符串
     * @return 返回资源文件对象md5相同的文件的集合
     */
    List<UserResource> getResourceByMd5(String discipline, String md5);

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
    List<UserResource> getResourceListByUserId(int user_id);

    /**
     * 通过用户的id，获取分页查询资源列表
     *
     * @param user_id 用户id
     * @param begin   开始的位置
     * @param size    页面大小（个数）
     * @return 当前页面的内容list
     */
    List<UserResource> getUserResourceByPage(@Param("user_id") int user_id, @Param("begin") int begin, @Param("size") int size);

    /**
     * 通过用户id，获取用户所拥有的资源数量
     *
     * @param user_id 用户id
     * @return 用户资源数
     */
    int getResourceNumber(int user_id);

    /**
     * 通过关键字搜索查询用户的资源库
     *
     * @param user_id 用户id
     * @param key     搜索关键词
     * @return 返回一个与关键词相关的list结果集合
     */
    List<UserResource> getResourceBySearch(@Param("user_id") int user_id, @Param("key") String key, @Param("begin") int begin, @Param("size") int size);

    /**
     * 删除选中的多项资源，通过资源的id（假删除，用户没有权限删除）
     *
     * @param resourceList 被选中的资源列表
     * @return 删除执行状态结果
     */
    int setDeleteResourceByList(List<UserResource> resourceList);

    /**
     * 添加被删除的资源记录
     *
     * @param deletedResourceList 被删除的资源list集合
     * @return 返回更新结果
     */
    int insertDeletedResourceRecord(List<DeletedResource> deletedResourceList);

    /**
     * 更新用户资源信息，将资源信息修改
     *
     * @param info 资源信息
     * @return 返回资源更新状态
     */
    int updateResourceInfo(UserResource info);

    /**
     * 通过资源的id，更新资源的下载量
     *
     * @param id 资源id
     * @return 更新状态
     */
    int addResourceDownloads(int id);

    /**
     * 通过资源文件的id获取资源文件的原文件名
     *
     * @param id 资源对应的id
     * @return 返回资源的原文件名
     */
    String getResourceOriginFileName(int id);

    /**
     * 通过用户id，获取用户满足条件的资源数量
     *
     * @param user_id 用户id
     * @param like    查询条件
     * @return 返回满足条件的资源数量
     */
    int getResourceNumbersByCondition(int user_id, String like);

    /**
     * 通过用户id获取用户的资源删除记录
     *
     * @param user_id 用户id
     * @param begin 开始取值的位置
     * @param number 需要取多少个
     * @return 返回删除记录集合
     */
    List<DeletedResource> queryDeletedResourceRecordByUserId(int user_id, int begin, int number);

    /**
     * 统计用户id对应的删除记录数量
     *
     * @param user_id 用户id
     * @return 返回用户删除记录总数
     */
    int countDeletedResourceRecordNumbers(int user_id);

    /**
     * 将list集合中包含的资源信息删除
     *
     * @param deletedResourceList 需要删除的资源集合
     * @return 返回删除的结果
     */
    int realDeleteResourceByList(List<DeletedResource> deletedResourceList);

    /**
     * 将list中的回收箱中的记录删除
     *
     * @param deletedResourceList 需要删除的记录信息
     * @return 返回删除结果
     */
    int deleteResourceDeletedRecordByList(List<DeletedResource> deletedResourceList);

    /**
     * 将资源从被删除状态恢复
     *
     * @param reinstateResourceList 需要恢复的资源信息集合
     * @return 返回更新结果
     */
    int reinstateResourceByList(List<DeletedResource> reinstateResourceList);


    /**
     * 通过资源id获取资源信息list
     *
     * @param resourceIdList 资源id集合
     * @return 返回资源信息集合
     */
    List<UserResource> queryUserResourceByIds(List<Integer> resourceIdList);

    /**
     * 通过资源的id获取资源信息
     *
     * @param id 资源id
     * @return 返回id对应的资源
     */
    UserResource queryUserResourceById(int id);

}
