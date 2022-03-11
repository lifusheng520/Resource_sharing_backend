package com.sharing.controller;


import com.sharing.Utils.ResponseCode;
import com.sharing.Utils.ResultFormatUtil;
import com.sharing.pojo.User;
import com.sharing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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

    @PostMapping("/register")
    public String register(@RequestBody Map<String, String> params) throws IOException {
        // 获取注册参数
        String username = params.get("username");
        String password = params.get("password");

        // 查询账号是否已经被注册
        User userByName = this.userService.getUserByName(username);
        if (userByName != null) {   // 重复注册
            return ResultFormatUtil.format(ResponseCode.REGISTER_REPETITION, null);
        }

        // 加密用户密码
        String encode = new BCryptPasswordEncoder().encode(password);

        User user = new User();
        user.setUsername(username);
        user.setPassword(encode);
        user.setEnabled(1); // 设置为可用状态
//        user.setCreate_time();

        // 持久化账号到数据库
        int i = this.userService.register(user);
        if (i > 0) {
            // 注册成功
            return ResultFormatUtil.format(ResponseCode.REGISTER_SUCCESS, null);
        } else {
            return ResultFormatUtil.format(ResponseCode.REGISTER_FAIL, user);
        }
    }

}
