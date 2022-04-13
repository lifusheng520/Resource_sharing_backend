package com.sharing.controller;

import com.sharing.Utils.ResponseCode;
import com.sharing.Utils.ResultFormatUtil;
import com.sharing.pojo.MyPage;
import com.sharing.pojo.UserInfo;
import com.sharing.service.AdminUserManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用户管理接口
 *
 * @author 李福生
 * @date 2022-4-12
 * @time 下午 02:58
 */
@RestController
@RequestMapping("/admin")
public class UserManagerController {

    @Autowired
    private AdminUserManagerService adminUserManagerService;

    /**
     * 获取用户账号信息接口
     *
     * @param params 请求的分页参数
     * @return 返回分页数据json
     */
    @PostMapping("/getUserList")
    public String getUserAccountList(@RequestBody Map<String, String> params) {
        if (params == null || params.size() == 0)
            return ResultFormatUtil.format(ResponseCode.REQUEST_PARAM_ERROR, null);

        String currentPage = params.get("currentPage");
        String pageSize = params.get("pageSize");
        String totalPage = params.get("total");

        // 获取分页数据
        int page = MyPage.getValidTransfer(currentPage, "page");
        int size = MyPage.getValidTransfer(pageSize, "size");
        Integer total = Integer.valueOf(totalPage);

        // 查询用户信息
        List<UserInfo> userInfoByPage = this.adminUserManagerService.getUserInfoByPage((page - 1) * size, size);

        // 设置分页数据
        MyPage<UserInfo> myPage = new MyPage<>();
        myPage.setCurrentPage(page);
        myPage.setPageSize(size);
        myPage.setPageList(userInfoByPage);

        if (total < 0)
            total = this.adminUserManagerService.countUserInfoPageTotal();
        myPage.setTotal(total);

        ResponseCode responseCode = ResponseCode.GET_USER_ACCOUNT_INFO_SUCCESS;
        return ResultFormatUtil.format(responseCode, myPage);
    }

    /**
     * 冻结账号
     *
     * @param infos 请求冻结的账号信息
     * @return 返回被冻结的账号id集合
     */
    @PostMapping("/lockAccounts")
    public String lockUserAccount(@RequestBody List<UserInfo> infos) {
        if (infos == null || infos.size() == 0)
            return ResultFormatUtil.format(ResponseCode.REQUEST_PARAM_ERROR, infos);

        ArrayList<Integer> lockList = new ArrayList<>();
        for (UserInfo info : infos) {
            List<String> roles = info.getRoles();
            // 不能冻结管理员账号
            boolean flag = true;
            for (String role : roles)
                if ("admin".equals(role)) {
                    flag = false;
                    break;
                }
            if (flag)
                lockList.add(info.getId());
        }

        // 冻结集合中的账号
        int i = this.adminUserManagerService.lockUserAccount(lockList, 0);

        ResponseCode responseCode;
        if (i > 0)
            responseCode = ResponseCode.LOCK_USER_ACCOUNT_SUCCESS;
        else
            responseCode = ResponseCode.LOCK_USER_ACCOUNT_FAIL;
        return ResultFormatUtil.format(responseCode, lockList);
    }

    /**
     * 冻结账号
     *
     * @param accounts 请求冻结的账号信息
     * @return 返回被冻结的账号id集合
     */
    @PostMapping("/unlockAccounts")
    public String unlockUserAccount(@RequestBody List<UserInfo> accounts) {
        if (accounts == null || accounts.size() == 0)
            return ResultFormatUtil.format(ResponseCode.REQUEST_PARAM_ERROR, accounts);

        ArrayList<Integer> unlockList = new ArrayList<>();
        for (UserInfo account : accounts)
            unlockList.add(account.getId());


        // 重新启用激活集合中的账号
        int i = this.adminUserManagerService.unlockUserAccount(unlockList, 1);

        ResponseCode responseCode;
        if (i > 0)
            responseCode = ResponseCode.UNLOCK_USER_ACCOUNT_SUCCESS;
        else
            responseCode = ResponseCode.UNLOCK_USER_ACCOUNT_FAIL;
        return ResultFormatUtil.format(responseCode, unlockList);
    }


}
