package com.sharing.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 收藏的内容实体类
 *
 * @author 李福生
 * @date 2022-4-3
 * @time 下午 08:05
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Favourite {
    private int id;
    private int user_id;
    private int resource_id;
    @DateTimeFormat(pattern = "yyyy-MM-dd  HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd  HH:mm:ss", timezone = "GMT+8")
    private Date time;
    private int folder_id;
    private String folder_name;
}
