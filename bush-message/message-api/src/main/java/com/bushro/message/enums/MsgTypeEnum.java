package com.bushro.message.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 钉钉消息枚举类
 */
@Getter
@AllArgsConstructor
public enum  MsgTypeEnum {
    ACTION_CARD("action_card"),
    IMAGE("image"),
    LINK("link"),
    TEXT("text"),
    FILE("file");
    private String value;
}
