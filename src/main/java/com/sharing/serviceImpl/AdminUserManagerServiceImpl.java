package com.sharing.serviceImpl;

import com.sharing.mapper.AdminUserManagerMapper;
import com.sharing.mapper.UserMapper;
import com.sharing.pojo.UserInfo;
import com.sharing.service.AdminUserManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 李福生
 * @date 2022-4-12
 * @time 下午 03:06
 */

@Service
public class AdminUserManagerServiceImpl implements AdminUserManagerService {

    @Autowired
    private AdminUserManagerMapper adminUserManagerMapper;


    @Autowired
    private UserMapper userMapper;

    @Override
    public List<UserInfo> getUserInfoByPage(int begin, int number) {
        List<UserInfo> userInfos = this.adminUserManagerMapper.queryUserInfoByPage(begin, number);
        for (UserInfo info : userInfos) {
            int id = info.getId();
            List<String> roles = this.userMapper.getRoles(id);
            info.setRoles(roles);
        }
        return userInfos;
    }

    @Override
    public int countUserInfoPageTotal() {
        return this.adminUserManagerMapper.countUserInfoPageTotal();
    }

    @Override
    public int lockUserAccount(List<Integer> idList, int enabled) {
        return this.adminUserManagerMapper.updateUserAccountEnabled(idList, enabled);
    }

    @Override
    public int unlockUserAccount(List<Integer> unlockIdList, int enabled) {
        return this.adminUserManagerMapper.updateUserAccountEnabled(unlockIdList, enabled);
    }
}
