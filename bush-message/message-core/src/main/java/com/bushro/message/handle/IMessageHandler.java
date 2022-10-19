package com.bushro.message.handle;

import com.bushro.message.base.BaseMessage;

/**
 * imessage处理程序
 *
 * @author bushro
 * @date 2022/10/19
 */
public interface IMessageHandler<T extends BaseMessage> {

    /**
     * 设置消息参数
     *
     * @param t t
     */
    void setBaseMessage(T t);
}
