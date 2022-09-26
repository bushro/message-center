package com.bushro.message.handle.servcie;

import com.lmax.disruptor.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import com.bushro.message.entity.MessageRequest;
import com.bushro.message.service.IMessageRequestService;

/**
 * @description: 插入请求参数
 * @author bushro
 * @date: 2021/10/28
 */
@Component
@Slf4j
public class MessageRequestHandler implements EventHandler<MessageRequest> {

    @Autowired
    @Lazy
    private IMessageRequestService requestService;


    @Override
    public void onEvent(MessageRequest event, long sequence, boolean endOfBatch) throws Exception {
        try {
            requestService.save(event);
        } catch (Exception e) {
            log.error("请求参数日志记录异常", e);
        }
    }
}
