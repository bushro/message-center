package com.bushro.message.consumer;

import com.bushro.message.enums.MqTypeEnum;


/**
 * 抽象消息处理程序
 *
 * @author bushro
 * @date 2022/09/27
 */
public abstract class AbstractConsumerHandler{

    /**
     * mq类型
     */
    public abstract MqTypeEnum mqType();

}
