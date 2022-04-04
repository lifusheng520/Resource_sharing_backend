package com.sharing.serviceImpl;

import com.sharing.mapper.FavouriteMapper;
import com.sharing.pojo.Favourite;
import com.sharing.pojo.FavouriteFolder;
import com.sharing.service.FavouriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 李福生
 * @date 2022-4-4
 * @time 下午 01:34
 */

@Service
public class FavouriteServiceImpl implements FavouriteService {

    @Autowired
    private FavouriteMapper favouriteMapper;

    @Override
    public int initDefaultFavouriteFolder(int user_id) {
        return this.favouriteMapper.insertDefaultFavouriteFolder(user_id);
    }

    @Override
    public int getFavouriteRecordNumbers(int user_id, int resource_id) {
        return this.favouriteMapper.queryFavouriteRecordNumbers(user_id, resource_id);
    }

    @Override
    public int cancelFavouriteByUserAndResourceId(int user_id, int resource_id) {
        return this.favouriteMapper.deleteFavouriteByUserAndResourceId(user_id, resource_id);
    }

    @Override
    public int addFavourite(Favourite favourite) {
        return this.favouriteMapper.insertFavourite(favourite);
    }

    @Override
    public List<FavouriteFolder> getFavouriteFoldersByUserId(int user_id) {
        return this.favouriteMapper.queryFavouriteFoldersByUserId(user_id);
    }
}
