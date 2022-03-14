package com.sharing.serviceImpl;

import com.sharing.pojo.LoginAuthentication;
import com.sharing.pojo.User;
import com.sharing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;

/**
 * @author 李福生
 * @date 2022-3-5
 * @time 下午 12:43
 */
@Component
public class MyUserDetailsServiceImpl implements UserDetailsService {

    /**
     * 获取数据库中的用户信息
     */
    @Autowired
    private UserService userService;

    @Value("${files.icon.host.url}")
    private String userHeadIconFilePath;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("============MyUserDetailsServiceImpl");

        System.out.println("username==>" + username);

        if (username == null || "".equals(username)) {
            throw new UsernameNotFoundException("用户名不能为空~");
        }

        // 根据用户名查询用户
        User user = this.userService.getUserByName(username);
        if (user == null) {
            throw new RuntimeException("用户不存在~");
        }
        System.out.println("userid" + user.getId());

        // 读取用户权限
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        List<String> roles = this.userService.getRoles(user.getId());

        System.out.println("roles======" + roles);

        for (String role : roles) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        }

        // 查询用户头像信息
        String userIconInfo = this.userService.getUserIconInfo(user.getId());
        user.setHeadIcon(this.userHeadIconFilePath + userIconInfo);

        LoginAuthentication authentication = new LoginAuthentication(user, grantedAuthorities);
        System.out.println(authentication);

        System.out.println("============MyUserDetailsServiceImpl");

        // 返回spring security 的user对象
        return authentication;
    }
}
