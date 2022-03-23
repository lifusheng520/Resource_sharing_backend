package com.sharing.controller;

import com.sharing.Utils.ResponseCode;
import com.sharing.Utils.ResultFormatUtil;
import com.sharing.pojo.MyPage;
import com.sharing.pojo.UserResource;
import com.sharing.service.ResourceRecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 资源推荐业务接口
 *
 * @author 李福生
 * @date 2022-3-23
 * @time 下午 04:31
 */

@RestController
@RequestMapping("/resource/recommend")
public class ResourceRecommendationController {

    @Autowired
    private ResourceRecommendService recommendService;

    /**
     * 获取默认的推荐资源接口
     *
     * @param params 分页数据
     * @return 返回一个包装资源list的分页对象json
     */
    @PostMapping("/default")
    public String getDefaultResource(@RequestBody Map<String, String> params) {
//        {currentPage=-1, totalPages=-1, pageSize=10}
        // 获取页面参数
        String currentPage = params.get("currentPage");
        String pageSize = params.get("pageSize");
        int page;
        int size;
        if (currentPage == null || "".equals(currentPage))
            return ResultFormatUtil.format(ResponseCode.REQUEST_PARAM_ERROR, "page");
        if (pageSize == null || "".equals(pageSize))
            return ResultFormatUtil.format(ResponseCode.REQUEST_PARAM_ERROR, "page");

        // 查询默认推荐资源
        size = Integer.valueOf(pageSize);
        page = Integer.valueOf(currentPage);
        MyPage<UserResource> resourcePage = new MyPage<>();
        List<UserResource> resources;
        // 分页数据异常，默认参数去查询资源
        if (page <= 0)
            page = 1;
        if (size < 0)
            size = 15;

        resources = this.recommendService.getDefaultUserResourceList((page - 1) * size, size);

        // 设置分页数据
        int totalPages = this.recommendService.getUserResourceNumbers(null, null);
        resourcePage.setTotal(totalPages);
        resourcePage.setCurrentPage(page);
        resourcePage.setPageSize(size);
        resourcePage.setPageList(resources);

        return ResultFormatUtil.format(ResponseCode.GET_RESOURCE_SUCCESS, resourcePage);
    }


    /**
     * 通过选择获取资源
     *
     * @return 资源list
     */
    @PostMapping("/select")
    public String getResourceByDiscipline(@RequestBody Map<String, String> params) {
        // 获取科目
        String discipline = params.get("disciplineContent");
        if (discipline == null || "".equals(discipline))
            return ResultFormatUtil.format(ResponseCode.REQUEST_PARAM_LOSE, "discipline");

        // 通过科目查询科目资源
        List<UserResource> resources = this.recommendService.getUserResourceListByDiscipline(discipline);

        return ResultFormatUtil.format(ResponseCode.GET_RESOURCE_SUCCESS, resources);
    }

}
