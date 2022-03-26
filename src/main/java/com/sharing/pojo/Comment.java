package com.sharing.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Date time;
}
