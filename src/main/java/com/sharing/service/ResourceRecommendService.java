package com.sharing.service;

import com.sharing.pojo.UserResource;

import java.util.List;

/**
 * 资源推荐页业务service
 *
 * @author 李福生
 * @date 2022-3-23
 * @time 下午 04:42
 */
public interface ResourceRecommendService {

    /**
     * 获取资源的总数
     *
     * @param discipline 资源的科目类别
     * @param like       检索的字符串
     * @return 返回满足条件的资源总数
     */
    int getUserResourceNumbers(String discipline, String like);

    /**
     * 通过科目类别获取资源list
     *
     * @param discipline 资源类别
     * @param begin      符合条件的取记录的开始位置
     * @param number     要取的资源的个数
     * @return 返回资源查询结果list
     */
    List<UserResource> getUserResourceListByDiscipline(String discipline, int begin, int number);


    /**
     * 通过默认的推荐资源，从第begin个记录开始，取number个
     *
     * @param begin  开始位置
     * @param number 要取的个数
     * @return 返回资源查询结果list
     */
    List<UserResource> getDefaultUserResourceList(int begin, int number);


    /**
     * 通过like关键字查询用户资源
     *
     * @param like   查询关键字
     * @param begin  从begin开始取值
     * @param number 要取的个数
     * @return 返回符合该关键字的list
     */
    List<UserResource> getUserResourceByLike(String like, int begin, int number);

    /**
     * 通过条件过去用户资源
     *
     * @param discipline 资源所属科目
     * @param like       查询关键字
     * @param begin      从begin开始取值
     * @param number     要取值的个数
     * @return 返回范围在 [begin, begin + number] 的资源list
     */
    List<UserResource> getUserResourceByCondition(String discipline, String like, int begin, int number);

}
