package com.sharing.controller;

import com.sharing.Utils.ResponseCode;
import com.sharing.Utils.ResultFormatUtil;
import com.sharing.pojo.Focus;
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
        String userId = params.get("userId").replaceAll(" ", "");
        String focusUserId = params.get("focusUserId").replaceAll(" ", "");
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


    @PostMapping("/cancel")
    public String cancelFocus(@RequestBody Map<String, String> params) {
        // 获取请求参数
        String userId = params.get("userId").replaceAll(" ", "");
        String focusUserId = params.get("focusUserId").replaceAll(" ", "");
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


}
