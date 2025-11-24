package com.sharing.serviceImpl;

import com.sharing.mapper.ResourceRecommendMapper;
import com.sharing.pojo.UserResource;
import com.sharing.service.ResourceRecommendService;
import com.sharing.service.SupportService;
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

    @Autowired
    private SupportService supportService;

    @Override
    public int getUserResourceNumbers(String discipline, String like) {
        return this.resourceRecommendMapper.getResourceNumbers(discipline, like);
    }

    @Override
    public List<UserResource> getUserResourceListByDiscipline(String discipline, int begin, int number) {
        List<UserResource> list = this.resourceRecommendMapper.getUserResourceListByDiscipline(discipline, begin, number);
        for (UserResource item : list) {
            int id = item.getId();
            int i = this.supportService.countResourceSupportNumbers(id);
            item.setSupportNumber(i);
        }
        return list;
    }

    @Override
    public List<UserResource> getDefaultUserResourceList(int begin, int number) {
        List<UserResource> list = this.resourceRecommendMapper.getDefaultResourceList(begin, number);
        for (UserResource item : list) {
            int id = item.getId();
            int i = this.supportService.countResourceSupportNumbers(id);
            item.setSupportNumber(i);
        }
        return list;
    }

    @Override
    public List<UserResource> getUserResourceByLike(String like, int begin, int number) {
        List<UserResource> list = this.resourceRecommendMapper.getResourceByLike(like, begin, number);
        for (UserResource item : list) {
            int id = item.getId();
            int i = this.supportService.countResourceSupportNumbers(id);
            item.setSupportNumber(i);
        }
        return list;
    }

    @Override
    public List<UserResource> getUserResourceByCondition(String discipline, String like, int begin, int number) {
        List<UserResource> list = this.resourceRecommendMapper.getResourceByCondition(discipline, like, begin, number);
        for (UserResource item : list) {
            int id = item.getId();
            int i = this.supportService.countResourceSupportNumbers(id);
            item.setSupportNumber(i);
        }
        return list;
    }
}
