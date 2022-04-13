package com.sharing.serviceImpl;

import com.sharing.mapper.PlatformResourceMapper;
import com.sharing.pojo.CompleteResourceInfo;
import com.sharing.pojo.UserResource;
import com.sharing.service.PlatformResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 李福生
 * @date 2022-4-13
 * @time 下午 01:47
 */

@Service
public class PlatformResourceServiceImpl implements PlatformResourceService {

    @Autowired
    private PlatformResourceMapper platformResourceMapper;

    @Override
    public List<CompleteResourceInfo> getAllResourceByPage(int begin, int number, String search) {
        return this.platformResourceMapper.queryAllResourceByPage(begin, number, search);
    }

    @Override
    public int countAllResourceBySearch(String search) {
        return this.platformResourceMapper.countAllResourceBySearch(search);
    }

    @Override
    public int deleteCommentByResourceId(List<Integer> resourceIds) {
        return this.platformResourceMapper.deleteCommentByResourceId(resourceIds);
    }

    @Override
    public int deleteFavouriteByResourceId(List<Integer> resourceIds) {
        return this.platformResourceMapper.deleteFavouriteByResourceId(resourceIds);
    }

    @Override
    public int deleteSupportRecordByResourceId(List<Integer> resourceIds) {
        return this.platformResourceMapper.deleteSupportRecordByResourceId(resourceIds);
    }

    @Override
    public int realDeleteResourceByResourceId(List<Integer> resourceIds) {
        return this.platformResourceMapper.realDeleteResourceByResourceId(resourceIds);
    }

    @Override
    public List<UserResource> getCheckResourceInfoListByPage(int begin, int number) {
        return this.platformResourceMapper.queryCheckResourceInfoListByPage(begin, number);
    }

    @Override
    public int countCheckResourceNumbers() {
        return this.platformResourceMapper.countCheckResourceNumbers();
    }
}
