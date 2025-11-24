package com.sharing.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户资源信息的完整实体类
 *
 * @author 李福生
 * @date 2022-3-21
 * @time 下午 05:49
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAndResource implements Serializable {
    private UserInfo userInfo;
    private UserResource resource;
}
