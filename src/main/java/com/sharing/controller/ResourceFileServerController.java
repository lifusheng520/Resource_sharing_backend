package com.sharing.controller;

import com.sharing.Utils.ResponseCode;
import com.sharing.Utils.ResultFormatUtil;
import com.sharing.pojo.IndexData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/discipline")
    public String getAllResourceDiscipline(){
        return ResultFormatUtil.format(ResponseCode.GET_ALL_RESOURCE_DISCIPLINE_SUCCESS, IndexData.discipline);
    }



}
