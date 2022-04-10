package com.sharing.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 李福生
 * @date 2022-4-9
 * @time 下午 04:25
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeletedResource {
    private int id;
    private int user_id;
    private int resource_id;
    private Date time;
    private String origin_name;
    private long size;
    private String discipline;
}
