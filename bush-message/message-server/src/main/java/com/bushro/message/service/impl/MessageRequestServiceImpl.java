package com.bushro.message.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bushro.message.entity.MessageRequest;
import com.bushro.message.mapper.MessageRequestMapper;
import com.bushro.message.service.IMessageRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * <p>
 * 消息发送请求 服务实现类
 * </p>
 *
 * @author bushro
 * @since 2022-10-09
 */
@Service
@Slf4j
public class MessageRequestServiceImpl extends ServiceImpl<MessageRequestMapper, MessageRequest> implements IMessageRequestService {

    @Override
    public void log(MessageRequest messageRequest) {
        this.save(messageRequest);
    }
}
