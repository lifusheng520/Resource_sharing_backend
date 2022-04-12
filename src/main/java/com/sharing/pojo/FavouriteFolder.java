package com.sharing.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


/**
 * 收藏的文件夹
 *
 * @author 李福生
 * @date 2022-4-4
 * @time 上午 10:32
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavouriteFolder{
    private int id;
    private int user_id;
    private String folder_name;
    private Date time;

    private int resourceNumber;
}
