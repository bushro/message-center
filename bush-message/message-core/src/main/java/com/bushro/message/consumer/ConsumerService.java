package com.bushro.message.consumer;

import com.bushro.message.base.BaseMessage;

/**
 * @description: DOTO
 * @author: luoq
 * @date: 2022/9/27
 */
public interface ConsumerService {

    /**
     * 接收消息
     *
     * @return {@link BaseMessage}
     */
    BaseMessage receiveMessage();
}
