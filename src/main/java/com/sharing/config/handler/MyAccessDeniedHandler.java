package com.sharing.config.handler;

import com.sharing.Utils.ResponseCode;
import com.sharing.Utils.ResultFormatUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 没有权限或权限不足处理类
 * @author 李福生
 * @date 2022-3-5
 * @time 下午 07:06
 */

@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ResultFormatUtil.ApiResult result = ResultFormatUtil.ApiResult.build(ResponseCode.USER_NOT_PERMISSION, null);
        ResultFormatUtil.writeJSON(request, response, result);
    }
}
