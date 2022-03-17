package com.sharing.serviceImpl;


import com.sharing.mapper.UserResourceMapper;
import com.sharing.pojo.UserResource;
import com.sharing.service.UserResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 李福生
 * @date 2022-3-16
 * @time 下午 09:47
 */
@Service
public class UserResourceImpl implements UserResourceService {

    @Autowired
    private UserResourceMapper resourceMapper;

    @Override
    public List<UserResource> getUserResourceByMd5(String md5) {
        return this.resourceMapper.getResourceByMd5(md5);
    }

    @Override
    public int addUserResource(UserResource userResource) {
        return this.resourceMapper.addUserResource(userResource);
    }
}
