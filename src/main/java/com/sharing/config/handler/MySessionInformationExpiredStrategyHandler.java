package com.sharing.config.handler;

import com.sharing.Utils.ResultFormatUtil;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * 被挤下线处理类
 * @author 李福生
 * @date 2022-3-6
 * @time 下午 01:11
 */

@Component
public class MySessionInformationExpiredStrategyHandler implements SessionInformationExpiredStrategy {
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        ResultFormatUtil.ApiResult result = ResultFormatUtil.ApiResult.build(233, "当前账号已经在其他地方登录~", null);

        ResultFormatUtil.writeJSON(event.getRequest(), event.getResponse(), result);
    }
}
