package com.bushro.message.handle;

import com.bushro.message.base.BaseMessage;

public interface IMessageHandler<T extends BaseMessage> {

    /**
     * 设置消息参数
     *
     * @param t t
     */
    void setBaseMessage(T t);
}
