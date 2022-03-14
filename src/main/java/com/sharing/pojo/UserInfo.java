package com.sharing.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd  HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd  HH:mm:ss", timezone = "GMT+8")
    private Date create_time;
    private String name;
    private String sex;
    private String email;
    private String address;
    private String headIcon;
    private List<String> roles;
    private String verifyCode;
    private Date verifyTime;
}
