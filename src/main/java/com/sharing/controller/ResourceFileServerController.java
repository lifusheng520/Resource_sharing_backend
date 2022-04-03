package com.sharing.controller;

import com.sharing.Utils.ResponseCode;
import com.sharing.Utils.ResultFormatUtil;
import com.sharing.pojo.IndexData;
import com.sharing.pojo.MyPage;
import com.sharing.pojo.UserAndResource;
import com.sharing.service.ResourceDetailService;
import com.sharing.service.SupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 资源服务controller接口
 *
 * @author 李福生
 * @date 2022-3-22
 * @time 下午 09:35
 */

@RestController
@RequestMapping("/resource/server")
public class ResourceFileServerController {

    @Autowired
    private ResourceDetailService resourceDetailService;

    @Autowired
    private SupportService supportService;

    @Value("${files.icon.host.url}")
    private String iconHostURL;

    /**
     * 获取资源种类
     *
     * @return 返回资源种类list
     */
    @GetMapping("/discipline")
    public String getAllResourceDiscipline() {
        return ResultFormatUtil.format(ResponseCode.GET_ALL_RESOURCE_DISCIPLINE_SUCCESS, IndexData.discipline);
    }


    /**
     * 根据资源的id，获取资源的完整详细信息
     *
     * @param resourceId 资源id
     * @return 资源完整信息的json
     */
    @GetMapping("/detail/{resourceId}")
    public String getResourceDetails(@PathVariable Integer resourceId) {
        if (resourceId == null || resourceId == 0)
            return ResultFormatUtil.format(ResponseCode.REQUEST_PARAM_ERROR, "id");

        UserAndResource userResourceDetail = this.resourceDetailService.getUserResourceDetail(resourceId);
        // 查询点赞数量
        int id = userResourceDetail.getResource().getId();
        int number = this.supportService.countResourceSupportNumbers(id);
        userResourceDetail.getResource().setSupportNumber(number);

        // 设置头像URL
        String headIcon = userResourceDetail.getUserInfo().getHeadIcon();
        String iconURL;
        if (headIcon == null || "".equals(headIcon))
            iconURL = "";
        else
            iconURL = this.iconHostURL + headIcon;
        userResourceDetail.getUserInfo().setHeadIcon(iconURL);


        return ResultFormatUtil.format(ResponseCode.GET_RESOURCE_DETAIL_SUCCESS, userResourceDetail);
    }


    /**
     * 根据用户id，获取用户的资源信息
     *
     * @param params 请求参数
     * @return 返回用户的分页查询json数据
     */
    @PostMapping("/getList")
    public String getUserResourceById(@RequestBody Map<String, String> params) {
        // 获取请求参数
        String userId = params.get("user_id");
        String pageSize = params.get("pageSize");
        String currentPage = params.get("currentPage");
        String totalPage = params.get("total");

        if (userId == null || "".equals(userId))
            return ResultFormatUtil.format(ResponseCode.REQUEST_PARAM_ERROR, null);

        // 获取分页参数
        int size = MyPage.getValidTransfer(pageSize, "size");
        int page = MyPage.getValidTransfer(currentPage, "page");

        // 查询用户资源信息
        int user_id = Integer.valueOf(userId);
        List<UserAndResource> userAndResources = this.resourceDetailService.getFocusUserResourceByUserId(user_id, (page - 1) * size, size);

        // 设置分页参数
        MyPage<UserAndResource> myPage = new MyPage<>();
        myPage.setPageSize(size);
        myPage.setCurrentPage(page);
        myPage.setPageList(userAndResources);

        int total = Integer.valueOf(totalPage);
        if (total < 0)
            total = this.resourceDetailService.countUserResourceNumbers(user_id);

        myPage.setTotal(total);

        return ResultFormatUtil.format(ResponseCode.GET_FOCUS_USER_RESOURCE_SUCCESS, myPage);
    }



}
