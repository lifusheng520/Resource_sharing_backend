package com.sharing.config.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 退出登录处理类
 * @author 李福生
 * @date 2022-3-7
 * @time 上午 10:18
 */

@Component
public class MyLogoutHandler implements LogoutHandler {
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String headerToken = request.getHeader("token");
        System.out.println("logout header Token = " + headerToken);
        System.out.println("logout request getMethod = " + request.getMethod());
        //
        if (headerToken != null && !"".equals(headerToken)) {
            //postMan测试时，自动假如的前缀，要去掉。
//            String token = headerToken.replace("Bearer", "").trim();
//            System.out.println("authentication = " + authentication);

            // 注销时，将登录时放入上下文context中的token清除
            SecurityContextHolder.clearContext();
        }
        // 注销时，将登录时放入上下文context中的token清除
        SecurityContextHolder.clearContext();

    }
}
