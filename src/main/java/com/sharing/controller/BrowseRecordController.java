package com.sharing.controller;

import cn.hutool.core.util.StrUtil;
import com.sharing.Utils.ResponseCode;
import com.sharing.Utils.ResultFormatUtil;
import com.sharing.pojo.BrowseRecord;
import com.sharing.pojo.MyPage;
import com.sharing.service.BrowseRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 李福生
 * @date 2022-4-15
 * @time 上午 10:49
 */

@RestController
@RequestMapping("/browse")
public class BrowseRecordController {

    @Value("${files.icon.host.url}")
    private String iconHostURL;

    @Autowired
    private BrowseRecordService browseRecordService;

    @GetMapping("/add/{user_id}/{resource_id}")
    public void addBrowseRecord(@PathVariable Integer user_id, @PathVariable Integer resource_id) {
        if (user_id == null || user_id == 0)
            return;
        if (resource_id == null || resource_id == 0)
            return;

        BrowseRecord browseRecord = new BrowseRecord();
        browseRecord.setUser_id(user_id);
        browseRecord.setResource_id(resource_id);
        browseRecord.setTime(new Date());
        browseRecord.setIsDeleted(0);

        // 更新到数据库
        this.browseRecordService.addRecord(browseRecord);
    }

    @PostMapping("/getList")
    public String addBrowseRecord(@RequestBody Map<String, String> pageMap) {
        if (pageMap == null || pageMap.size() == 0 || StrUtil.isEmpty(pageMap.get("user_id")))
            return ResultFormatUtil.format(ResponseCode.REQUEST_PARAM_ERROR, null);

        String currentPage = pageMap.get("currentPage");
        String pageSize = pageMap.get("pageSize");
        String total = pageMap.get("total");
        String user_id = pageMap.get("user_id");

        int page = MyPage.getValidTransfer(currentPage, "page");
        int size = MyPage.getValidTransfer(pageSize, "size");
        Integer totalPage = Integer.valueOf(total);
        Integer userId = Integer.valueOf(user_id);

        // 查询用户的浏览记录
        List<BrowseRecord> recordList = this.browseRecordService.getRecordListByUserId(userId, (page - 1) * size, size);
        if (totalPage < 0)
            totalPage = this.browseRecordService.countRecordNumberByUserId(userId);

        // 设置用户头像
        for (BrowseRecord record : recordList) {
           String icon =  record.getHeadIcon();
           if(StrUtil.isEmpty(icon)){
               record.setHeadIcon("");
           } else{
               String iconURL = this.iconHostURL + record.getHeadIcon();
               record.setHeadIcon(iconURL);
           }
        }

        MyPage<BrowseRecord> recordMyPage = new MyPage<>();
        recordMyPage.setCurrentPage(page);
        recordMyPage.setPageSize(size);
        recordMyPage.setPageList(recordList);
        recordMyPage.setTotal(totalPage);

        ResponseCode responseCode = ResponseCode.GET_BROWSE_RECORD_SUCCESS;
        return ResultFormatUtil.format(responseCode, recordMyPage);
    }


    @PostMapping("/delete")
    public String deleteBrowseRecord(@RequestBody List<Integer> recordIdList) {
        if (recordIdList == null || recordIdList.size() == 0)
            return ResultFormatUtil.format(ResponseCode.REQUEST_PARAM_ERROR, null);

        // 根据记录的id，将记录置为删除状态
        int i = this.browseRecordService.deleteRecord(recordIdList);
        ResponseCode responseCode;
        if (i>0)
            responseCode = ResponseCode.DELETE_BROWSE_RECORD_SUCCESS;
        else
            responseCode = ResponseCode.DELETE_BROWSE_RECORD_FAIL;
        return ResultFormatUtil.format(responseCode, null);
    }
}
