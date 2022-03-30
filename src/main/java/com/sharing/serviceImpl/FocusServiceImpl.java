package com.sharing.serviceImpl;

import com.sharing.mapper.FocusMapper;
import com.sharing.pojo.Focus;
import com.sharing.service.FocusService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
