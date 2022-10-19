package com.bushro.message.consumer;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.json.JSONObject;
import com.bushro.message.base.BaseMessage;
import com.bushro.message.dto.MessagePushDTO;
import com.bushro.message.dto.TypeMessageDTO;
import com.bushro.message.enums.MessageTypeEnum;
import com.bushro.message.enums.MqTypeEnum;
import com.bushro.message.utils.MessageHandlerUtils;
import com.lmax.disruptor.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
        if (typeMessageDTO == null) {
            return;
        }
        handleMessage(event);
//        try {
//            // 处理参数
//            JSONObject param = typeMessageDTO.getParam();
//            //获取消息处理器的参数类型
//            Class<?> actualTypeArgument = MessageHandlerUtils.getParamType(this);
//            BaseMessage baseMessage = param == null ? (BaseMessage) ReflectUtil.newInstance(actualTypeArgument) : (BaseMessage) param.toBean(actualTypeArgument);
//            baseMessage.setRequestNo(event.getRequestNo());
//            baseMessage.setConfigIds(typeMessageDTO.getConfigIds());
//            // 最后调用实际消息处理的方法
//            handle((T) baseMessage);
//        } catch (Exception e) {
//            log.error("消息处理异常", e);
//        }
    }

    @Override
    public MqTypeEnum mqType() {
        return MqTypeEnum.MEMORY;
    }


    @Override
    public void handleMessage(MessagePushDTO messagePushDTO) {
        BaseMessage baseMessage = new BaseMessage();
        baseMessage.setRequestNo(messagePushDTO.getRequestNo());
        baseMessage.setConfigIds(messagePushDTO.getMessageDTO().getConfigIds());


    }
}
