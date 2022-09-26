package com.bushro.message.handle.servcie;

import com.lmax.disruptor.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.bushro.message.entity.MessageRequest;
import com.bushro.message.entity.MessageRequestDetail;
import com.bushro.message.service.IMessageRequestDetailService;

/**
 * @description: 插入请求结果
 * @author bushro
 * @date: 2021/10/28
 */
@Component
@Slf4j
public class MessageRequestDetailHandler implements EventHandler<MessageRequestDetail> {

    @Autowired
    private IMessageRequestDetailService detailService;


    @Override
    public void onEvent(MessageRequestDetail event, long sequence, boolean endOfBatch) throws Exception {
        try {
            detailService.save(event);
        } catch (Exception e) {
            log.error("详细发送结果日志记录异常", e);
        }
    }
}
