package com.bushro.message.consumer;

import com.bushro.message.base.BaseMessage;
import com.bushro.message.dto.MessagePushDTO;
import com.bushro.message.dto.TypeMessageDTO;
import com.bushro.message.enums.MessageTypeEnum;
import com.bushro.message.enums.MqTypeEnum;

import java.util.Map;

/**
 * @description: DOTO
 * @author: luoq
 * @date: 2022/9/27
 */
public interface ConsumerService {


    /**
     * 处理消息
     *
     * @param messageParam 消息参数
     */
    void handleMessage(MessagePushDTO messagePushDTO);

    /**
     * mq类型
     */
    MqTypeEnum mqType();
}
