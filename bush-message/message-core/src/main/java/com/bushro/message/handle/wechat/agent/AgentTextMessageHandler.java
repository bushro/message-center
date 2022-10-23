package com.bushro.message.handle.wechat.agent;

import com.bushro.message.dto.wechat.agent.TextMessageDTO;
import com.bushro.message.entity.MessageRequestDetail;
import com.bushro.message.enums.MessageTypeEnum;
import com.bushro.message.handle.wechat.AbstractWechatAgentHandler;
import com.bushro.message.properties.WechatWorkAgentConfig;
import com.bushro.message.service.IMessageConfigService;
import com.bushro.message.service.IMessageRequestDetailService;
import me.chanjar.weixin.cp.bean.message.WxCpMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 企业微信文本消息handler
 *
 **/
@Component
public class AgentTextMessageHandler extends AbstractWechatAgentHandler<TextMessageDTO> implements Runnable {

    @Resource
    private IMessageConfigService messageConfigService;

    @Resource
    private IMessageRequestDetailService messageRequestDetailService;

    private TextMessageDTO param;

    @Override
    public void setBaseMessage(Object object) {
        this.param = (TextMessageDTO) object;
    }

    @Override
    public Runnable getRunnable() {
        return this;
    }

    @Override
    public MessageTypeEnum messageType() {
        this.messageTypeEnum = MessageTypeEnum.WECHAT_WORK_AGENT_TEXT;
        return MessageTypeEnum.WECHAT_WORK_AGENT_TEXT;
    }

    @Override
    public void run() {
        List<WechatWorkAgentConfig> configs = messageConfigService.queryConfigOrDefault(param, WechatWorkAgentConfig.class);
        for (WechatWorkAgentConfig config : configs) {
            this.config = config;
            this.checkAndSetUsers(param);

            WxCpMessage message = WxCpMessage.TEXT()
                    .agentId(config.getAgentId())
                    .toUser(param.getToUser())
                    .content(param.getContent())
                    .toParty(param.getToParty())
                    .toTag(param.getToTag())
                    .build();

            MessageRequestDetail requestDetail = this.execute(param, message);
            messageRequestDetailService.logDetail(requestDetail);
        }
    }
}
