package com.bushro.message.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bushro.message.entity.MessageConfigValue;
import com.bushro.message.mapper.MessageConfigValueMapper;
import com.bushro.message.service.IMessageConfigValueService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 消息配置值 服务实现类
 * </p>
 *
 * @author bushro
 * @since 2021-10-09
 */
@Service
public class MessageConfigValueServiceImpl extends ServiceImpl<MessageConfigValueMapper, MessageConfigValue> implements IMessageConfigValueService {

    @Resource
    private MessageConfigValueMapper configValueMapper;

    @Override
    public List<MessageConfigValue> listByConfidIds(List<Long> configIds) {
        return configValueMapper.listByConfidIds(configIds);
    }
}
