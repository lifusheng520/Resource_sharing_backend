package com.sharing.mapper;

import com.sharing.pojo.Favourite;
import com.sharing.pojo.FavouriteFolder;
import com.sharing.pojo.UserFavouriteAndResourceInfo;
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
     * @return 返回收藏记录
     */
    List<String> queryFavouriteRecord(int user_id, int resource_id);

    /**
     * 通过对应的用户id和资源id，将收藏夹名字包含于folderNames中的收藏记录删除
     *
     * @param user_id     用户id
     * @param resource_id 资源id
     * @param folderNames 取消的收藏夹名称list
     * @return 返回更新结果
     */
    int deleteFavouriteByIdAndFolderNameList(int user_id, int resource_id, List<String> folderNames);


    /**
     * 将批量插入收藏内容
     *
     * @param favouriteList 收藏信息
     * @return 返回更新结果
     */
    int insertFavouriteByList(List<Favourite> favouriteList);

    /**
     * 通过用户id，获取用户所有的收藏文件夹
     *
     * @param user_id 用户id
     * @return 返回用户收藏文件夹list
     */
    List<FavouriteFolder> queryFavouriteFoldersByUserId(int user_id);

    /**
     * 查询用户id对应的收藏夹文件名的数量
     *
     * @param user_id     用户id
     * @param folder_name
     * @return
     */
    int queryFolderNumber(int user_id, String folder_name);

    /**
     * 添加一个收藏夹
     *
     * @param folder 收藏夹信息
     * @return 返回更新结果
     */
    int insertFavouriteFolder(FavouriteFolder folder);


    /**
     * 根据用户id查询收藏夹下的资源
     *
     * @param user_id     用户id
     * @param folder_name 收藏夹名称
     * @param begin       开始取值的位置
     * @param number      需要取值的个数
     * @return 返回收藏和资源信息list
     */
    List<UserFavouriteAndResourceInfo> queryFavouriteListByPage(int user_id, String folder_name, int begin, int number);

    /**
     * 统计用户的某个收藏夹下的资源数量
     *
     * @param user_id     用户id
     * @param folder_name 收藏夹名称
     * @return 返回收藏夹下的资源总数
     */
    int countFolderResourceNumber(int user_id, String folder_name);

    /**
     * 根据list集合中的收藏id，将id对应的收藏删除
     *
     * @param idList 收藏的id集合
     * @return 返回更新结果
     */
    int deleteFavouriteByIds(List<Integer> idList);

    /**
     * 将收藏从原来的收藏夹移动到新的收藏夹
     *
     * @param favouriteIdList 收藏的id集合
     * @param newFolder       新的收藏夹
     * @return 返回更新结果
     */
    int updateFavouriteFolder(List<Integer> favouriteIdList, FavouriteFolder newFolder);

    /**
     * 根据收藏的id集合获取收藏信息
     *
     * @param favouriteIdList 收藏的id集合
     * @return 返回收藏内容list集合
     */
    List<Favourite> queryFavouriteByIdList(List<Integer> favouriteIdList);

}
