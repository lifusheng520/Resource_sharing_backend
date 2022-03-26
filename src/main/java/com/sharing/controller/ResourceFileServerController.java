package com.sharing.controller;

import com.sharing.Utils.ResponseCode;
import com.sharing.Utils.ResultFormatUtil;
import com.sharing.pojo.IndexData;
import com.sharing.pojo.UserAndResource;
import com.sharing.service.ResourceDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

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

        // 设置头像URL
        String headIcon = userResourceDetail.getUserInfo().getHeadIcon();
        String iconURL;
        if(headIcon == null || "".equals(headIcon))
            iconURL = "";
        else
            iconURL = this.iconHostURL + headIcon;
        userResourceDetail.getUserInfo().setHeadIcon(iconURL);


        return ResultFormatUtil.format(ResponseCode.GET_RESOURCE_DETAIL_SUCCESS, userResourceDetail);
    }


}
