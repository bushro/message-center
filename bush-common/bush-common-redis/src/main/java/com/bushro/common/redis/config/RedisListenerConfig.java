package com.bushro.common.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * <p>
 * redis监听事件配置
 * </p>
 *
 * @author luo.qiang
 * @description 1.监听redis过期事件 {@link com.bushro.common.redis.config.RedisKeyExpirationListener}
 * -            2.发布订阅 {@link com.bushro.common.redis.config.DemoRedisListenerConfig }
 * @date 2022/11/17 13:56
 */
@Configuration
public class RedisListenerConfig {
    @Bean
    @Primary
    RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory
//            , MessageListener redisTestListener
    ) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        // 加入发布订阅配置
        // 添加消息监听者，可以是多个
//        container.addMessageListener(
//                new MessageListenerAdapter(redisTestListener),
//                new ChannelTopic(RedisConstant.REDIS_CHANNEL_TEST)
//        );
//        container.addMessageListener(
//                new MessageListenerAdapter(new MyListener()),
//                new ChannelTopic(RedisConstant.REDIS_CHANNEL_ZQ)
//        );
        return container;
    }

}
