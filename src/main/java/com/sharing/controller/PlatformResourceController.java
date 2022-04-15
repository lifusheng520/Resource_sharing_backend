package com.sharing.controller;

import cn.hutool.core.util.StrUtil;
import com.sharing.Utils.ResponseCode;
import com.sharing.Utils.ResultFormatUtil;
import com.sharing.pojo.CompleteResourceInfo;
import com.sharing.pojo.MyPage;
import com.sharing.pojo.UserResource;
import com.sharing.service.PlatformResourceService;
import com.sharing.service.UserResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author 李福生
 * @date 2022-4-13
 * @time 下午 01:16
 */

@RestController
@RequestMapping("/platform")
public class PlatformResourceController {

    @Value("${files.resource.upload.root.path}")
    private String uploadRootPath;

    @Autowired
    private PlatformResourceService platformResourceService;

    @Autowired
    private UserResourceService userResourceService;

    /**
     * 获取系统内的资源集合
     *
     * @param params 请求参数
     * @return 返回平台资源的list
     */
    @PostMapping("/getResourceList")
    public String getResourceList(@RequestBody Map<String, String> params) {
        if (params == null || params.size() == 0)
            return ResultFormatUtil.format(ResponseCode.REQUEST_PARAM_ERROR, null);

        String search = params.get("search");
        String pageSize = params.get("pageSize");
        String currentPage = params.get("currentPage");
        String total = params.get("total");

        // 解析分页参数
        int page = MyPage.getValidTransfer(currentPage, "page");
        int size = MyPage.getValidTransfer(pageSize, "size");
        Integer totalPage = Integer.valueOf(total);

        List<CompleteResourceInfo> allResourceByPage;


        // 查询资源集合
        if (StrUtil.isEmpty(search)) {
            // 默认查询所有资源
            allResourceByPage = this.platformResourceService.getAllResourceByPage((page - 1) * size, size, null);
            if (totalPage < 0)
                totalPage = this.platformResourceService.countAllResourceBySearch(null);
        } else {
            // 按搜索查询
            allResourceByPage = this.platformResourceService.getAllResourceByPage((page - 1) * size, size, search);
            if (totalPage < 0)
                totalPage = this.platformResourceService.countAllResourceBySearch(search);
        }

        // 设置分页参数
        MyPage<CompleteResourceInfo> myPage = new MyPage<>();
        myPage.setPageSize(size);
        myPage.setCurrentPage(page);
        myPage.setTotal(totalPage);
        myPage.setPageList(allResourceByPage);

        ResponseCode responseCode = ResponseCode.GET_PLATFORM_RESOURCE_SUCCESS;
        return ResultFormatUtil.format(responseCode, myPage);
    }

    /**
     * 通过资源id，删除系统内的资源
     *
     * @param resourceIdList 需要删除的资源id集合
     * @return 返回删除结果
     */
    @PostMapping("/deleteResourceByIds")
    public String realDeleteResourceByIds(@RequestBody List<Integer> resourceIdList) {
        if (resourceIdList == null || resourceIdList.size() == 0)
            return ResultFormatUtil.format(ResponseCode.REQUEST_PARAM_ERROR, null);

        // 获取被删除的资源信息
        List<UserResource> resourceList = this.userResourceService.getUserResourceByIds(resourceIdList);

        // 根据资源的id，删除评论
        this.platformResourceService.deleteCommentByResourceId(resourceIdList);

        // 根据资源的id，删除收藏
        this.platformResourceService.deleteFavouriteByResourceId(resourceIdList);

        // 根据资源的id，删除点赞记录
        this.platformResourceService.deleteSupportRecordByResourceId(resourceIdList);

        // 根据资源的id，删除资源
        this.platformResourceService.realDeleteResourceByResourceId(resourceIdList);

        // 删除磁盘文件
        for (UserResource resource : resourceList) {
            String fileName = this.uploadRootPath + File.separator + resource.getDiscipline() + File.separator + resource.getDisk_name();
            this.deleteDiskFile(fileName);
        }

        ResponseCode responseCode = ResponseCode.PLATFORM_DELETE_RESOURCE_SUCCESS;
        return ResultFormatUtil.format(responseCode, null);
    }

    /**
     * 删除单个文件
     *
     * @param fileName 被删除文件的文件名
     * @return 单个文件删除成功返回true, 否则返回false
     */
    public boolean deleteDiskFile(String fileName) {
        File file = new File(fileName);
        if (file.isFile() && file.exists()) {
            file.delete();
            return true;
        } else {
            return false;
        }
    }

    /**
     * 分页获取需要审批的资源list
     *
     * @param pageParam 分页参数
     * @return 返回资源信息集合
     */
    @PostMapping("/getCheckResourceList")
    public String getCheckResourceList(@RequestBody Map<String, String> pageParam) {
        if (pageParam == null || pageParam.size() == 0)
            return ResultFormatUtil.format(ResponseCode.REQUEST_PARAM_ERROR, null);

        String search = pageParam.get("search");
        String currentPage = pageParam.get("currentPage");
        String pageSize = pageParam.get("pageSize");
        String total = pageParam.get("total");

        // 解析分页数据
        int page = MyPage.getValidTransfer(currentPage, "page");
        int size = MyPage.getValidTransfer(pageSize, "size");
        Integer totalPage = Integer.valueOf(total);

        // 获取需要审批的资源list
        List<UserResource> checkResourceList;
        if (StrUtil.isEmpty(search)) {
            checkResourceList = this.platformResourceService.getCheckResourceInfoListByPage(null, (page - 1) * size, size);
            if (totalPage < 0)
                totalPage = this.platformResourceService.countCheckResourceNumbers(null);
        } else {
            checkResourceList = this.platformResourceService.getCheckResourceInfoListByPage(search, (page - 1) * size, size);
            if (totalPage < 0)
                totalPage = this.platformResourceService.countCheckResourceNumbers(search);
        }

        // 设置分页参数
        MyPage<UserResource> myPage = new MyPage<>();
        myPage.setCurrentPage(page);
        myPage.setPageSize(size);
        myPage.setPageList(checkResourceList);
        myPage.setTotal(totalPage);

        ResponseCode responseCode = ResponseCode.GET_CHECK_RESOURCE_SUCCESS;
        return ResultFormatUtil.format(responseCode, myPage);
    }


    /**
     * 将资源id集合中对应的资源通过审批
     *
     * @param resourceIdList 资源的id集合
     * @return 返回资源审批结果
     */
    @PostMapping("/passResourceCheck")
    public String passCheckByResourceIdList(@RequestBody List<Integer> resourceIdList) {
        if (resourceIdList == null || resourceIdList.size() == 0)
            return ResultFormatUtil.format(ResponseCode.REQUEST_PARAM_ERROR, null);

        // 更新资源状态
        int i = this.platformResourceService.updateCheckStateByResourceIdList(resourceIdList, "已通过审批");
        ResponseCode responseCode;
        if (i > 0)
            responseCode = ResponseCode.CHECK_RESOURCE_SUCCESS;
        else
            responseCode = ResponseCode.CHECK_RESOURCE_FAIL;
        return ResultFormatUtil.format(responseCode, null);
    }

    /**
     * 将资源id集合中对应的审批资源删除
     *
     * @param deleteResourceIdList 需要删除的资源的id集合
     * @return 返回删除结果
     */
    @PostMapping("/deleteCheckResource")
    public String deleteCheckResourceByIdList(@RequestBody List<Integer> deleteResourceIdList) {
        String result = this.realDeleteResourceByIds(deleteResourceIdList);
        return result;
    }

}
