package com.sharing.config.handler;

import com.sharing.Utils.ResponseCode;
import com.sharing.Utils.ResultFormatUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 退出登录成功处理类
 * @author 李福生
 * @date 2022-3-6
 * @time 下午 01:24
 */

@Component
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        ResultFormatUtil.ApiResult build = ResultFormatUtil.ApiResult.build(ResponseCode.LOGOUT_SUCCESS, null);
        ResultFormatUtil.writeJSON(request, response, build);
    }
}
