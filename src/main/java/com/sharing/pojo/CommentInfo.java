package com.sharing.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 评论区内容实体类
 *
 * @author 李福生
 * @date 2022-3-28
 * @time 下午 12:46
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentInfo {
    private int user_id;
    private String headIcon;
    private String name;
    private String to_name;
    @DateTimeFormat(pattern = "yyyy-MM-dd  HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd  HH:mm:ss", timezone = "GMT+8")
    private Date time;
    private String content;
    private int support_number;
}
