package com.sharing.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 用户关注实体类
 *
 * @author 李福生
 * @date 2022-3-30
 * @time 上午 11:39
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Focus {
    private int id;
    private int user_id;
    private int focus_uid;
    @DateTimeFormat(pattern = "yyyy-MM-dd  HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd  HH:mm:ss", timezone = "GMT+8")
    private Date time;
}
