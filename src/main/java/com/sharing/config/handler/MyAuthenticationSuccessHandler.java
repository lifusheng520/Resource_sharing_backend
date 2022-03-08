package com.sharing.config.handler;

import com.sharing.Utils.ResponseCode;
import com.sharing.Utils.ResultFormatUtil;
import com.sharing.pojo.JsonUser;
import com.sharing.pojo.LoginAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录认证成功处理类
 * @author 李福生
 * @date 2022-3-4
 * @time 下午 01:27
 */

@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    /**
     * 登录成功返回json数据
     * @param request
     * @param response
     * @param authentication
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        LoginAuthentication loginInfo = (LoginAuthentication) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // 传输用户数据的json实体类
        JsonUser user = new JsonUser();
        user.setId(loginInfo.getUser().getId());
        user.setUsername(loginInfo.getUsername());
        user.setIsEnable(loginInfo.getUser().getEnabled());
        user.setToken(loginInfo.getToken());
        user.setRoles(loginInfo.getAuthorities());

        // 将用户token写入响应头中
//        response.setHeader("token", loginInfo.getToken());
        response.setHeader("token", "hahahahaha");

        ResultFormatUtil.ApiResult data = ResultFormatUtil.ApiResult.build(ResponseCode.LOGIN_SUCCESS, user);
        ResultFormatUtil.writeJSON(request, response, data);

    }
}
