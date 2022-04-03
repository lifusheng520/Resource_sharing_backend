package com.sharing.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 收藏实体类
 *
 * @author 李福生
 * @date 2022-4-3
 * @time 下午 08:05
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class favourite {
    private int id;
    private int user_id;
    private int resource_id;
    private Date time;
    private String folder;
}
