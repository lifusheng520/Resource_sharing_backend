package com.sharing.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author 李福生
 * @date 2022-4-8
 * @time 下午 01:40
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DownloadRecord {
    private int id;
    private int user_id;
    private int resource_id ;
    @DateTimeFormat(pattern = "yyyy-MM-dd  HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd  HH:mm:ss", timezone = "GMT+8")
    private Date time;
}
