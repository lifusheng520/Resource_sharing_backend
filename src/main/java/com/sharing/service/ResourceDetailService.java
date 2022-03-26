package com.sharing.service;

import com.sharing.pojo.UserAndResource;

/**
 * 资源详情业务服务
 *
 * @author 李福生
 * @date 2022-3-26
 * @time 上午 09:56
 */
public interface ResourceDetailService {

    /**
     * 获取用户资源详细信息
     *
     * @param id 资源id
     * @return 返回用户和资源信息
     */
    UserAndResource getUserResourceDetail(int id);

}
