package com.sharing.controller;

import com.sharing.Utils.ResponseCode;
import com.sharing.Utils.ResultFormatUtil;
import com.sharing.pojo.Focus;
import com.sharing.pojo.MyPage;
import com.sharing.service.FocusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用户关注业务接口
 *
 * @author 李福生
 * @date 2022-3-30
 * @time 上午 11:25
 */

@RestController
@RequestMapping("/focus")
public class FocusController {

    @Autowired
    private FocusService focusService;

    /**
     * 判断字符串是否为空
     *
     * @param text 判断的字符串
     * @return 如果不为空返回true，否则返回false
     */
    public boolean isNotNull(String text) {
        return text != null && !"".equals(text);
    }

    /**
     * 添加用户关注
     *
     * @param params 用户关注的参数
     * @return 返回添加结果Focus类的json
     */
    @PostMapping("/add")
    public String addFocus(@RequestBody Map<String, String> params) {
        // 获取请求参数
        String userId = params.get("userId");
        String focusUserId = params.get("focusUserId");
        if (!this.isNotNull(userId) || !this.isNotNull(focusUserId))
            return ResultFormatUtil.format(ResponseCode.REQUEST_PARAM_ERROR, null);

        // 添加关注参数
        Focus focus = new Focus();
        focus.setUser_id(Integer.valueOf(userId));
        focus.setFocus_uid(Integer.valueOf(focusUserId));
        focus.setTime(new Date());
        // 持久化关注数据
        int i = this.focusService.addFocus(focus);

        ResponseCode responseCode;

        if (i > 0)
            responseCode = ResponseCode.FOCUS_ADD_SUCCESS;
        else
            responseCode = ResponseCode.FOCUS_ADD_FAIL;

        return ResultFormatUtil.format(responseCode, focus);
    }

    /**
     * 通过用户的id获取用户的关注内容list
     *
     * @param userId 用户id
     * @return 返回包含用户关注内容的list集合
     */
    @GetMapping("/getList/{userId}")
    public String getFocusList(@PathVariable Integer userId) {
        if (userId == null || userId == 0)
            return ResultFormatUtil.format(ResponseCode.REQUEST_PARAM_ERROR, null);

        // 获取用户关注内容list
        List<Focus> focusList = this.focusService.getUserFocusListById(userId);

        ResponseCode responseCode = ResponseCode.GET_FOCUS_LIST_SUCCESS;
        return ResultFormatUtil.format(responseCode, focusList);
    }


    /**
     * 取消关注功能接口
     *
     * @param params 请求参数
     * @return 返回删除用户的关注id的结果json
     */
    @PostMapping("/cancel")
    public String cancelFocus(@RequestBody Map<String, String> params) {
        // 获取请求参数
        String userId = params.get("userId");
        String focusUserId = params.get("focusUserId");
        if (!this.isNotNull(userId) || !this.isNotNull(focusUserId))
            return ResultFormatUtil.format(ResponseCode.REQUEST_PARAM_ERROR, null);

        Integer user_id = Integer.valueOf(userId);
        Integer focus_uid = Integer.valueOf(focusUserId);

        // 根据用户的id和关注的id，删除用户关注记录
        int i = this.focusService.cancelFocus(user_id, focus_uid);

        ResponseCode responseCode;
        if (i > 0)
            responseCode = ResponseCode.CANCEL_FOCUS_SUCCESS;
        else
            responseCode = ResponseCode.CANCEL_FOCUS_FAIL;

        return ResultFormatUtil.format(responseCode, focus_uid);
    }

    /**
     * 分页获取关注信息
     *
     * @param params 分页数据
     * @return 返回对应页的关注list
     */
    @PostMapping("/getInfo")
    public String getFocusInfoByPage(@RequestBody Map<String, String> params) {
        // 获取请求参数
        String userId = params.get("user_id");
        String currentPage = params.get("currentPage");
        String totalPage = params.get("total");
        String pageSize = params.get("pageSize");
        if (!this.isNotNull(userId))
            return ResultFormatUtil.format(ResponseCode.REQUEST_PARAM_ERROR, null);

        // 获取分页参数
        int page = MyPage.getValidTransfer(currentPage, "page");
        int size = MyPage.getValidTransfer(pageSize, "size");

        // 分页查询用户关注内容
        int user_id = Integer.parseInt(userId);
        List<Focus> focusList = this.focusService.getUserFocusPageListByUserId(user_id, (page - 1) * size, size);

        // 设置分页参数
        MyPage<Focus> myPage = new MyPage<>();
        myPage.setCurrentPage(page);
        myPage.setPageSize(size);
        myPage.setPageList(focusList);

        int total = Integer.valueOf(totalPage);
        if (total < 0)
            total = this.focusService.countFocusNumberByUserId(user_id);

        myPage.setTotal(total);

        ResponseCode responseCode = ResponseCode.GET_FOCUS_INFO_SUCCESS;
        return ResultFormatUtil.format(responseCode, myPage);
    }

    @GetMapping("/getAllInfo/{user_id}")
    public String getAllFocusInfo(@PathVariable Integer user_id) {
        if (user_id == null || user_id == 0)
            return ResultFormatUtil.format(ResponseCode.REQUEST_PARAM_ERROR, null);

        // 查询全部数据
        List<Focus> focusList = this.focusService.getUserFocusPageListByUserId(user_id, -1, -1);

        ResponseCode responseCode = ResponseCode.GET_FOCUS_INFO_SUCCESS;
        return ResultFormatUtil.format(responseCode, focusList);
    }


}
