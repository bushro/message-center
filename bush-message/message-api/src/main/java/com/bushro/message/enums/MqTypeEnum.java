package com.bushro.message.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MqTypeEnum {
    MEMORY,
    ROCKETMQ;
}
