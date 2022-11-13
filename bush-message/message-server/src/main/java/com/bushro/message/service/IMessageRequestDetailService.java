package com.bushro.message.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bushro.message.entity.MessageRequestDetail;

/**
 * <p>
 * 消息发送结果详细 服务类
 * </p>
 *
 * @author bushro
 * @since 2022-10-09
 */
public interface IMessageRequestDetailService extends IService<MessageRequestDetail> {

    /**
     * 记录消息历史记录详情
     */
    void logDetail(MessageRequestDetail messageRequestDetail);

}
