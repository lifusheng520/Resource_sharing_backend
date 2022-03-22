package com.sharing.service;

import com.sharing.pojo.UploadRankInfo;
import com.sharing.pojo.UserAndResource;
import com.sharing.pojo.UserInfo;
import com.sharing.pojo.UserResource;

import java.math.BigInteger;
import java.util.List;

/**
 * 首页数据服务接口
 *
 * @author 李福生
 * @date 2022-3-21
 * @time 上午 10:57
 */
public interface IndexDataService {

    /**
     * 获取系统使用用户数量
     *
     * @return 系统用户量
     */
    int getSystemUserNumbers();

    /**
     * 获取系统资源的总量
     *
     * @return 返回系统资源数
     */
    int getSystemResourceNumbers();


    /**
     * 获取系统中的资源下载次数
     *
     * @return 返回资源下载次数总和
     */
    int getResourceDownloads();

    /**
     * 获取每个学科中的资源
     *
     * @return 返回一个包含用户资源的list集合
     */
    List<UserAndResource> getEachDisciplineResourceList();

    /**
     * 获取资源上传最多的用户列表
     *
     * @param number 需要获取的用户个数
     * @return 返回上传次数最多的用户信息
     */
    List<UploadRankInfo> getUploadMostUserList(int number);


}
