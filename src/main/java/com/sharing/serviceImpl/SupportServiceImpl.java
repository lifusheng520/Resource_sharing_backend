package com.sharing.serviceImpl;

import com.sharing.mapper.SupportServiceMapper;
import com.sharing.service.SupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 李福生
 * @date 2022-4-3
 * @time 上午 11:45
 */

@Service
public class SupportServiceImpl implements SupportService {

    @Autowired
    private SupportServiceMapper supportServiceMapper;

    @Override
    public int countResourceSupportNumbers(int resource_id) {
        return this.supportServiceMapper.countResourceSupportNumbers(resource_id);
    }

    @Override
    public boolean supportIsExist(int user_id, int resource_id) {
        int i = this.supportServiceMapper.countSupportRecord(user_id, resource_id);
        return i > 0;
    }

    @Override
    public int deleteResourceSupportRecord(int user_id, int resource_id) {
        return this.supportServiceMapper.deleteResourceSupportRecord(user_id, resource_id);
    }

    @Override
    public int addResourceSupportRecord(int user_id, int resource_id) {
        return this.supportServiceMapper.addResourceSupportRecord(user_id, resource_id);
    }
}
