package com.sharing.controller;

import cn.hutool.core.util.StrUtil;
import com.sharing.Utils.ResponseCode;
import com.sharing.Utils.ResultFormatUtil;
import com.sharing.pojo.Favourite;
import com.sharing.pojo.FavouriteFolder;
import com.sharing.pojo.MyPage;
import com.sharing.pojo.UserFavouriteAndResourceInfo;
import com.sharing.service.FavouriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    /**
     * 根据用户id获取用户的所有收藏夹
     *
     * @param user_id 用户id
     * @return 返回用户收藏夹list
     */
    @GetMapping("/getFolders/{user_id}")
    public String getFolderList(@PathVariable Integer user_id) {
        if (user_id == null || user_id == 0)
            return ResultFormatUtil.format(ResponseCode.REQUEST_PARAM_ERROR, null);

        // 查询用户id下的所有收藏文件夹
        List<FavouriteFolder> folders = this.favouriteService.getFavouriteFoldersByUserId(user_id);

        return ResultFormatUtil.format(ResponseCode.GET_FAVOURITE_FOLDER_SUCCESS, folders);
    }

    /**
     * 添加收藏文件夹
     *
     * @param params 收藏夹参数
     * @return 返回添加成功的文件夹信息
     */
    @PostMapping("/addFolder")
    public String addFolder(@RequestBody Map<String, String> params) {
        String userId = params.get("user_id");
        String folder_name = params.get("folder_name");
        if (StrUtil.isEmpty(userId))
            return ResultFormatUtil.format(ResponseCode.REQUEST_PARAM_ERROR, null);

        ResponseCode responseCode;
        FavouriteFolder folder = null;
        Integer user_id = Integer.valueOf(userId);
        // 查询收藏夹是否已经存在
        int number = this.favouriteService.getFolderNumber(user_id, folder_name);
        if (number > 0) {
            // 收藏夹已经存在，不再添加
            responseCode = ResponseCode.FAVOURITE_FOLDER_EXISTS;
        } else {
            // 不存在，添加收藏夹
            folder = new FavouriteFolder();
            folder.setUser_id(user_id);
            folder.setFolder_name(folder_name);
            folder.setTime(new Date());
            int i = this.favouriteService.addFavouriteFolder(folder);

            // 获取收藏夹的id
            int id = this.favouriteService.getInsertFolderId(user_id, folder_name);
            folder.setId(id);
            if (i > 0)
                responseCode = ResponseCode.ADD_FAVOURITE_FOLDER_SUCCESS;
            else
                responseCode = ResponseCode.ADD_FAVOURITE_FOLDER_FAIL;
        }

        return ResultFormatUtil.format(responseCode, folder);
    }


    /**
     * 添加资源到收藏夹
     *
     * @param params 选择的收藏夹
     * @return 返回执行结果json
     */
    @PostMapping("/add")
    public String addFavourite(@RequestBody Map<String, Object> params) {
        Object selectFoldersList = params.get("selectFoldersList");
        Object favourite = params.get("favourite");
        if (params.size() == 0 || selectFoldersList == null || favourite == null)
            return ResultFormatUtil.format(ResponseCode.REQUEST_PARAM_ERROR, params);

        Map<String, Object> favouriteMap = (Map<String, Object>) favourite;
        List selectFolders = (List) selectFoldersList;

        // 遍历提取选中的收藏夹参数
        ArrayList<FavouriteFolder> selectFolderList = new ArrayList<>();
        for (Object folderMap : selectFolders) {
            Map<String, Object> folder = (Map<String, Object>) folderMap;
            FavouriteFolder temp = new FavouriteFolder();
            temp.setId((Integer) folder.get("id"));
            temp.setUser_id((Integer) folder.get("user_id"));
            temp.setFolder_name(folder.get("folder_name").toString());
            selectFolderList.add(temp);
        }

        // 提取收藏者的id和资源的id
        String user_id = favouriteMap.get("user_id").toString();
        String resource_id = favouriteMap.get("resource_id").toString();
        Integer userId = Integer.valueOf(user_id);
        Integer resourceId = Integer.valueOf(resource_id);

        // 查询收藏该资源的收藏夹
        List<String> folderNameList = this.favouriteService.getFavouriteRecord(userId, resourceId);

        List<Favourite> addFavouriteList = new ArrayList<>();
        List<String> cancelFolderNameList = new ArrayList<>();
        for (FavouriteFolder item : selectFolderList)
            // 不包含，说明收藏夹没有该资源，添加即可
            if (!folderNameList.contains(item.getFolder_name())) {
                Favourite af = new Favourite();
                af.setUser_id(userId);
                af.setResource_id(resourceId);
                af.setFolder_name(item.getFolder_name());
                af.setTime(new Date());
                af.setFolder_id(item.getId());
                addFavouriteList.add(af);
            }
        for (String name : folderNameList) {
            boolean flag = false;
            for (FavouriteFolder folder : selectFolderList)
                if (folder.getFolder_name().equals(name)) {
                    flag = true;
                    break;
                }
            // 原先的记录现在没被选中，取消即可
            if (!flag)
                cancelFolderNameList.add(name);
        }

        ResponseCode responseCode = ResponseCode.ADD_FAVOURITE_FOLDER_SUCCESS;

        // 有需要添加，将其添加到数据库
        if (addFavouriteList.size() != 0)
            this.favouriteService.addFavouriteByList(addFavouriteList);
        // 有需要取消的收藏
        if (cancelFolderNameList.size() != 0)
            this.favouriteService.cancelFavouriteByIdAndFolderNameList(userId, resourceId, cancelFolderNameList);

        return ResultFormatUtil.format(responseCode, addFavouriteList);
    }

    /**
     * 获取已经收藏的记录
     *
     * @param user_id     用户id
     * @param resource_id 资源id
     * @return 返回用户的对应资源的收藏记录
     */
    @GetMapping("/getRecord/{user_id}/{resource_id}")
    public String getFavouriteRecord(@PathVariable Integer user_id, @PathVariable Integer resource_id) {
        if (user_id == 0 || resource_id == 0)
            return ResultFormatUtil.format(ResponseCode.REQUEST_PARAM_ERROR, null);

        // 查询记录
        List<String> record = this.favouriteService.getFavouriteRecord(user_id, resource_id);

        ResponseCode responseCode = ResponseCode.GET_FAVOURITE_RECORD_SUCCESS;
        return ResultFormatUtil.format(responseCode, record);
    }

    /**
     * 分页获取用户收藏数据
     *
     * @param params 分页和用户信息参数
     * @return 返回收藏的分页数据json
     */
    @PostMapping("/get")
    public String getFavouritePageData(@RequestBody Map<String, String> params) {
        // 获取请求参数
        String user_id = params.get("user_id");
        String folder_name = params.get("folder_name");
        String currentPage = params.get("currentPage");
        String totalPage = params.get("total");
        String pageSize = params.get("pageSize");
        if (StrUtil.isEmpty(user_id) || StrUtil.isEmpty(folder_name))
            return ResultFormatUtil.format(ResponseCode.REQUEST_PARAM_ERROR, null);

        Integer userId = Integer.valueOf(user_id);
        // 解析分页参数
        int page = MyPage.getValidTransfer(currentPage, "page");
        int size = MyPage.getValidTransfer(pageSize, "size");
        Integer total = Integer.valueOf(totalPage);

        // 根据分页参数查询页面数据
        List<UserFavouriteAndResourceInfo> favouriteListByPage = this.favouriteService.getFavouriteListByPage(userId, folder_name, (page - 1) * size, size);

        if (total < 0)
            total = this.favouriteService.countFolderResourceNumber(userId, folder_name);

        // 设置分页数据
        MyPage<UserFavouriteAndResourceInfo> myPage = new MyPage<>();
        myPage.setCurrentPage(page);
        myPage.setTotal(total);
        myPage.setPageSize(size);
        myPage.setPageList(favouriteListByPage);

        return ResultFormatUtil.format(ResponseCode.GET_FAVOURITE_RESOURCE_PAGE_SUCCESS, myPage);
    }

    /**
     * 将list中包含的id对应的收藏取消
     *
     * @param cancelIdList 需要取消的收藏的id集合
     * @return 返回更新结果
     */
    @PostMapping("/cancel")
    public String cancelFavourite(@RequestBody List<Integer> cancelIdList) {
        if (cancelIdList == null || cancelIdList.size() == 0)
            return ResultFormatUtil.format(ResponseCode.REQUEST_PARAM_ERROR, null);

        // 取消收藏
        int i = this.favouriteService.cancelFavourite(cancelIdList);
        ResponseCode responseCode;
        if (i > 0)
            responseCode = ResponseCode.FAVOURITE_CANCEL_SUCCESS;
        else
            responseCode = ResponseCode.FAVOURITE_CANCEL_FAIL;
        return ResultFormatUtil.format(responseCode, cancelIdList);
    }

    /**
     * 移动收藏内容
     *
     * @param params 请求参数
     * @return 返回更新状态
     */
    @PostMapping("/move")
    public String moveFavourite(@RequestBody Map<String, Object> params) {
        Object selectList = params.get("selectList");
        Object selectFolder = params.get("selectFolder");
        if (selectList == null || selectFolder == null)
            return ResultFormatUtil.format(ResponseCode.REQUEST_PARAM_ERROR, null);

        List<Integer> favouriteIdList = (List) selectList;
        Map<String, Object> folderMap = (Map) selectFolder;
        if (favouriteIdList.size() == 0 || folderMap.size() == 0)
            return ResultFormatUtil.format(ResponseCode.REQUEST_PARAM_ERROR, null);

        // 获取收藏夹参数
        Integer id = (Integer) folderMap.get("id");
        Integer user_id = (Integer) folderMap.get("user_id");
        String folder_name = (String) folderMap.get("folder_name");
        FavouriteFolder folder = new FavouriteFolder();
        folder.setId(id);
        folder.setUser_id(user_id);
        folder.setFolder_name(folder_name);

        // 变更收藏的收藏夹
        int i = this.favouriteService.moveFavourite(favouriteIdList, folder);

        ResponseCode responseCode;
        if (i > 0)
            responseCode = ResponseCode.MOVE_FAVOURITE_SUCCESS;
        else
            responseCode = ResponseCode.MOVE_FAVOURITE_FAIL;
        return ResultFormatUtil.format(responseCode, null);
    }

    /**
     * 复制收藏内容
     *
     * @param params 请求参数
     * @return 返回更新状态
     */
    @PostMapping("/copy")
    public String copyFavourite(@RequestBody Map<String, Object> params) {
        Object favouriteIdList = params.get("favouriteIdList");
        Object selectFolderInfo = params.get("selectFolder");
        if (favouriteIdList == null || selectFolderInfo == null)
            return ResultFormatUtil.format(ResponseCode.REQUEST_PARAM_ERROR, null);

        List<Integer> idList = (List) favouriteIdList;
        Map<String, Object> folderMap = (Map) selectFolderInfo;
        if (idList.size() == 0 || folderMap.size() == 0)
            return ResultFormatUtil.format(ResponseCode.REQUEST_PARAM_ERROR, null);

        // 获取收藏夹参数
        Integer id = (Integer) folderMap.get("id");
        Integer user_id = (Integer) folderMap.get("user_id");
        String folder_name = (String) folderMap.get("folder_name");
        FavouriteFolder folder = new FavouriteFolder();
        folder.setId(id);
        folder.setUser_id(user_id);
        folder.setFolder_name(folder_name);

        // 将收藏内容复制到收藏夹
        int i = this.favouriteService.copyFavourite(idList, folder);

        ResponseCode responseCode;
        if (i > 0)
            responseCode = ResponseCode.COPY_FAVOURITE_SUCCESS;
        else
            responseCode = ResponseCode.COPY_FAVOURITE_FAIL;
        return ResultFormatUtil.format(responseCode, null);
    }


    /**
     * 分页获取用户的收藏夹信息
     *
     * @param params 请求参数
     * @return 返回更新状态
     */
    @PostMapping("/getFolders")
    public String getFavouriteFolderByPage(@RequestBody Map<String, String> params) {
        if (params == null || params.size() == 0 || StrUtil.isEmpty(params.get("user_id")))
            return ResultFormatUtil.format(ResponseCode.REQUEST_PARAM_ERROR, null);
        String currentPage = params.get("currentPage");
        String totalPage = params.get("total");
        String pageSize = params.get("pageSize");
        String user_id = params.get("user_id");

        // 获取分页数据
        int page = MyPage.getValidTransfer(currentPage, "page");
        int size = MyPage.getValidTransfer(pageSize, "size");
        Integer userId = Integer.valueOf(user_id);
        Integer total = Integer.valueOf(totalPage);

        // 分页查询收藏夹信息
        List<FavouriteFolder> foldersByPage = this.favouriteService.getFavouriteFoldersByPage(userId, (page - 1) * size, size);

        // 设置分页参数
        MyPage<FavouriteFolder> myPage = new MyPage<>();
        myPage.setCurrentPage(page);
        myPage.setPageSize(size);
        myPage.setPageList(foldersByPage);

        if (total < 0)
            total = this.favouriteService.countFavouriteFolderNumber(userId);
        myPage.setTotal(total);

        ResponseCode responseCode = ResponseCode.GET_FAVOURITE_FOLDER_PAGE_SUCCESS;
        return ResultFormatUtil.format(responseCode, myPage);
    }

    /**
     * 将list集合中的收藏夹删除，并且收藏夹的内容也一并删除，默认收藏夹不删除
     *
     * @param folderList 收藏夹集合
     * @return 返回更新的结果
     */
    @PostMapping("/deleteFolders")
    public String deleteFavouriteFoldersByFolderList(@RequestBody List<FavouriteFolder> folderList) {
        if (folderList == null || folderList.size() == 0)
            return ResultFormatUtil.format(ResponseCode.REQUEST_PARAM_ERROR, null);

        ArrayList<Integer> idList = new ArrayList<>();
        for (FavouriteFolder folder : folderList)
            if (!"默认收藏夹".equals(folder.getFolder_name()))
                idList.add(folder.getId());

        // 删除收藏夹的收藏内容
        this.favouriteService.cancelFavouriteByFolderIdList(idList);

        // 删除收藏夹
        int i1 = this.favouriteService.deleteFavouriteFoldersByIdList(idList);

        ResponseCode responseCode;
        if (i1 > 0)
            responseCode = ResponseCode.DELETE_FAVOURITE_FOLDER_SUCCESS;
        else
            responseCode = ResponseCode.DELETE_FAVOURITE_FOLDER_FAIL;

        return ResultFormatUtil.format(responseCode, null);
    }


}
