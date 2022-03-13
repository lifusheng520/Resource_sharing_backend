package com.sharing.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author 李福生
 * @date 2022-3-11
 * @time 下午 10:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    private int id;
    private String username;
    private int enabled;
    private Date create_time;
    private String name;
    private String sex;
    private String email;
    private String address;
    private String headIcon;
    private List<String> roles;
}
