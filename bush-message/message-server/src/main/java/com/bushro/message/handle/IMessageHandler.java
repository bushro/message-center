package com.bushro.message.handle;

import com.bushro.message.enums.MessageTypeEnum;

/**
 * imessage处理程序
 *
 * @author bushro
 * @date 2022/10/19
 */
public interface IMessageHandler {

    /**
     * 消息处理器需要使用的参数
     *
     * @param object 对象
     */
    void setBaseMessage(Object object);


    /**
     * 获取线程
     *
     * @return {@link Runnable}
     */
    Runnable getRunnable();

    /**
     * 所有消息处理器必须实现这个接口，标识自己处理的是哪个消息类型
     *
     * @return {@link MessageTypeEnum}
     */
    MessageTypeEnum messageType();
}
