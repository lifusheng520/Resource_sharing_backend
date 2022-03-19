package com.sharing.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd  HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd  HH:mm:ss", timezone = "GMT+8")
    private Date upload_time;
    private int downloads;
    private int favorite_number;
    private int enabled;
    private int isDeleted;
    private String md5;
    private String description;
}
