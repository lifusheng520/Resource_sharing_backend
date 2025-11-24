package com.sharing.config.handler;

import com.sharing.Utils.ResponseCode;
import com.sharing.Utils.ResultFormatUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 未登录处理类
 * @author 李福生
 * @date 2022-3-5
 * @time 下午 06:59
 */

@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ResultFormatUtil.ApiResult result = ResultFormatUtil.ApiResult.build(ResponseCode.USER_NOT_LOGIN, null);
        ResultFormatUtil.writeJSON(request, response, result);
    }
}
