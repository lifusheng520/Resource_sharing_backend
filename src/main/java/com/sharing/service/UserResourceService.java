package com.sharing.service;

import com.sharing.pojo.UserResource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 李福生
 * @date 2022-3-16
 * @time 下午 09:38
 */
public interface UserResourceService {

    /**
     * 通过md5获取用户资源文件信息
     *
     * @param md5 md5字符串
     * @return 返回资源文件对象md5相同的文件的集合
     */
    List<UserResource> getUserResourceByMd5(String md5);

    /**
     * 向数据库中添加一条用户上传资源的记录
     *
     * @param userResource 用户上传的资源信息
     * @return 添加的状态
     */
    int addUserResource(UserResource userResource);

}
