package com.sharing.serviceImpl;

import com.sharing.mapper.ResourceDetailMapper;
import com.sharing.pojo.UserAndResource;
import com.sharing.pojo.UserInfo;
import com.sharing.pojo.UserResource;
import com.sharing.service.CommentService;
import com.sharing.service.ResourceDetailService;
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
    private CommentService commentService;

    @Override
    public UserAndResource getUserResourceDetail(int id) {
        UserAndResource userAndResource = new UserAndResource();
        UserResource resource = this.resourceDetailMapper.getResourceById(id);
        userAndResource.setResource(resource);
        UserInfo userInfo = this.resourceDetailMapper.getUserInfoById(resource.getUser_id());
        userAndResource.setUserInfo(userInfo);
        return userAndResource;
    }

    @Override
    public List<UserAndResource> getFocusUserResourceByUserId(int user_id, int begin, int number) {
        List<UserAndResource> userAndResources = this.resourceDetailMapper.getFocusResourceByUserId(user_id, begin, number);
        for (UserAndResource item : userAndResources) {
            String headIcon = item.getUserInfo().getHeadIcon();
            if (headIcon != null && !"".equals(headIcon))
                item.getUserInfo().setHeadIcon(this.iconHostURL + headIcon);
        }
        return userAndResources;
    }

    @Override
    public int countUserResourceNumbers(int user_id) {
        return this.resourceDetailMapper.countResourceNumberByUserId(user_id);
    }
}
