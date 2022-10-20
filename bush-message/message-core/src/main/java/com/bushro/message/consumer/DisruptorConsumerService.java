package com.bushro.message.consumer;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.json.JSONObject;
import com.bushro.message.base.BaseMessage;
import com.bushro.message.dto.MessagePushDTO;
import com.bushro.message.dto.TypeMessageDTO;
import com.bushro.message.entity.MessageRequest;
import com.bushro.message.enums.MqTypeEnum;
import com.bushro.message.handle.IMessageHandler;
import com.bushro.message.handle.MessageHandlerHolder;
import com.bushro.message.service.IMessageRequestService;
import com.bushro.message.utils.ThreadPoolUtil;
import com.lmax.disruptor.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.time.LocalDateTime;


/**
 * Disruptor消费者服务
 *
 * @author bushro
 * @date 2022/09/27
 */
@Service
@Slf4j
public class DisruptorConsumerService implements EventHandler<MessagePushDTO>, ConsumerService {

    @Resource
    private IMessageRequestService messageRequestService;


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
            //记录请求日志
            log(event);
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

    /**
     * 日志
     *
     * @param event 事件
     */
    public void log (MessagePushDTO event) {
        // 记录请求参数信息
        MessageRequest request = new MessageRequest();
        request.setMessageType(event.getMessageTypeEnum().getName());
        request.setPlatform(event.getMessageTypeEnum().getPlatform().name());
        request.setParam(event.getParam());
        request.setCreateTime(LocalDateTime.now());
        request.setRequestNo(event.getRequestNo());
        messageRequestService.log(request);
    }
}
