package com.sharing.controller;

import com.sharing.Utils.ResultFormatUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 李福生
 * @date 2022-3-5
 * @time 下午 04:07
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @PostMapping("/")
    public String test() {
        return ResultFormatUtil.format(250, "没权限啊~", null);
    }
}
