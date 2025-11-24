package com.sharing.service;

import com.sharing.pojo.BrowseRecord;

import java.util.List;

/**
 * @author 李福生
 * @date 2022-4-15
 * @time 上午 10:47
 */
public interface BrowseRecordService {

    int addRecord(BrowseRecord record);

    List<BrowseRecord> getRecordListByUserId(int userId, int begin, int number);

    int countRecordNumberByUserId(int userId);

    int deleteRecord(List<Integer> recordIdList);
}
