package com.sharing.Utils;

import lombok.Data;

/**
 * 响应状态码枚举类
 *
 * @author 李福生
 * @date 2022-3-6
 * @time 下午 01:31
 * rules:
 * #1001～1999 区间表示登录认证状态码和消息
 * #2001～2999 区间表示系统异常状态码和消息
 * #3001～3999 区间表示请求类状态
 * #4001～4999 区间表示用户业务请求状态和消息
 * #5001～5999 区间表示用户 评论业务请求状态和消息
 * #6001～6050 区间表示用户 关注业务请求状态和消息
 * #6051～6999 区间表示用户 关注业务请求状态和消息
 */
public enum ResponseCode {

    /**
     * 用户认证类型的响应状态码和消息
     */
    LOGIN_SUCCESS(1001, "登录成功"),
    LOGOUT_SUCCESS(1002, "退出登录成功"),
    REGISTER_SUCCESS(1003, "注册成功"),
    REGISTER_FAIL(1004, "注册失败"),
    REGISTER_REPETITION(1005, "注册账号已经被注册"),
    USER_NOT_LOGIN(1006, "您还没有登录，请先登录再操作"),
    USER_NOT_PERMISSION(1007, "您没有该资源的访问权限"),
    SESSION_EXPIRED(1008, "您的登录已过期，请重新登录"),
    UPDATE_PASSWORD_FAIL(1009, "验证码错误或失效，修改密码失败"),
    UPDATE_PASSWORD_SUCCESS(1010, "修改密码成功"),

    /**
     * 系统异常状态码和消息
     */
    EXCEPTION_RUNTIME(2001, "系统运行时发生异常"),
    EXCEPTION_IO(2002, "文件IO操作异常"),
    EXCEPTION_IO_UPLOAD(2003, "文件上传异常"),

    /**
     * 请求类状态
     */
    REQUEST_PARAM_LOSE(3001, "请求参数缺失"),
    REQUEST_USER_DONT_EXIST(3002, "还未添加用户信息，请先添加用户信息"),
    REQUEST_USER_INFO_SUCCESS(3003, "查询用户信息请求成功"),
    REQUEST_PARAM_ERROR(3004, "请求参数错误"),


    /**
     * 用户个人信息业务
     */
    USER_INFO_UPDATE_SUCCESS(4001, "用户信息更新成功"),
    USER_INFO_UPDATE_FAIL(4002, "用户信息更新失败"),
    USER_ICON_UPDATE_SUCCESS(4003, "头像上传成功"),
    USER_ICON_UPDATE_FAIL(4004, "头像上传失败"),
    EMAIL_SEND_FAIL(4005, "邮件发送失败"),
    EMAIL_SEND_SUCCESS(4006, "邮件发送成功"),
    EMAIL_VERIFY_CODE_ERROR(4007, "邮箱验证码错误"),
    EMAIL_VERIFY_CODE_EXPIRED(4008, "邮箱验证码错误"),
    EMAIL_BIND_SUCCESS(4009, "邮箱绑定成功"),
    EMAIL_BIND_FAIL(4010, "邮箱绑定失败"),
    EMAIL_NOT_BIND(4011, "账号还未绑定邮箱"),
    EMAIL_CODE_NOT_FOUND(4012, "未查询到邮箱验证码"),


    /**
     * 资源文件业务
     */
    RESOURCE_UPLOAD_SUCCESS(4013, "资源文件上传成功"),
    RESOURCE_UPLOAD_FAIL(4014, "资源文件上传异常，上传失败"),
    GET_RESOURCE_LIST_SUCCESS(4015, "资源文件列表获取成功"),
    NULL_RESOURCE(4016, "没有资源"),
    RESOURCE_NOT_SEARCH(4017, "没有搜索到资源"),
    RESOURCE_SEARCH_SUCCESS(4018, "搜索资源成功"),
    DELETE_RESOURCE_SUCCESS(4019, "删除资源成功"),
    DELETE_RESOURCE_FAIL(4020, "删除资源失败"),
    UPDATE_RESOURCE_SUCCESS(4021, "资源更新成功"),
    UPDATE_RESOURCE_FAIL(4022, "资源更新失败"),
    GET_RESOURCE_SUCCESS(4027, "获取资源成功"),


    /**
     * 首页业务
     */
    INDEX_SYSTEM_INFO_GET_SUCCESS(4023, "首页系统信息获取成功"),
    INDEX_SYSTEM_GET_RECOMMEND_SUCCESS(4024, "首页推荐资源获取成功"),
    GET_RECOMMEND_RANK_INFO_SUCCESS(4025, "获取排行榜数据成功"),
    GET_ALL_RESOURCE_DISCIPLINE_SUCCESS(4026, "资源类别列表获取成功"),
    GET_RESOURCE_DETAIL_SUCCESS(4028, "获取资源详细信息成功"),

    /**
     * 资源评论业务
     */
    RESOURCE_COMMENT_SUCCESS(5001, "资源评论成功"),
    RESOURCE_COMMENT_FAIL(5002, "资源评论失败"),
    ILLEGAL_RESOURCE_COMMENT_CONTENT(5003, "评论内容违规，请注意言辞"),
    GET_RESOURCE_COMMENT_INFO_SUCCESS(5004, "获取评论区内容成功"),
    GET_USER_ALL_COMMENT_SUCCESS(5005, "获取用户评论成功"),
    DELETE_COMMENT_SUCCESS(5006, "评论删除成功"),
    DELETE_COMMENT_FAIL(5007, "评论删除失败"),

    /**
     * 用户关注业务
     */
    FOCUS_ADD_SUCCESS(6001, "添加关注成功"),
    FOCUS_ADD_FAIL(6002, "添加关注失败"),
    GET_FOCUS_LIST_SUCCESS(6003, "获取关注内容列表成功"),
    CANCEL_FOCUS_SUCCESS(6004, "取消关注成功"),
    CANCEL_FOCUS_FAIL(6005, "取消关注失败"),
    GET_FOCUS_INFO_SUCCESS(6006, "获取关注信息成功"),
    GET_FOCUS_USER_RESOURCE_SUCCESS(6007, "成功获取关注资源"),

    /**
     * 点赞支持类业务
     */
    RESOURCE_ADD_SUPPORT_SUCCESS(6051, "点赞成功，感谢你的支持"),
    RESOURCE_DELETE_SUPPORT_SUCCESS(6052, "取消点赞成功"),


    /* 用户错误 */
    USER_ACCOUNT_EXPIRED(2002, "账号已过期"),
    USER_CREDENTIALS_ERROR(2003, "密码错误"),
    USER_CREDENTIALS_EXPIRED(2004, "密码过期"),
    USER_ACCOUNT_DISABLE(2005, "账号不可用"),
    USER_ACCOUNT_LOCKED(2006, "账号被锁定"),
    USER_ACCOUNT_NOT_EXIST(2007, "账号不存在"),
    USER_ACCOUNT_ALREADY_EXIST(2008, "账号已存在"),
    USER_ACCOUNT_USE_BY_OTHERS(2009, "您的登录已经超时或者已经在另一台机器登录，您被迫下线"),
    USER_SESSION_INVALID(2010, "登录已经超时"),
    /* 业务错误 */
    NO_PERMISSION(4001, "没有权限");

    private int code;
    private String message;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
