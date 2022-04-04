package com.sharing.service;

import com.sharing.pojo.Favourite;
import com.sharing.pojo.FavouriteFolder;

import java.util.List;

/**
 * 收藏业务接口
 *
 * @author 李福生
 * @date 2022-4-4
 * @time 下午 01:33
 */
public interface FavouriteService {

    /**
     * 根据用户id，初始化默认收藏夹
     *
     * @param user_id 用户id
     * @return 返回更新结果
     */
    int initDefaultFavouriteFolder(int user_id);

    /**
     * 根据用户id和资源id查询收藏的记录
     *
     * @param user_id     用户id
     * @param resource_id 资源id
     * @return 返回记录的数目
     */
    int getFavouriteRecordNumbers(int user_id, int resource_id);

    /**
     * 通过对应的用户id和资源id，取消收藏
     *
     * @param user_id     用户id
     * @param resource_id 资源id
     * @return 返回更新结果
     */
    int cancelFavouriteByUserAndResourceId(int user_id, int resource_id);

    /**
     * 添加一个收藏
     *
     * @param favourite 收藏信息
     * @return 返回更新结果
     */
    int addFavourite(Favourite favourite);

    /**
     * 通过用户id，获取用户所有的收藏文件夹
     *
     * @param user_id 用户id
     * @return 返回用户收藏文件夹list
     */
    List<FavouriteFolder> getFavouriteFoldersByUserId(int user_id);

}
