package com.bushro.message.handle;

import com.bushro.common.core.util.SpringContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import com.bushro.message.enums.MessageTypeEnum;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 加载所有的消息处理器
 */
@Component
public class MessageHandlerHolder implements CommandLineRunner {


    /**
     * 系统所有的消息处理器
     */
    private static final Map<MessageTypeEnum, MessageHandler<?>> HANDLER_MAP = new LinkedHashMap<>();

    public static MessageHandler<?> get(MessageTypeEnum messageType) {
        return HANDLER_MAP.get(messageType);
    }

    public static Collection<MessageHandler<?>> values() {
        return HANDLER_MAP.values();
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void run(String... args) {
        Map<String, MessageHandler> springMap = SpringContextHolder.getBeansOfType(MessageHandler.class);
        for (MessageHandler messageHandler : springMap.values()) {
            MessageTypeEnum messageType = messageHandler.messageType();
            if (HANDLER_MAP.containsKey(messageType)) {
                // 一种消息平台只接受一个消息处理器（如果接受多个会有处理器执行的顺序问题，会变复杂，暂时不处理这种情况）
                throw new IllegalStateException("存在重复消息处理器");
            }
            HANDLER_MAP.put(messageType, messageHandler);
        }
    }
}
