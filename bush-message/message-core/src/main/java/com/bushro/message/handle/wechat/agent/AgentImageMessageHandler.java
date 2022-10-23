package com.bushro.message.handle.wechat.agent;

import com.bushro.message.dto.wechat.agent.MediaMessageDTO;
import com.bushro.message.entity.MessageRequestDetail;
import com.bushro.message.enums.MessageTypeEnum;
import com.bushro.message.handle.wechat.AbstractWechatAgentHandler;
import com.bushro.message.properties.WechatWorkAgentConfig;
import com.bushro.message.service.IMessageConfigService;
import com.bushro.message.service.IMessageRequestDetailService;
import me.chanjar.weixin.cp.bean.message.WxCpMessage;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 企业微信-图片消息
 *
 **/
@Component
public class AgentImageMessageHandler extends AbstractWechatAgentHandler<MediaMessageDTO> implements Runnable {

    @Resource
    private IMessageConfigService messageConfigService;

    @Resource
    private IMessageRequestDetailService messageRequestDetailService;

    private MediaMessageDTO param;

    @Override
    public void setBaseMessage(Object object) {
        this.param = (MediaMessageDTO) object;
    }

    @Override
    public Runnable getRunnable() {
        return this;
    }

    @Override
    public MessageTypeEnum messageType() {
        this.messageTypeEnum = MessageTypeEnum.WECHAT_WORK_AGENT_IMAGE;
        return MessageTypeEnum.WECHAT_WORK_AGENT_IMAGE;
    }


    @Override
    public void run() {
        List<WechatWorkAgentConfig> configs = messageConfigService.queryConfigOrDefault(param, WechatWorkAgentConfig.class);
        for (WechatWorkAgentConfig config : configs) {
            this.config = config;
            this.checkAndSetUsers(param);
            WxCpMessage message = WxCpMessage.IMAGE()
                    // 企业号应用ID
                    .agentId(config.getAgentId())
                    .toUser(param.getToUser())
                    .toParty(param.getToParty())
                    .toTag(param.getToTag())
                    .mediaId(param.getMediaId())
                    .build();

            MessageRequestDetail requestDetail = this.execute(param, message);
            messageRequestDetailService.logDetail(requestDetail);
        }
    }

}
