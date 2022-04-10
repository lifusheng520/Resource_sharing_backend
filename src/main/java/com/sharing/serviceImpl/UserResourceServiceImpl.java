package com.sharing.serviceImpl;


import com.sharing.mapper.UserResourceMapper;
import com.sharing.pojo.DeletedResource;
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
public class UserResourceServiceImpl implements UserResourceService {

    @Autowired
    private UserResourceMapper resourceMapper;

    @Override
    public List<UserResource> getUserResourceByMd5(String discipline, String md5) {
        return this.resourceMapper.getResourceByMd5(discipline, md5);
    }

    @Override
    public int addUserResource(UserResource userResource) {
        return this.resourceMapper.addUserResource(userResource);
    }

    @Override
    public List<UserResource> getUserResourceByUserId(int user_id) {
        return this.resourceMapper.getResourceListByUserId(user_id);
    }

    @Override
    public List<UserResource> getUserResourceByPage(int user_id, int begin, int size) {
        return this.resourceMapper.getUserResourceByPage(user_id, begin, size);
    }

    @Override
    public int getUserResourceNumber(int user_id) {
        return this.resourceMapper.getResourceNumber(user_id);
    }

    @Override
    public int getUserResourceNumbersByCondition(int user_id, String like) {
        return this.resourceMapper.getResourceNumbersByCondition(user_id, like);
    }

    @Override
    public List<UserResource> getUserResourceBySearch(int user_id, String key, int begin, int size) {
        return this.resourceMapper.getResourceBySearch(user_id, key, begin, size);
    }

    @Override
    public int deleteUserResourceByList(List<UserResource> resourceList) {
        return this.resourceMapper.setDeleteResourceByList(resourceList);
    }

    @Override
    public int addDeletedResourceRecord(List<DeletedResource> deletedResourceList) {
        return this.resourceMapper.insertDeletedResourceRecord(deletedResourceList);
    }

    @Override
    public int updateUserResourceInfo(UserResource userResource) {
        return resourceMapper.updateResourceInfo(userResource);
    }

    @Override
    public int addUserResourceDownloads(int id) {
        return this.resourceMapper.addResourceDownloads(id);
    }

    @Override
    public String getUserResourceOriginFileName(int id) {
        return this.resourceMapper.getResourceOriginFileName(id);
    }

    @Override
    public List<DeletedResource> getDeletedResourceRecordByUserId(int user_id, int begin, int number) {
        return this.resourceMapper.queryDeletedResourceRecordByUserId(user_id, begin, number);
    }

    @Override
    public int countDeletedRecord(int user_id) {
        return this.resourceMapper.countDeletedResourceRecordNumbers(user_id);
    }

    @Override
    public int realDeleteUserResourceByList(List<DeletedResource> deletedResourceList) {
        return this.resourceMapper.realDeleteResourceByList(deletedResourceList);
    }

    @Override
    public int deleteResourceDeletedRecordByList(List<DeletedResource> deletedResourceList) {
        return this.resourceMapper.deleteResourceDeletedRecordByList(deletedResourceList);
    }

    @Override
    public int reinstateUserResourceByList(List<DeletedResource> reinstateResourceList) {
        return this.resourceMapper.reinstateResourceByList(reinstateResourceList);
    }

}
