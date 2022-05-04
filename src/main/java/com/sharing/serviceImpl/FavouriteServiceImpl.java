package com.sharing.serviceImpl;

import com.sharing.mapper.FavouriteMapper;
import com.sharing.pojo.Favourite;
import com.sharing.pojo.FavouriteFolder;
import com.sharing.pojo.UserFavouriteAndResourceInfo;
import com.sharing.service.FavouriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author 李福生
 * @date 2022-4-4
 * @time 下午 01:34
 */

@Service
public class FavouriteServiceImpl implements FavouriteService {

    @Value("${files.icon.host.url}")
    private String iconHostURL;

    @Autowired
    private FavouriteMapper favouriteMapper;

    @Override
    public int initDefaultFavouriteFolder(int user_id) {
        return this.favouriteMapper.insertDefaultFavouriteFolder(user_id);
    }

    @Override
    public List<String> getFavouriteRecord(int user_id, int resource_id) {
        return this.favouriteMapper.queryFavouriteRecord(user_id, resource_id);
    }

    @Override
    public int cancelFavouriteByIdAndFolderNameList(int user_id, int resource_id, List<String> folderNames) {
        return this.favouriteMapper.deleteFavouriteByIdAndFolderNameList(user_id, resource_id, folderNames);
    }

    @Override
    public int addFavouriteByList(List<Favourite> favouriteList) {
        return this.favouriteMapper.insertFavouriteByList(favouriteList);
    }

    @Override
    public List<FavouriteFolder> getFavouriteFoldersByUserId(int user_id) {
        return this.favouriteMapper.queryFavouriteFoldersByUserId(user_id);
    }

    @Override
    public int getFolderNumber(int user_id, String folder_name) {
        return this.favouriteMapper.queryFolderNumber(user_id, folder_name);
    }

    @Override
    public int addFavouriteFolder(FavouriteFolder folder) {
        return this.favouriteMapper.insertFavouriteFolder(folder);
    }

    @Override
    public List<UserFavouriteAndResourceInfo> getFavouriteListByPage(int user_id, String folder_name, int begin, int number) {
        List<UserFavouriteAndResourceInfo> resultList = this.favouriteMapper.queryFavouriteListByPage(user_id, folder_name, begin, number);
        for (UserFavouriteAndResourceInfo info : resultList) {
            String icon = info.getUserInfo().getHeadIcon();
            if (icon != null && !"".equals(icon)) {
                String headIcon = this.iconHostURL + icon;
                info.getUserInfo().setHeadIcon(headIcon);
            }
        }
        return resultList;
    }

    @Override
    public int countFolderResourceNumber(int user_id, String folder_name) {
        return this.favouriteMapper.countFolderResourceNumber(user_id, folder_name);
    }

    @Override
    public int cancelFavourite(List<Integer> idList) {
        return this.favouriteMapper.deleteFavouriteByIds(idList);
    }

    @Override
    public int moveFavourite(List<Integer> favouriteIdList, FavouriteFolder newFolder) {
        return this.favouriteMapper.updateFavouriteFolder(favouriteIdList, newFolder);
    }

    @Override
    public int copyFavourite(List<Integer> favouriteIdList, FavouriteFolder newFolder) {
        // 根据收藏的id查询收藏的内容信息
        List<Favourite> favourites = this.favouriteMapper.queryFavouriteByIdList(favouriteIdList);
        // 设置新的收藏信息
        for (Favourite favourite : favourites) {
            favourite.setTime(new Date());
            favourite.setFolder_id(newFolder.getId());
            favourite.setFolder_name(newFolder.getFolder_name());
        }
        int i = this.favouriteMapper.insertFavouriteByList(favourites);
        return i;
    }

    @Override
    public List<FavouriteFolder> getFavouriteFoldersByPage(int user_id, int begin, int number) {
        List<FavouriteFolder> folders = this.favouriteMapper.queryFavouriteFoldersByPage(user_id, begin, number);
        return folders;
    }

    @Override
    public int countFavouriteFolderNumber(int user_id) {
        return this.favouriteMapper.countFavouriteFolderNumber(user_id);
    }

    @Override
    public int clearOvertimeDeletedRecord(int days) {
        return this.favouriteMapper.clearOvertimeDeletedRecord(days);
    }

    @Override
    public List<Integer> getOverTimeDeletedResourceIdList(int days) {
        return this.favouriteMapper.queryOverTimeDeletedResourceIdList(days);
    }

    @Override
    public int cancelFavouriteByFolderIdList(List<Integer> folderIdList) {
        return this.favouriteMapper.deleteFavouriteByFolderIdList(folderIdList);
    }

    @Override
    public int deleteFavouriteFoldersByIdList(List<Integer> idList) {
        return this.favouriteMapper.deleteFavouriteFoldersByIdList(idList);
    }

    @Override
    public int getInsertFolderId(int user_id, String folder_name) {
        return this.favouriteMapper.queryInsertFolderId(user_id, folder_name);
    }

}
