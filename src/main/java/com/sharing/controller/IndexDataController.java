package com.sharing.controller;

import com.sharing.Utils.ResponseCode;
import com.sharing.Utils.ResultFormatUtil;
import com.sharing.pojo.IndexData;
import com.sharing.pojo.UserAndResource;
import com.sharing.pojo.UserResource;
import com.sharing.service.IndexDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 访问网站首页时需要的数据controller
 *
 * @author 李福生
 * @date 2022-3-21
 * @time 上午 10:41
 */

@RestController
@RequestMapping("/index")
public class IndexDataController {

    @Autowired
    private IndexDataService indexDataService;

    /**
     * 获取系统的使用信息
     *
     * @return 返回系统信息
     */
    @GetMapping("/systemInfo")
    public String getSystemUseInfo() {
        IndexData indexData = new IndexData();
        // 获取首页数据
        int systemUserNumbers = this.indexDataService.getSystemUserNumbers();
        int systemResourceNumbers = this.indexDataService.getSystemResourceNumbers();
        int resourceDownloads = this.indexDataService.getResourceDownloads();
        indexData.setUserNumber(systemUserNumbers);
        indexData.setResourceNumber(systemResourceNumbers);
        indexData.setDownloadTimes(resourceDownloads);

        return ResultFormatUtil.format(ResponseCode.INDEX_SYSTEM_INFO_GET_SUCCESS, indexData);
    }

    /**
     * 获取学科推荐资源
     *
     * @return 每个科目中的顶级资源
     */
    @GetMapping("/disciplineRecommend")
    public String getRecommendResource() {
        // 获取首页推荐资源list
        List<UserAndResource> userAndResourceList = this.indexDataService.getEachDisciplineResourceList();

        return ResultFormatUtil.format(ResponseCode.INDEX_SYSTEM_GET_RECOMMEND_SUCCESS, userAndResourceList);
    }

}
