package com.sharing.mapper;

import com.sharing.pojo.CompleteResourceInfo;
import com.sharing.pojo.UserResource;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 李福生
 * @date 2022-4-13
 * @time 下午 01:48
 */

@Mapper
@Repository
public interface PlatformResourceMapper {

    /**
     * 通过分页获取系统的所有资源，如果有搜索内容，则按搜索获取
     *
     * @param begin  分页的起始位置
     * @param number 需要取值的个数
     * @param like   搜索的内容
     * @return 返回当前页资源
     */
    List<CompleteResourceInfo> queryAllResourceByPage(int begin, int number, String like);


    /**
     * 统计平台通过审批的所有资源
     *
     * @param search 搜索的内容
     * @return 返回平台资源数量
     */
    int countAllResourceBySearch(String search);

    /**
     * 通过资源id，删除资源的评论
     *
     * @param resourceIds 资源id集合
     * @return 返回更新结果
     */
    int deleteCommentByResourceId(List<Integer> resourceIds);

    /**
     * 通过资源id，删除收藏
     *
     * @param resourceIds 资源id集合
     * @return 返回更新结果
     */
    int deleteFavouriteByResourceId(List<Integer> resourceIds);

    /**
     * 通过资源id，删除点赞记录
     *
     * @param resourceIds 资源id集合
     * @return 返回更新结果
     */
    int deleteSupportRecordByResourceId(List<Integer> resourceIds);

    /**
     * 通过资源id，彻底删除资源
     *
     * @param resourceIds 资源id集合
     * @return 返回更新结果
     */
    int realDeleteResourceByResourceId(List<Integer> resourceIds);

    /**
     * 分页获取需要审批的资源list集合
     *
     * @param search 搜索的内容
     * @param begin  取值的开始位置
     * @param number 需要取值的个数
     * @return 返回资源信息集合
     */
    List<UserResource> queryCheckResourceInfoListByPage(String search, int begin, int number);

    /**
     * 统计审批资源数量
     *
     * @param like
     * @return 返回审批资源数量
     */
    int countCheckResourceNumbers(String like);

    /**
     * 通过资源id集合，更新资源状态为通过审批
     *
     * @param idList 资源id集合
     * @param state  审批状态
     * @return 返回更新结果
     */
    int updateResourceStateByIdList(List<Integer> idList, String state);

    /**
     * 获取需要审批的资源
     *
     * @return 返回资源信息集合
     */
    List<UserResource> queryCheckResourceList();

}
