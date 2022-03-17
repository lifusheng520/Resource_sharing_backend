package com.sharing.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 李福生
 * @date 2022-3-16
 * @time 下午 09:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResource {
    private int id;
    private int user_id;
    private String origin_name;
    private String type;
    private long size;
    private String disk_name;
    private String discipline;
    private Date upload_time;
    private int favorite_number;
    private int enabled;
    private int isDeleted;
    private String md5;
}
