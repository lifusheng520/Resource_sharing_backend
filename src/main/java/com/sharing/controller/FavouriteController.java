package com.sharing.controller;

import cn.hutool.core.util.StrUtil;
import com.sharing.Utils.ResponseCode;
import com.sharing.Utils.ResultFormatUtil;
import com.sharing.pojo.Favourite;
import com.sharing.pojo.FavouriteFolder;
import com.sharing.service.FavouriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 资源收藏业务controller
 *
 * @author 李福生
 * @date 2022-4-3
 * @time 下午 08:15
 */

@RestController
@RequestMapping("/favourite")
public class FavouriteController {

    @Autowired
    private FavouriteService favouriteService;

    @GetMapping("/getFolders/{user_id}")
    public String getFolderList(@PathVariable Integer user_id) {
        if (user_id == null || user_id == 0)
            return ResultFormatUtil.format(ResponseCode.REQUEST_PARAM_ERROR, null);

        // 查询用户id下的所有收藏文件夹
        List<FavouriteFolder> folders = this.favouriteService.getFavouriteFoldersByUserId(user_id);

        return ResultFormatUtil.format(ResponseCode.GET_FAVOURITE_FOLDER_SUCCESS, folders);
    }


    /**
     * 添加一条资源收藏
     *
     * @param params 收藏参数
     * @return 返回执行结果json
     */
    @PostMapping("/add")
    public String addFavourite(@RequestBody Map<String, String> params) {
        String userId = params.get("user_id");
        String resourceId = params.get("resource_id");
        String folderId = params.get("folder_id");
        String folder = params.get("folder");
        if (StrUtil.isEmpty(userId) || StrUtil.isEmpty(resourceId) || StrUtil.isEmpty(folderId))
            return ResultFormatUtil.format(ResponseCode.REQUEST_PARAM_ERROR, null);

        // 创建一个收藏对象，并设置其参数
        Integer user_id = Integer.valueOf(userId);
        Integer resource_id = Integer.valueOf(resourceId);
        Integer folder_id = Integer.valueOf(folderId);

        ResponseCode responseCode;
        // 判断是否存在
        int numbers = this.favouriteService.getFavouriteRecordNumbers(user_id, resource_id);
        if (numbers > 0) {    // 已经存在，则取消收藏
            int i = this.favouriteService.cancelFavouriteByUserAndResourceId(user_id, resource_id);
            if (i > 0)
                responseCode = ResponseCode.FAVOURITE_CANCEL_SUCCESS;
            else
                responseCode = ResponseCode.FAVOURITE_CANCEL_FAIL;
        } else { // 不存在添加收藏
            Favourite favourite = new Favourite();
            favourite.setUser_id(user_id);
            favourite.setResource_id(resource_id);
            favourite.setTime(new Date());
            favourite.setFolder_id(folder_id);
            favourite.setFolder_name(folder);
            int i = this.favouriteService.addFavourite(favourite);
            if (i > 0)
                responseCode = ResponseCode.FAVOURITE_ADD_SUCCESS;
            else
                responseCode = ResponseCode.FAVOURITE_ADD_FAIL;
        }

        return ResultFormatUtil.format(responseCode, null);
    }

}
