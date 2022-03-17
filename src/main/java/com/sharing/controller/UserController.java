package com.sharing.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.sharing.Utils.ResponseCode;
import com.sharing.Utils.ResultFormatUtil;
import com.sharing.config.MyEmailSenderConfig;
import com.sharing.pojo.UserInfo;
import com.sharing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
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
     * 邮箱发送器
     */
    @Autowired
    private MyEmailSenderConfig emailSender;


    @PostMapping("/updatePass")
    public String updateUserPassword(@RequestBody Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");
        String checkPass = params.get("checkPass");
        String verifyCode = params.get("verifyCode");

        // 从数据库中查询用户名对应的验证码和发送时间
        UserInfo verifyCodeAndTime = this.userService.getUserInfo(username);
        String code = verifyCodeAndTime.getVerifyCode();
        Date time = verifyCodeAndTime.getVerifyTime();

        if (code == null || "".equals(code))
            return ResultFormatUtil.format(ResponseCode.EMAIL_CODE_NOT_FOUND, username);

        // 校验验证码是否正确且未过期
        long end = System.currentTimeMillis();
        long begin = time.getTime();
        long seconds = (end - begin) / 1000;
        if (code.equals(verifyCode) && seconds <= MyEmailSenderConfig.VERIFY_VALID_TIME) {
            // 校验通过，修改用户密码
            if (password != null && !"".equals(password) && password.equals(checkPass)) {
                // 加密用户密码
                int i = this.userService.updatePassword(username, new BCryptPasswordEncoder().encode(password));
                if (i > 0)
                    return ResultFormatUtil.format(ResponseCode.UPDATE_PASSWORD_SUCCESS, username);
            }

        }

        return ResultFormatUtil.format(ResponseCode.UPDATE_PASSWORD_FAIL, username);
    }


    /**
     * 向用户发送一封邮件，用于修改密码的身份验证
     *
     * @param username 用户名
     * @return 发送状态
     */
    @GetMapping("/authEmail/{username}")
    public String authenticationUserEmail(@PathVariable String username) {
        if (username == null || "".equals(username))
            return ResultFormatUtil.format(ResponseCode.REQUEST_PARAM_ERROR, "username");

        // 获取用户的邮箱号
        UserInfo info = this.userService.getUserInfo(username);
        String email = info.getEmail();
        if (email == null || "".equals(email))
            return ResultFormatUtil.format(ResponseCode.EMAIL_NOT_BIND, username);

        // 获取一个六位数的验证码
        String verifyCode = MyEmailSenderConfig.generateVerifyCode(6);

        // 发送邮箱
        String title = "重置密码验证";
        String message = "重置您的账号密码";
        boolean b = this.emailSender.sendEmail(email, title, message, verifyCode);
        if (!b)
            return ResultFormatUtil.format(ResponseCode.EMAIL_SEND_FAIL, username);

        // 将验证码更新到数据库
        info.setVerifyCode(verifyCode);
        info.setVerifyTime(new Date());
        int id = this.userService.getUserIdByUsername(username);
        info.setId(id);
        int i = this.userService.updateEmailVerifyCode(info);

        if (i > 0)
            return ResultFormatUtil.format(ResponseCode.EMAIL_SEND_SUCCESS, username);
        else
            return ResultFormatUtil.format(ResponseCode.EMAIL_SEND_FAIL, username);
    }

    @PostMapping("/bindEmail")
    public String bindEmailByVerifyCode(@RequestBody Map<String, String> params) {
        // 获取请求参数
        int id = Integer.valueOf(params.get("id"));
        String email = params.get("email");
        String verifyCode = params.get("verifyCode");

        // 查询数据库中的用户验证码信息
        UserInfo info = this.userService.getVerifyCodeAndTime(id);
        String code = info.getVerifyCode();

        // 如果验证码不匹配
        if (code != null && !code.equals(verifyCode)) {
            return ResultFormatUtil.format(ResponseCode.EMAIL_VERIFY_CODE_ERROR, email);
        }

        // 如果验证码已经过期了
        long currentTimeMillis = System.currentTimeMillis();
        long time = info.getVerifyTime().getTime();
        long interval = (currentTimeMillis - time) / 1000;
        if (interval > MyEmailSenderConfig.VERIFY_VALID_TIME) {
            return ResultFormatUtil.format(ResponseCode.EMAIL_VERIFY_CODE_EXPIRED, email);
        }

        // 验证码正确且没有过时，更新用户邮箱号
        info.setId(id);
        info.setEmail(email);
        int i = this.userService.updateUserEmail(info);

        if (i > 0)
            return ResultFormatUtil.format(ResponseCode.EMAIL_BIND_SUCCESS, email);
        else
            return ResultFormatUtil.format(ResponseCode.EMAIL_BIND_FAIL, email);
    }

    /**
     * 邮箱验证码发送接口
     *
     * @param params 用户的id和邮箱地址参数map
     * @return 邮件发送的状态码json字符串
     */
    @PostMapping("/sendVerifyCode")
    public String sendUserEmail(@RequestBody Map<String, String> params) {
        // 获取用户id和邮箱地址
        int id = Integer.valueOf(params.get("id"));
        String emailAddress = params.get("email");

        //  获取一个六位数的验证码
        String verifyCode = MyEmailSenderConfig.generateVerifyCode(6);

        // 向用户输入的邮箱地址发送验证码
        String title = "邮箱绑定";
        String message = "绑定安全邮箱";
        boolean b = this.emailSender.sendEmail(emailAddress, title, message, verifyCode);
        if (!b) {    // 邮箱发送失败
            return ResultFormatUtil.format(ResponseCode.EMAIL_SEND_FAIL, emailAddress);
        }

        // 获取当前时间戳，记录验证码发送时间，并将验证码和发送时间存储到数据库中
        UserInfo info = new UserInfo();
        Date time = new Date();
        info.setId(id);
        info.setVerifyCode(verifyCode);
        info.setVerifyTime(time);
        int i = this.userService.updateEmailVerifyCode(info);

        if (i > 0)
            return ResultFormatUtil.format(ResponseCode.EMAIL_SEND_SUCCESS, time);
        else
            return ResultFormatUtil.format(ResponseCode.EMAIL_SEND_FAIL, emailAddress);
    }

    @GetMapping("/icon/{iconFileName}")
    public void getIconUrl(@PathVariable String iconFileName, HttpServletResponse response) throws IOException {
        // 获取磁盘文件绝对路径
        String distFile = this.iconUploadPath + iconFileName;
        File file = new File(distFile);

        // 设置输出流格式
        ServletOutputStream outputStream = response.getOutputStream();
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(iconFileName, "UTF-8"));
        response.setContentType("application/octet-stream");

        // 读取文件的字节流
        outputStream.write(FileUtil.readBytes(file));
        outputStream.flush();
        outputStream.close();
    }


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
        // 设置用户头像URL
        String icon = userInfo.getHeadIcon();
        userInfo.setHeadIcon(this.iconHostUrl + icon);
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
