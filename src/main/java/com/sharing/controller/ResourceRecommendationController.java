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
     * 通过字符串获取合法的页码
     *
     * @param number 页码字符串
     * @param type   需要转换的类型（page、size）
     * @return 如果type为page：字符串正常将该字符串转换为int返回，否则返回 1。如果type为size，返回正常的页面大小，默认返回10
     */
    int getValidTransfer(String number, String type) {
        Integer value;
        if ("page".equals(type)) {
            if (number == null || "".equals(number))
                value =  1;
            value = Integer.valueOf(number);
            if (value < 1)
                value =  1;
        } else {
            if (number == null || "".equals(number))
                value =  10;
            value = Integer.valueOf(number);
            if (value < 1)
                value =  10;
        }
        return value;
    }

    /**
     * 获取默认的推荐资源接口
     *
     * @param params 分页数据
     * @return 返回一个包装资源list的分页对象json
     */
    @PostMapping("/default")
    public String getDefaultResource(@RequestBody Map<String, String> params) {
        // 获取页面参数
        String currentPage = params.get("currentPage");
        String pageSize = params.get("pageSize");
        String totalPages = params.get("totalPages");
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
        Integer total = Integer.valueOf(totalPages);
        int numbers = 0;
        if (total < 0)
            numbers = this.recommendService.getUserResourceNumbers(null, null);
        else
            numbers = total;
        resourcePage.setTotal(numbers);
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
        //  获取请求参数
        String discipline = params.get("disciplineContent");
        String currentPage = params.get("currentPage");
        String pageSize = params.get("pageSize");
        String totalPages = params.get("totalPages");

        if (discipline == null || "".equals(discipline))
            return ResultFormatUtil.format(ResponseCode.REQUEST_PARAM_LOSE, "discipline");

        // 获取页码和页大小
        int page = this.getValidTransfer(currentPage, "page");
        int size = this.getValidTransfer(pageSize, "size");

        // 通过科目查询科目资源
        List<UserResource> resources = this.recommendService.getUserResourceListByDiscipline(discipline, (page - 1) * size, size);

        // 设置分页内容
        Integer total = Integer.valueOf(totalPages);
        int numbers = 0;
        if (total < 0)
            numbers = this.recommendService.getUserResourceNumbers(discipline, null);
        else
            numbers = total;
        MyPage<UserResource> resourcePage = new MyPage<>();
        resourcePage.setCurrentPage(page);
        resourcePage.setPageList(resources);
        resourcePage.setPageSize(size);
        resourcePage.setTotal(numbers);

        return ResultFormatUtil.format(ResponseCode.GET_RESOURCE_SUCCESS, resourcePage);
    }

    /**
     * 单独搜索资源
     *
     * @param params 搜索参数
     * @return 返回搜索的资源分页数据
     */
    @PostMapping("/search")
    public String getResourceBySearch(@RequestBody Map<String, String> params) {
        // 获取请求参数
        String searchContent = params.get("searchContent");
        String currentPage = params.get("currentPage");
        String pageSize = params.get("pageSize");
        String totalPages = params.get("totalPages");

        // 获取页码和页的大小
        int page = this.getValidTransfer(currentPage, "page");
        int size = this.getValidTransfer(pageSize, "size");

        if (searchContent == null)
            return ResultFormatUtil.format(ResponseCode.REQUEST_PARAM_LOSE, "searchContent");

        // 通过搜索查询数据库中的资源
        List<UserResource> resources = this.recommendService.getUserResourceByLike(searchContent, (page - 1) * size, size);

        // 设置分页参数
        Integer total = Integer.valueOf(totalPages);
        int numbers = 0;
        if (total < 0)
            numbers = this.recommendService.getUserResourceNumbers(null, searchContent);
        else
            numbers = total;
        MyPage<UserResource> resourcePage = new MyPage<>();
        resourcePage.setCurrentPage(page);
        resourcePage.setPageSize(size);
        resourcePage.setPageList(resources);
        resourcePage.setTotal(numbers);

        return ResultFormatUtil.format(ResponseCode.GET_RESOURCE_SUCCESS, resourcePage);
    }


    /**
     * 通过条件获取资源接口
     *
     * @param params 条件参数
     * @return 返回资源list分页json
     */
    @PostMapping("/condition")
    public String getResourceByCondition(@RequestBody Map<String, String> params) {
        // 获取参数
        String discipline = params.get("disciplineContent");
        String searchContent = params.get("searchContent");
        String currentPage = params.get("currentPage");
        String pageSize = params.get("pageSize");
        String totalPages = params.get("totalPages");

        // 获取页码和页大小
        int page = this.getValidTransfer(currentPage, "page");
        int size = this.getValidTransfer(pageSize, "size");
        Integer total = Integer.valueOf(totalPages);

        List<UserResource> resources = this.recommendService.getUserResourceByCondition(discipline, searchContent, (page - 1) * size, size);
        int numbers = 0;
        if (total < 0)
            numbers = this.recommendService.getUserResourceNumbers(discipline, searchContent);
        else
            numbers = total;

        // 设置分页参数
        MyPage<UserResource> resourcePage = new MyPage<>();
        resourcePage.setCurrentPage(page);
        resourcePage.setTotal(numbers);
        resourcePage.setPageList(resources);
        resourcePage.setPageSize(size);

        return ResultFormatUtil.format(ResponseCode.GET_RESOURCE_SUCCESS, resourcePage);
    }

}
