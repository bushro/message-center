package com.bushro.message.consumer;

import com.bushro.message.base.BaseMessage;
import com.bushro.message.dto.MessagePushDTO;
import com.bushro.message.handle.AbstractMessageHandler;
import com.lmax.disruptor.EventHandler;
import org.springframework.stereotype.Service;


/**
 * Disruptor消费者服务
 *
 * @author bushro
 * @date 2022/09/27
 */
@Service
public class DisruptorConsumerService implements EventHandler<MessagePushDTO> {


    @Override
    public void onEvent(MessagePushDTO event, long sequence, boolean endOfBatch) throws Exception {

    }
}
