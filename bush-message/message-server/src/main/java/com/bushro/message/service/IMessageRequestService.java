package com.bushro.message.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bushro.message.entity.MessageRequest;
import com.bushro.message.entity.MessageRequestDetail;

/**
 * <p>
 * 消息发送请求 服务类
 * </p>
 *
 * @author bushro
 * @since 2022-10-09
 */
public interface IMessageRequestService extends IService<MessageRequest> {

    /**
     * 记录消息历史记录
     */
    void log(MessageRequest messageRequest);

}
