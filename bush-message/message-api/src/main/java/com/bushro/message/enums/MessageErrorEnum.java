package com.bushro.message.enums;

import com.bushro.common.core.enums.IMessagePairs;

/**
 * @description: DOTO
 * @author bushro
 * @date: 2021/10/12
 */
public enum MessageErrorEnum implements IMessagePairs<Integer, String> {
    //消息推送 31xxxxx
    PUSH_PARAM_ERROR(310000,"消息推送参数错误"),
    PUSH_SUCCESS(310001,"消息投递成功"),
    //企业微信
    //邮件
    //配置 34xxxxx
    CONFIG_NOT_NULL(340000,"配置不能为空"),
    CONFIG_NAME_NOT_NULL(340001,"新增配置名称不能为空"),
    CONFIG_PARAM_NOT_NULL(340002,"配置参数不全"),
    CONFIG_NAME_REPEAT(340003,"配置名称重复"),
    ;

    private Integer code;
    private String msg;

    MessageErrorEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer code() {
        return code;
    }

    @Override
    public String message() {
        return this.msg;
    }
}
