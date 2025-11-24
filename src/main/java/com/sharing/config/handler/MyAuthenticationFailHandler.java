package com.sharing.config.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sharing.Utils.ResultFormatUtil;
import com.sharing.pojo.LoginAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录认证失败处理类
 *
 * @author 李福生
 * @date 2022-3-5
 * @time 下午 03:40
 */

@Component
public class MyAuthenticationFailHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        // 登录失败进行数据处理
        String message;
        try {
            throw exception;
        } catch (DisabledException e) {
            message = "账号已经被禁用，请联系管理员~~~";
        } catch (BadCredentialsException e) {
            message = "用户名或者密码错误~~~ ";
        } catch (Exception e) {
            message = "登录认证失败~~~";
        }

        ResultFormatUtil.ApiResult build = ResultFormatUtil.ApiResult.build(505, message, null);

        ResultFormatUtil.writeJSON(request, response, build);

    }
}
