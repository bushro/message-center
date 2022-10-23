package com.bushro.message.handle.wechat.agent;

import com.bushro.message.dto.wechat.agent.VideoMessageDTO;
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
 * 企业微信-视频消息
 *
 **/
@Component
public class AgentVideoMessageHandler  extends AbstractWechatAgentHandler<VideoMessageDTO> implements Runnable {

    @Resource
    private IMessageConfigService messageConfigService;

    @Resource
    private IMessageRequestDetailService messageRequestDetailService;

    private VideoMessageDTO param;

    @Override
    public void setBaseMessage(Object object) {
        this.param = (VideoMessageDTO) object;
    }

    @Override
    public Runnable getRunnable() {
        return this;
    }

    @Override
    public MessageTypeEnum messageType() {
        this.messageTypeEnum = MessageTypeEnum.WECHAT_WORK_AGENT_VIDEO;
        return MessageTypeEnum.WECHAT_WORK_AGENT_VIDEO;
    }


    @Override
    public void run() {
        List<WechatWorkAgentConfig> configs = messageConfigService.queryConfigOrDefault(param, WechatWorkAgentConfig.class);
        for (WechatWorkAgentConfig config : configs) {
            this.config = config;
            this.checkAndSetUsers(param);

            WxCpMessage message = WxCpMessage.VIDEO()
                    // 企业号应用ID
                    .agentId(config.getAgentId())
                    .toUser(param.getToUser())
                    .toParty(param.getToParty())
                    .toTag(param.getToTag())
                    .mediaId(param.getMediaId())
                    .description(param.getDescription())
                    .title(param.getTitle())
                    .build();

            MessageRequestDetail requestDetail = this.execute(param, message);
            messageRequestDetailService.logDetail(requestDetail);
        }
    }
}
