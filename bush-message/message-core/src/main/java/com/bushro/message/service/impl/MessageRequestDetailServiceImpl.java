package com.bushro.message.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lmax.disruptor.EventHandler;
import org.springframework.stereotype.Service;
import com.bushro.message.entity.MessageRequestDetail;
import com.bushro.message.factory.DisruptorFactory;
import com.bushro.message.handle.servcie.MessageRequestDetailHandler;
import com.bushro.message.mapper.MessageRequestDetailMapper;
import com.bushro.message.service.IMessageRequestDetailService;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * <p>
 * 消息发送结果详细 服务实现类
 * </p>
 *
 * @author bushro
 * @since 2021-10-09
 */
@Service
public class MessageRequestDetailServiceImpl extends ServiceImpl<MessageRequestDetailMapper, MessageRequestDetail> implements IMessageRequestDetailService {

    @Resource
    private MessageRequestDetailHandler messageRequestDetailHandler;

    @Override
    public void logDetail(MessageRequestDetail messageRequestDetail) {

        DisruptorFactory<MessageRequestDetail> disruptorFactory = new DisruptorFactory<>();
        disruptorFactory.push(messageRequestDetail, MessageRequestDetail::new,
            Arrays.asList(messageRequestDetailHandler).toArray(new EventHandler[0]));
    }

}
