package com.sharing.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 资源评论实体类
 *
 * @author 李福生
 * @date 2022-3-26
 * @time 上午 09:30
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private int id;
    private int resource_id;
    private int user_id;
    private int to_uid;
    private String content;
    private int support_number;
    @DateTimeFormat(pattern = "yyyy-MM-dd  HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd  HH:mm:ss", timezone = "GMT+8")
    private Date time;
    private int isIllegal;

    private String resource_name;
    private String to_name;
}
