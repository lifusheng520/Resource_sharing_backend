package com.sharing.controller;

import com.sharing.Utils.ResponseCode;
import com.sharing.Utils.ResultFormatUtil;
import com.sharing.service.ResourceDetailService;
import com.sharing.service.SupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 点赞类业务的处理接口
 *
 * @author 李福生
 * @date 2022-4-3
 * @time 下午 12:02
 */

@RestController
@RequestMapping("/support")
public class SupportHandlerController {

    @Autowired
    private SupportService supportService;

    /**
     * 资源点赞功能接口
     *
     * @param user_id     点赞的用户id
     * @param resource_id 被赞的资源id
     * @return 返回处理结果json
     */
    @GetMapping("/resource/{user_id}/{resource_id}")
    public String supportHandler(@PathVariable Integer user_id, @PathVariable Integer resource_id) {
        // 根据用户参数查询是否已经存在
        boolean isExist = this.supportService.supportIsExist(user_id, resource_id);

        ResponseCode responseCode;

        // 如果存在，删除点赞记录，否则添加一条记录
        if (isExist) {
            this.supportService.deleteResourceSupportRecord(user_id, resource_id);
            responseCode = ResponseCode.RESOURCE_DELETE_SUPPORT_SUCCESS;
        } else {
            this.supportService.addResourceSupportRecord(user_id, resource_id);
            responseCode = ResponseCode.RESOURCE_ADD_SUPPORT_SUCCESS;
        }

        return ResultFormatUtil.format(responseCode, null);
    }


}
