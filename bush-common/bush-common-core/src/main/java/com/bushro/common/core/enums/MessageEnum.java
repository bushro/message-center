package com.bushro.common.core.enums;

public enum MessageEnum implements IMessagePairs<Integer, String> {
    ERROR(500, "系统内部错误"),
    BUSINESS_ERROR(400, "业务异常"),
    UN_AUTHORIZED(401, "未认证"),
    SUCCESS(200, "成功"),

    METHOD_ARGUMENT_NOT_VALID(1000000, "方法参数校验不过"),
    ILLEGAL_ARGUMENT(1000001, "非法参数")
    ;

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
