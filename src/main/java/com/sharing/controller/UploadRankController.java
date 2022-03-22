package com.sharing.controller;

import com.sharing.Utils.ResponseCode;
import com.sharing.Utils.ResultFormatUtil;
import com.sharing.pojo.UploadRankInfo;
import com.sharing.pojo.UserInfo;
import com.sharing.service.IndexDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.List;

/**
 * 上传资源排行榜页面的controller
 *
 * @author 李福生
 * @date 2022-3-22
 * @time 上午 09:52
 */

@RestController
@RequestMapping("/rank")
public class UploadRankController {

    @Autowired
    private IndexDataService indexDataService;

    @Value("${http://localhost:8080/user/icon/}")
    private String iconHostUrl;

    @GetMapping("/upload")
    public String getUploadRank() {
        List<UploadRankInfo> uploadMostUserList = this.indexDataService.getUploadMostUserList(10);

        // 遍历排行榜信息，设置头像的URL
        for (UploadRankInfo info : uploadMostUserList) {
            String icon = info.getHeadIcon();
            String headIcon;
            if (icon == null || "".equals(icon))
                headIcon = this.iconHostUrl + "ico.png";
            else
                headIcon = this.iconHostUrl + icon;
            info.setHeadIcon(headIcon);
        }

        return ResultFormatUtil.format(ResponseCode.GET_RECOMMEND_RANK_INFO_SUCCESS, uploadMostUserList);
    }
}
