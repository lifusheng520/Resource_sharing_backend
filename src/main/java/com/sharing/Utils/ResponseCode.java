package com.sharing.Utils;

import lombok.Data;

/**
 * 响应状态码枚举类
 *
 * @author 李福生
 * @date 2022-3-6
 * @time 下午 01:31
 * rules:
 * #1001～1999 区间表示登录认证状态码
 * #2001～2999 区间表示用户错误
 */
public enum ResponseCode {

    LOGIN_SUCCESS(1001, "登录成功"),
    LOGOUT_SUCCESS(1002, "退出登录成功"),
    REGISTER_SUCCESS(1003, "注册成功"),
    REGISTER_FAIL(1004, "注册失败"),
    REGISTER_REPETITION(1005, "注册账号已经被注册"),

    /* 参数错误：1000～1999 */
    PARAM_NOT_VALID(1001, "参数无效"),
    PARAM_IS_BLANK(1002, "参数为空"),
    PARAM_TYPE_ERROR(1003, "参数类型错误"),
    PARAM_NOT_COMPLETE(1004, "参数缺失"),

    /* 用户错误 */
    USER_NOT_LOGIN(2001, "用户未登录"),
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
