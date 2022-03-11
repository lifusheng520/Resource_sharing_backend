package com.sharing.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


/**
 * @author 李福生
 * @date 2022-2-25
 * @time 下午 12:45
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    private String username;
    private String password;
    private int enabled;
    private Date create_time;
}
