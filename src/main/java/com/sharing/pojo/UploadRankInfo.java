package com.sharing.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 资源贡献排行榜的信息
 *
 * @author 李福生
 * @date 2022-3-22
 * @time 下午 02:12
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadRankInfo implements Serializable {
    private int user_id;
    private String headIcon;
    private String name;
    private String username;
    private int resourceNumbers;
}
