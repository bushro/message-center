package com.bushro.message.factory;

import cn.hutool.core.bean.BeanUtil;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.bushro.message.utils.SingletonUtil;

import java.util.concurrent.Executors;

/**
 * 队列工具类
 */
public class DisruptorFactory<T> {

    public DisruptorFactory() {
    }

    private Disruptor<T> getDisruptor(EventFactory<T> eventFactory, EventHandler... messageHandler) {
        return SingletonUtil.get("disruptor_" + eventFactory.getClass().getName(), () -> {
            // 启动消息队列
            int bufferSize = 1024;
            Disruptor<T> disruptor =
                new Disruptor<T>(eventFactory::newInstance, bufferSize, Executors.defaultThreadFactory());
            // 设置消费者
            disruptor.handleEventsWith(messageHandler);
            disruptor.start();
            return disruptor;
        });
    }

    /**
     * @descript :把消息放到队列里面
     * @date : 2022-10-28
     * @param message : 消息
     * @param eventFactory : 事件工厂
     * @param messageHandler : 处理器
     * @return : void
     */
    public void push(T message, EventFactory<T> eventFactory, EventHandler... messageHandler) {
        // 往队列里扔
        RingBuffer<T> ringBuffer = getDisruptor(eventFactory, messageHandler).getRingBuffer();
        long sequence = ringBuffer.next();
        try {
            T event = ringBuffer.get(sequence);
            BeanUtil.copyProperties(message, event);
        } finally {
            // 发布事件
            ringBuffer.publish(sequence);
        }
    }
}
