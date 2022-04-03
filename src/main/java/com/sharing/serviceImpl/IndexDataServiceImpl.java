package com.sharing.serviceImpl;

import com.sharing.mapper.IndexDataMapper;
import com.sharing.mapper.UserMapper;
import com.sharing.pojo.*;
import com.sharing.service.IndexDataService;
import com.sharing.service.ResourceDetailService;
import com.sharing.service.SupportService;
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
    private SupportService supportService;

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
            if ("全部".equals(discipline))
                continue;
            // 查询科目资源
            UserAndResource userAndResource = this.indexDataMapper.getUserAndResourceByDiscipline(discipline);
            // 获取资源点赞数量
            int id = userAndResource.getResource().getId();
            int supportNumbers = this.supportService.countResourceSupportNumbers(id);
            userAndResource.getResource().setSupportNumber(supportNumbers);
            // 设置头像URL
            String icon = userAndResource.getUserInfo().getHeadIcon();
            if (icon != null && !"".equals(icon))
                userAndResource.getUserInfo().setHeadIcon(this.iconHostURL + icon);
            // 添加用户信息和资源信息
            userAndResourceList.add(userAndResource);
        }
        return userAndResourceList;
    }

    @Override
    public List<UploadRankInfo> getUploadMostUserList(int number) {
        return this.indexDataMapper.getUploadMostUserInfoList(number);
    }
}
