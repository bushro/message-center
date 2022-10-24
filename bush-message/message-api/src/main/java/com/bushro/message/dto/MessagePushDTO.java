package com.bushro.message.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.bushro.message.base.BaseParam;
import com.bushro.message.enums.MessageTypeEnum;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 消息推送参数
 *
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessagePushDTO extends BaseParam {
    private static final long serialVersionUID = 2732930320545780215L;

    /**
     * 原始参数，没有处理过的
     */
    private String param;

    /**
     * 消息类型
     */
    private MessageTypeEnum messageTypeEnum;

    /**
     * 消息参数
     */
    private TypeMessageDTO messageDTO;
}
