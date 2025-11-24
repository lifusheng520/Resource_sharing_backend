package com.sharing.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 李福生
 * @date 2022-4-6
 * @time 下午 05:56
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFavouriteAndResourceInfo {

    private UserInfo userInfo;
    private UserResource resource;
    private Favourite favourite;

}
