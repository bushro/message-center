package com.bushro.common.redis.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 监听redis过期事件
 * </p>
 *
 * @author luo.qiang
 * @description
 * @date 2022/11/17 22:55
 */
@Slf4j
@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    /**
     * redis - key失效事件监听
     *
     * @param message message.toString()获取失效事件key
     * @param pattern
     * @return void
     * @author luo.qiang
     * @date 2022/11/17 14:12
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expiredKey = message.toString();
        log.debug(" ****************** redis -> 过期key: 【{}】 ****************** ", expiredKey);
    }

}
