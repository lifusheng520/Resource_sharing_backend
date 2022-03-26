package com.sharing.serviceImpl;

import com.sharing.mapper.ResourceDetailMapper;
import com.sharing.pojo.UserAndResource;
import com.sharing.pojo.UserInfo;
import com.sharing.pojo.UserResource;
import com.sharing.service.ResourceDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户资源详细信息实现类
 *
 * @author 李福生
 * @date 2022-3-26
 * @time 下午 02:22
 */

@Service
public class ResourceDetailServiceImpl implements ResourceDetailService {

    @Autowired
    private ResourceDetailMapper resourceDetailMapper;

    @Override
    public UserAndResource getUserResourceDetail(int id) {
        UserAndResource userAndResource = new UserAndResource();
        UserResource resource = this.resourceDetailMapper.getResourceById(id);
        userAndResource.setResource(resource);
        UserInfo userInfo = this.resourceDetailMapper.getUserInfoById(resource.getUser_id());
        userAndResource.setUserInfo(userInfo);
        return userAndResource;
    }
}
