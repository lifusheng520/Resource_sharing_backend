package com.sharing.serviceImpl;

import com.sharing.mapper.IndexDataMapper;
import com.sharing.mapper.UserMapper;
import com.sharing.pojo.IndexData;
import com.sharing.pojo.UserAndResource;
import com.sharing.pojo.UserInfo;
import com.sharing.pojo.UserResource;
import com.sharing.service.IndexDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统数据服务实现类
 *
 * @author 李福生
 * @date 2022-3-21
 * @time 上午 11:02
 */

@Service
public class IndexDataServiceImpl implements IndexDataService {

    @Autowired
    private IndexDataMapper indexDataMapper;

    @Autowired
    private UserMapper userMapper;

    @Value("${files.icon.host.url}")
    private String iconHostURL;

    @Override
    public int getSystemUserNumbers() {
        return this.indexDataMapper.getSystemUserNumbers();
    }

    @Override
    public int getSystemResourceNumbers() {
        return this.indexDataMapper.getSystemResourceNumbers();
    }

    @Override
    public int getResourceDownloads() {
        return this.indexDataMapper.getResourceDownloadsList();
    }

    @Override
    public List<UserAndResource> getEachDisciplineResourceList() {
        List<UserAndResource> userAndResourceList = new ArrayList<>();
        // 遍历所有的学科类别，查询学科top1资源
        for (String discipline : IndexData.discipline) {
            // 创建一个用户资源信息完整实体类
            UserAndResource userAndResource = new UserAndResource();
            // 查询科目资源
            UserResource resource = this.indexDataMapper.getResourceByDiscipline(discipline);
            // 根据资源的用户id获取用户信息
            UserInfo userInfo = this.userMapper.getUserInfoById(resource.getUser_id());
            // 设置头像URL
            String url = this.iconHostURL + userInfo.getHeadIcon();
            userInfo.setHeadIcon(url);
            // 添加用户信息和资源信息
            userAndResource.setUserInfo(userInfo);
            userAndResource.setResource(resource);
            userAndResourceList.add(userAndResource);
        }
        return userAndResourceList;
    }
}
