package com.sharing.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 传输用户数据的json实体类
 * @author 李福生
 * @date 2022-3-7
 * @time 下午 03:02
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonUser {
    private int id;
    private String username;
    private int isEnable;
    private String token;
    private Collection<? extends GrantedAuthority> roles;
    private String headIcon;
}
