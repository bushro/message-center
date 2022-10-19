package com.bushro.message.consumer;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.json.JSONObject;
import com.bushro.message.base.BaseMessage;
import com.bushro.message.dto.MessagePushDTO;
import com.bushro.message.dto.TypeMessageDTO;
import com.bushro.message.enums.MessageTypeEnum;
import com.bushro.message.enums.MqTypeEnum;
import com.bushro.message.handle.IMessageHandler;
import com.bushro.message.handle.MessageHandlerHolder;
import com.bushro.message.handle.email.EmailMessageHandler;
import com.bushro.message.utils.MessageHandlerUtils;
import com.bushro.message.utils.ThreadPoolUtil;
import com.lmax.disruptor.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;


/**
 * Disruptor消费者服务
 *
 * @author bushro
 * @date 2022/09/27
 */
@Service
@Slf4j
public class DisruptorConsumerService implements EventHandler<MessagePushDTO>, ConsumerService {


    @Override
    public void onEvent(MessagePushDTO event, long sequence, boolean endOfBatch) throws Exception {
        TypeMessageDTO typeMessageDTO = event.getMessageDTO();
        try {
            // 处理参数
            JSONObject param = typeMessageDTO.getParam();
            IMessageHandler handler = MessageHandlerHolder.get(event.getMessageTypeEnum());
            ParameterizedType superclass = (ParameterizedType) handler.getClass().getGenericSuperclass();
            Class<?> argument = (Class<?>) superclass.getActualTypeArguments()[0];
            //获取消息处理器的参数类型
            BaseMessage baseMessage = param == null ? (BaseMessage) ReflectUtil.newInstance(argument) : (BaseMessage) param.toBean(argument);
            baseMessage.setRequestNo(event.getRequestNo());
            baseMessage.setConfigIds(typeMessageDTO.getConfigIds());
            handler.setBaseMessage(baseMessage);
            ThreadPoolUtil.getThreadPool().execute(handler.getRunnable());
        } catch (Exception e) {
            log.error("消息处理异常", e);
        }
    }

    @Override
    public MqTypeEnum mqType() {
        return MqTypeEnum.MEMORY;
    }


    @Override
    public void handleMessage(MessagePushDTO messagePushDTO) {

    }

    public static void main(String[] args) {
        EmailMessageHandler handler = new EmailMessageHandler();
//        ParameterizedType superclass = (ParameterizedType) handler.getClass().getGenericSuperclass();
//        Class<?> argument = (Class<?>) superclass.getActualTypeArguments()[0];
//        System.out.println("asf");
        Class clazz = handler.getClass();
        Type type = clazz.getGenericSuperclass();
        System.out.println(type);
        //ParameterizedType参数化类型，即泛型
        ParameterizedType p = (ParameterizedType) type;
        //getActualTypeArguments获取参数化类型的数组，泛型可能有多个
        Class c1 = (Class) p.getActualTypeArguments()[0];
        //c1: class java.lang.Integer
        System.out.println("c1: "+c1);
    }
}
