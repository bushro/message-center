package com.bushro.message.handle;

import com.bushro.common.core.util.SpringContextHolder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.bushro.message.enums.MessageTypeEnum;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 加载所有的消息处理器
 *
 * @author bushro
 * @date 2022/10/19
 */
@Component
public class MessageHandlerHolder implements CommandLineRunner {


    /**
     * 系统所有的消息处理器
     */
    private static final Map<MessageTypeEnum, IMessageHandler> HANDLER_MAP = new LinkedHashMap<>();

    public static IMessageHandler get(MessageTypeEnum messageType) {
        return HANDLER_MAP.get(messageType);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void run(String... args) {
        Map<String, IMessageHandler> HandlerMap = SpringContextHolder.getBeansOfType(IMessageHandler.class);
        for (IMessageHandler messageHandler : HandlerMap.values()) {
            MessageTypeEnum messageType = messageHandler.messageType();
            if (HANDLER_MAP.containsKey(messageType)) {
                // 一种消息平台只接受一个消息处理器（如果接受多个会有处理器执行的顺序问题，会变复杂，暂时不处理这种情况）
                throw new IllegalStateException("存在重复消息处理器");
            }
            HANDLER_MAP.put(messageType, messageHandler);
        }
    }
}
