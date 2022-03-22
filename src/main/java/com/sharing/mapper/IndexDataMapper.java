package com.sharing.mapper;

import com.sharing.pojo.UploadRankInfo;
import com.sharing.pojo.User;
import com.sharing.pojo.UserInfo;
import com.sharing.pojo.UserResource;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

/**
 * @author 李福生
 * @date 2022-3-21
 * @time 上午 11:02
 */

@Mapper
@Repository
public interface IndexDataMapper {

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
     * 获取系统中的每个资源的下载次数list集合
     *
     * @return 返回资源下载数的list集合
     */
    int getResourceDownloadsList();

    /**
     * 根据学科类别，查询学科资料中下载次数最多的资源
     *
     * @param discipline 学科类别
     * @return 返回资源信息
     */
    UserResource getResourceByDiscipline(String discipline);

    /**
     * 查询资源上传最多的用户列表
     *
     * @param number 需要获取的用户个数
     * @return 返回上传次数最多的用户信息
     */
    List<UploadRankInfo> getUploadMostUserInfoList(int number);


}
