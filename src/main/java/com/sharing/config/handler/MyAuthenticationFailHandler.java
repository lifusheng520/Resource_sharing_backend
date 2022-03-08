package com.sharing.config.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sharing.Utils.ResultFormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录认证失败处理类
 * @author 李福生
 * @date 2022-3-5
 * @time 下午 03:40
 */

@Component
public class MyAuthenticationFailHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // 登录失败进行数据处理

        ResultFormatUtil.ApiResult build = ResultFormatUtil.ApiResult.build(505, "登录认证失败~ ", null);

        ResultFormatUtil.writeJSON(request, response, build);

    }
}
