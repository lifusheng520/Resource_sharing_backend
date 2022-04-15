package com.sharing.mapper;

import com.sharing.pojo.BrowseRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 李福生
 * @date 2022-4-15
 * @time 上午 10:48
 */

@Mapper
@Repository
public interface BrowseRecordMapper {

    int insertRecord(BrowseRecord record);

    List<BrowseRecord> queryRecordListByUserId(int userId, int begin, int number);

    int countRecordNumberByUserId(int userId);

    int deleteRecord(List<Integer> recordIdList);

}
