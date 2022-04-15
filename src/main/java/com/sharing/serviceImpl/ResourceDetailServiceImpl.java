package com.sharing.serviceImpl;

import com.sharing.mapper.ResourceDetailMapper;
import com.sharing.pojo.UserAndResource;
import com.sharing.pojo.UserInfo;
import com.sharing.pojo.UserResource;
import com.sharing.service.CommentService;
import com.sharing.service.ResourceDetailService;
import com.sharing.service.SupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户资源详细信息实现类
 *
 * @author 李福生
 * @date 2022-3-26
 * @time 下午 02:22
 */

@Service
public class ResourceDetailServiceImpl implements ResourceDetailService {

    @Value("${files.icon.host.url}")
    private String iconHostURL;

    @Autowired
    private ResourceDetailMapper resourceDetailMapper;

    @Autowired
    private SupportService supportService;

    @Override
    public UserAndResource getUserResourceDetail(int resource_id) {
        return this.resourceDetailMapper.getUserAndResourceByResourceId(resource_id);
    }

    @Override
    public List<UserAndResource> getFocusUserResourceByUserId(int user_id, int begin, int number) {
        List<UserAndResource> userAndResources = this.resourceDetailMapper.getFocusResourceByUserId(user_id, begin, number);
        for (UserAndResource item : userAndResources) {
            // 设置头像
            String headIcon = item.getUserInfo().getHeadIcon();
            if (headIcon != null && !"".equals(headIcon))
                item.getUserInfo().setHeadIcon(this.iconHostURL + headIcon);

            // 查询点赞数量
            int resourceId = item.getResource().getId();
            int i = this.supportService.countResourceSupportNumbers(resourceId);
            item.getResource().setSupportNumber(i);
        }
        return userAndResources;
    }

    @Override
    public int countUserResourceNumbers(int user_id) {
        return this.resourceDetailMapper.countResourceNumberByUserId(user_id);
    }


}
