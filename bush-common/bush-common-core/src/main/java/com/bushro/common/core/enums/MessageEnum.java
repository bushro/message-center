package com.bushro.common.core.enums;

/**
 * 消息枚举
 *
 * @author bushro
 * @date 2022/11/17
 */
public enum MessageEnum implements IMessagePairs<Integer, String> {
    ERROR(500, "系统内部错误"),
    BUSINESS_ERROR(400, "业务异常"),
    UN_AUTHORIZED(401, "未认证"),
    SUCCESS(200, "成功"),
    // 参数校验失败
    PARAM_VALID_ERROR(400, "参数校验失败"),
    // 未登录
    UN_LOGIN(401, "请求未授权"),
    // 未通过认证
    USER_UNAUTHORIZED(402, "用户名或密码不正确"),
    // 用户不存在
    USER_NOT_EXIST(402, "用户不存在"),
    // 未认证（签名错误、token错误）
    UNAUTHORIZED(403, "未认证"),
    // 客户端认证失败
    CLIENT_AUTHENTICATION_FAILED(405, "客户端认证失败"),
    // 接口不存在
    NOT_FOUND(404, "接口不存在"),
    // 非法参数
    ILLEGAL_ARGUMENT(1000001, "非法参数"),
    // token过期
    TOKEN_EXPIRED(-1, "token过期"),
    // token丢失
    TOKEN_NOT_EXIST(-1, "token丢失"),
    // token已被禁止访问
    TOKEN_ACCESS_FORBIDDEN(-1, "token已被禁止访问"),
    // 服务器内部错误
    INTERNAL_SERVER_ERROR(500, "服务器内部错误");

    private Integer code;

    private String msg;

    MessageEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer code() {
        return this.code;
    }

    @Override
    public String message() {
        return this.msg;
    }
}
