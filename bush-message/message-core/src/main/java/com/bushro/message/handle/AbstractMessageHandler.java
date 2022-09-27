package com.bushro.message.handle;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.json.JSONObject;
import com.bushro.message.consumer.ConsumerService;
import com.bushro.message.service.IMessageRequestService;
import com.bushro.message.utils.MessageHandlerUtils;
import com.lmax.disruptor.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bushro.message.base.BaseMessage;
import com.bushro.message.dto.MessagePushDTO;
import com.bushro.message.dto.TypeMessageDTO;
import com.bushro.message.entity.MessageRequest;
import com.bushro.message.enums.MessageTypeEnum;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Map;


/**
 * 抽象消息处理程序
 *
 * @author bushro
 * @date 2022/09/27
 */
public abstract class AbstractMessageHandler<T extends BaseMessage> implements EventHandler<MessagePushDTO> {


    private final static Logger LOGGER = LoggerFactory.getLogger(AbstractMessageHandler.class);

    @Resource
    private IMessageRequestService messageRequestService;


    /**
     * @descript :
     * @param event : 发布到RingBuffer
     * @param sequence : 正在处理的事件
     * @param endOfBatch : 指示如果这是从一个批次中的最后事件RingBuffer
     * @return : void
     */
    @SuppressWarnings("unchecked")
    @Override
    public void onEvent(MessagePushDTO event, long sequence, boolean endOfBatch) throws Exception {
        Map<MessageTypeEnum, TypeMessageDTO> messageParamMap = event.getMessageParam();
        if (!messageParamMap.containsKey(messageType())) {
            // 不处理
            return;
        }
        MessageTypeEnum messageType = messageType();
        TypeMessageDTO typeMessageDTO = messageParamMap.get(messageType);
        if (typeMessageDTO == null) {
            return;
        }
        try {
            // 处理参数
            JSONObject param = typeMessageDTO.getParam();
            //获取消息处理器的参数类型
            Class<?> actualTypeArgument = MessageHandlerUtils.getParamType(this);
            BaseMessage baseMessage = param == null ? (BaseMessage) ReflectUtil.newInstance(actualTypeArgument) : (BaseMessage) param.toBean(actualTypeArgument);
            baseMessage.setRequestNo(event.getRequestNo());
            baseMessage.setConfigIds(typeMessageDTO.getConfigIds());
            log(event);
            // 最后调用实际消息处理的方法
            handle((T) baseMessage);
        } catch (Exception e) {
            LOGGER.error("消息处理异常", e);
        }
    }

    /**
     * 所有消息处理器必须实现这个接口，标识自己处理的是哪个消息类型
     */
    public abstract MessageTypeEnum messageType();

    /**
     * 实现这个接口来处理消息，再正式调用这个方法之前会处理好需要的参数和需要的配置
     *
     * @param param 参数
     */
    public abstract void handle(T param);
    /**
     * 日志
     *
     * @param event 事件
     */
    public void log (MessagePushDTO event) {
        // 记录请求参数信息
        MessageRequest request = new MessageRequest();
        request.setMessageType(messageType().name());
        request.setPlatform(messageType().getPlatform().name());
        request.setParam(event.getParam());
        request.setCreateTime(LocalDateTime.now());
        request.setRequestNo(event.getRequestNo());
        messageRequestService.log(request);
    }

}
