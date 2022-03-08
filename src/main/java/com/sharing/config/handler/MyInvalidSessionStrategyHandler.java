package com.sharing.config.handler;

import com.sharing.Utils.ResultFormatUtil;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * session失效过期处理类
 * @author 李福生
 * @date 2022-3-6
 * @time 下午 01:03
 */

@Component
public class MyInvalidSessionStrategyHandler implements InvalidSessionStrategy {
    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ResultFormatUtil.ApiResult result = ResultFormatUtil.ApiResult.build(2131, "登录过期，请重新登录~", null);

        ResultFormatUtil.writeJSON(request, response, result);
    }
}
