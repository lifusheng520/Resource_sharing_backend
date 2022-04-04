package com.sharing.mapper;

import com.sharing.pojo.Favourite;
import com.sharing.pojo.FavouriteFolder;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 数据库收藏表操作mapper
 *
 * @author 李福生
 * @date 2022-4-4
 * @time 下午 01:35
 */

@Mapper
@Repository
public interface FavouriteMapper {

    /**
     * 根据用户id，插入一条默认收藏夹
     *
     * @param user_id 用户id
     * @return 返回更新结果
     */
    int insertDefaultFavouriteFolder(int user_id);

    /**
     * 根据用户id和资源id查询收藏的记录
     *
     * @param user_id     用户id
     * @param resource_id 资源id
     * @return 返回记录的数目
     */
    int queryFavouriteRecordNumbers(int user_id, int resource_id);

    /**
     * 删除用户id和资源id对应的收藏记录
     *
     * @param user_id     用户id
     * @param resource_id 资源id
     * @return 返回更新结果
     */
    int deleteFavouriteByUserAndResourceId(int user_id, int resource_id);

    /**
     * 添加一条收藏记录
     *
     * @param favourite 收藏信息
     * @return 返回更新结果
     */
    int insertFavourite(Favourite favourite);

    /**
     * 通过用户id，获取用户所有的收藏文件夹
     *
     * @param user_id 用户id
     * @return 返回用户收藏文件夹list
     */
    List<FavouriteFolder> queryFavouriteFoldersByUserId(int user_id);

}
