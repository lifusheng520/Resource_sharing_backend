package com.sharing.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 李福生
 * @date 2022-3-23
 * @time 下午 04:46
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompleteResourceInfo implements Serializable {
    /**
     * 用户账号信息
     */
    private User user;

    /**
     * 用户个人信息
     */
    private UserInfo userInfo;

    /**
     * 用户资源信息
     */
    private UserResource resource;
}
