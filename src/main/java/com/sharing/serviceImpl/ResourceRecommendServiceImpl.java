package com.sharing.serviceImpl;

import com.sharing.mapper.ResourceRecommendMapper;
import com.sharing.pojo.UserResource;
import com.sharing.service.ResourceRecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 李福生
 * @date 2022-3-23
 * @time 下午 04:52
 */

@Service
public class ResourceRecommendServiceImpl implements ResourceRecommendService {

    @Autowired
    private ResourceRecommendMapper resourceRecommendMapper;

    @Override
    public int getUserResourceNumbers(String discipline, String like) {
        return this.resourceRecommendMapper.getResourceNumbers(discipline, like);
    }

    @Override
    public List<UserResource> getUserResourceListByDiscipline(String discipline, int begin, int number) {
        return this.resourceRecommendMapper.getUserResourceListByDiscipline(discipline, begin, number);
    }

    @Override
    public List<UserResource> getDefaultUserResourceList(int begin, int number) {
        return this.resourceRecommendMapper.getDefaultResourceList(begin, number);
    }

    @Override
    public List<UserResource> getUserResourceByLike(String like, int begin, int number) {
        return this.resourceRecommendMapper.getResourceByLike(like, begin, number);
    }

    @Override
    public List<UserResource> getUserResourceByCondition(String discipline, String like, int begin, int number) {
        return this.resourceRecommendMapper.getResourceByCondition(discipline, like, begin, number);
    }
}
