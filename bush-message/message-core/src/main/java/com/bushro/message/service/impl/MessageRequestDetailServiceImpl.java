package com.bushro.message.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bushro.message.entity.MessageRequestDetail;
import com.bushro.message.mapper.MessageRequestDetailMapper;
import com.bushro.message.service.IMessageRequestDetailService;
import org.springframework.stereotype.Service;

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


    @Override
    public void logDetail(MessageRequestDetail messageRequestDetail) {
        this.save(messageRequestDetail);
    }

}
