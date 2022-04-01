package com.sharing.serviceImpl;

import com.sharing.mapper.FocusMapper;
import com.sharing.pojo.Focus;
import com.sharing.service.FocusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户关注业务实现类
 *
 * @author 李福生
 * @date 2022-3-30
 * @time 上午 11:44
 */

@Service
public class FocusServiceImpl implements FocusService {

    @Value("${files.icon.host.url}")
    private String iconHostURL;

    @Autowired
    private FocusMapper focusMapper;

    @Override
    public int addFocus(Focus focus) {
        return this.focusMapper.addFocus(focus);
    }

    @Override
    public List<Focus> getUserFocusListById(int user_id) {
        return this.focusMapper.getFocusListById(user_id);
    }

    @Override
    public int cancelFocus(int user_id, int focus_uid) {
        return this.focusMapper.deleteFocus(user_id, focus_uid);
    }

    @Override
    public List<Focus> getUserFocusPageListByUserId(int user_id, int begin, int number) {
        List<Focus> resultList = this.focusMapper.getFocusPageListByUserId(user_id, begin, number);
        // 遍历结果集，为用户添加头像地址
        for (Focus focus : resultList) {
            String icon = focus.getFocusUserIcon();
            if(icon != null && !"".equals(icon))
                focus.setFocusUserIcon(this.iconHostURL + icon);
        }
        return resultList;
    }

    @Override
    public int countFocusNumberByUserId(int user_id) {
        return this.focusMapper.countFocusByUserId(user_id);
    }
}
