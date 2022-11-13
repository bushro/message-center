package com.bushro.message.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @description: 发送状态
 * @author bushro
 * @date: 2022/10/9
 */
@Getter
@AllArgsConstructor
public enum SendStatusEnum {
    SEND_STATUS_NOT_START("未开始", 0),
    SEND_STATUS_SUCCESS("发送成功", 1),
    SEND_STATUS_FAIL("发送失败", 2);

    /** 描述信息 */
    private String description;

    /** 状态 */
    private Integer code;

    public static SendStatusEnum of(Integer code) {
        Objects.requireNonNull(code);
        return Stream.of(values())
            .filter(bean -> bean.code.equals(code))
            .findAny()
            .orElseThrow(
                () -> new IllegalArgumentException(code + " not exists")
            );
    }
}
