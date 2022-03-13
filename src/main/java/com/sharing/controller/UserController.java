package com.sharing.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.sharing.Utils.ResponseCode;
import com.sharing.Utils.ResultFormatUtil;
import com.sharing.pojo.UserInfo;
import com.sharing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * 用户信息相关操的作接口
 *
 * @author 李福生
 * @date 2022-3-12
 * @time 下午 08:18
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户头像上传的磁盘位置
     */
    @Value("${files.icon.upload.path}")
    private String iconUploadPath;

    /**
     * 用户头像的请求URL
     */
    @Value("${files.icon.host.url}")
    private String iconHostUrl;

    /**
     * 头像文件上传接口
     *
     * @param file 接收的文件
     * @param id   用户id
     * @return 处理的状态码，如果成功将图片URL和状态码一并返回，否则返回上传的源文件名
     * @throws IOException 中间对磁盘操作可能抛出io异常
     */
    @PostMapping("/uploadIcon/{id}")
    public String uploadIcon(@RequestParam MultipartFile file, @PathVariable int id) {
        // 获取源文件名
        String originalFilename = file.getOriginalFilename();
        // 获取文件后缀名
        String type = FileUtil.extName(originalFilename);
        //获取文件大小
        long size = file.getSize();

        // 头像文件磁盘路径
        File uploadFile = new File(this.iconUploadPath);
        if (!uploadFile.exists()) {   // 判断文件目录是否存在
            uploadFile.mkdirs();
        }
        // 文件的唯一标识id
        String fileId = IdUtil.fastSimpleUUID();
        String iconFile = fileId + StrUtil.DOT + type;
        // 本地磁盘绝对路径
        File distFile = new File(this.iconUploadPath + iconFile);

        try {
            // 先将头像写入到磁盘
            file.transferTo(distFile);
        } catch (IOException e) {   // 上传失败
            e.printStackTrace();
            return ResultFormatUtil.format(ResponseCode.EXCEPTION_IO_UPLOAD, originalFilename);
        }
        // 文件的主机URL
        String url = this.iconHostUrl + iconFile;
        // 更新数据库头像文件名
        UserInfo info = new UserInfo();
        info.setId(id);
        info.setHeadIcon(iconFile);
        int i = this.userService.updateUserIcon(info);
        if (i > 0)
            return ResultFormatUtil.format(ResponseCode.USER_ICON_UPDATE_SUCCESS, url);
        else
            return ResultFormatUtil.format(ResponseCode.USER_ICON_UPDATE_FAIL, originalFilename);
    }

    /**
     * 根据用户的username查询用户信息
     *
     * @param username 用户名
     * @return 用户信息json字符串
     */
    @GetMapping("/getInfo/{username}")
    public String getUserInfo(@PathVariable String username) {
        // 缺失用户名
        if (username == null) {
            return ResultFormatUtil.format(ResponseCode.REQUEST_PARAM_LOSE, null);
        }

        // 从数据库中查询用户信息
        UserInfo userInfo = this.userService.getUserInfo(username);

        // 用户不存在
        if (userInfo == null) {
            return ResultFormatUtil.format(ResponseCode.REQUEST_USER_DONT_EXIST, null);
        }

        return ResultFormatUtil.format(ResponseCode.REQUEST_USER_INFO_SUCCESS, userInfo);
    }

    /**
     * 修改用户信息
     *
     * @param infoParams 用户信息参数
     * @return 修改结果的json字符串
     */
    @PostMapping("/updateInfo")
    public String updateUserInfo(@RequestBody Map<String, String> infoParams) {
        System.out.println(infoParams);
        Integer id = Integer.valueOf(infoParams.get("id"));

        if (id == null) {
            return ResultFormatUtil.format(ResponseCode.REQUEST_PARAM_LOSE, null);
        }

        // 获取并设置用户信息
        UserInfo userInfo = new UserInfo();
        userInfo.setId(id);
        userInfo.setName(infoParams.get("name"));
        userInfo.setSex(infoParams.get("sex"));
        userInfo.setAddress(infoParams.get("address"));
        System.out.println(userInfo);

        // 将用户信息存储到数据库
        int i = this.userService.updateUserInfo(userInfo);
        if (i > 0)
            return ResultFormatUtil.format(ResponseCode.USER_INFO_UPDATE_SUCCESS, userInfo);
        else
            return ResultFormatUtil.format(ResponseCode.USER_INFO_UPDATE_FAIL, userInfo);
    }
}
