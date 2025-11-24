package com.sharing.serviceImpl;

import com.sharing.mapper.BrowseRecordMapper;
import com.sharing.pojo.BrowseRecord;
import com.sharing.service.BrowseRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 李福生
 * @date 2022-4-15
 * @time 上午 10:47
 */

@Service
public class BrowseRecordServiceImpl implements BrowseRecordService {

    @Autowired
    private BrowseRecordMapper browseRecordMapper;

    @Override
    public int addRecord(BrowseRecord record) {
        return this.browseRecordMapper.insertRecord(record);
    }

    @Override
    public List<BrowseRecord> getRecordListByUserId(int userId, int begin, int number) {
        return this.browseRecordMapper.queryRecordListByUserId(userId, begin, number);
    }

    @Override
    public int countRecordNumberByUserId(int userId) {
        return this.browseRecordMapper.countRecordNumberByUserId(userId);
    }

    @Override
    public int deleteRecord(List<Integer> recordIdList) {
        return this.browseRecordMapper.deleteRecord(recordIdList);
    }
}
