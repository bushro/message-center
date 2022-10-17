package com.bushro.message.handle;

import com.bushro.message.enums.MessageTypeEnum;

public interface IMessageHandler {

    /**
     * 所有消息处理器必须实现这个接口，标识自己处理的是哪个消息类型
     */
    MessageTypeEnum messageType();
}
