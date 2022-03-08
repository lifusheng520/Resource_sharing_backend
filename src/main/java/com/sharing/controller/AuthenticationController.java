package com.sharing.controller;


import com.sharing.Utils.ResultFormatUtil;
import com.sharing.pojo.User;
import com.sharing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author 李福生
 * @date 2022-3-4
 * @time 上午 10:52
 */
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @PostMapping("/test")
    public ResultFormatUtil.ApiResult test() {
        return ResultFormatUtil.format(1234, "test", null);
    }

    @RequestMapping("/register")
    public ResultFormatUtil.ApiResult register(@RequestBody User user) {
        System.out.println("=================");

        System.out.println(user);

        return ResultFormatUtil.format(234, "注册", user);
    }


//    @PostMapping("/login")
//    @ResponseBody
//    public ResultFormatUtil.ApiResult login(@RequestBody Map<String, String> params) {
//        User user = this.userService.getUserByName(params.get("username"));
//        return ResultFormatUtil.format(123, "登录", user);
//    }

}
