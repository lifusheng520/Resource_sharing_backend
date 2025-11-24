package com.sharing.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * 用户的登录身份认证类
 *
 * @author 李福生
 * @date 2022-3-5
 * @time 下午 03:20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginAuthentication implements UserDetails {

    /**
     * 用户信息
     */
    private User user;

    /**
     * 用户token
     */
    private String token;

    /**
     * 用户收到的验证码
     */
    private int verificationCode;

    /**
     * 验证码发送时间
     */
    private long sendTime;

    /**
     * 用户信息的id
     */
    private int userInfo_id;

    /**
     * 用户权限
     */
    private Collection<? extends GrantedAuthority> authorities;

    public LoginAuthentication(User user, Collection<? extends GrantedAuthority> collection) {
        this.user = user;
        this.authorities = collection;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 判断账号是否可用
     *
     * @return 如果账号被冻结禁用了返回false，否则可用状态返回true
     */
    @Override
    public boolean isEnabled() {
        return this.user.getEnabled() == 1;
    }
}
