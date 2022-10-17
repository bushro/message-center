package com.bushro.message.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lmax.disruptor.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import com.bushro.message.entity.MessageRequest;
import com.bushro.message.factory.DisruptorFactory;
import com.bushro.message.handle.servcie.MessageRequestHandler;
import com.bushro.message.mapper.MessageRequestMapper;
import com.bushro.message.service.IMessageRequestService;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * <p>
 * 消息发送请求 服务实现类
 * </p>
 *
 * @author bushro
 * @since 2021-10-09
 */
@Service
@Slf4j
public class MessageRequestServiceImpl extends ServiceImpl<MessageRequestMapper, MessageRequest> implements IMessageRequestService {

    @Override
    public void log(MessageRequest messageRequest) {
        this.save(messageRequest);
    }
}
