package com.bushro.message.handle.wechat.agent;

import com.bushro.message.dto.wechat.agent.TextCardMessageDTO;
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
 * 文本卡片消息
 *
 **/
@Component
public class AgentTextCardMessageHandler extends AbstractWechatAgentHandler<TextCardMessageDTO> implements Runnable {

    @Resource
    private IMessageConfigService messageConfigService;

    @Resource
    private IMessageRequestDetailService messageRequestDetailService;

    private TextCardMessageDTO param;

    @Override
    public void setBaseMessage(Object object) {
        this.param = (TextCardMessageDTO) object;
        this.commonDTO = this.param;
    }

    @Override
    public Runnable getRunnable() {
        return this;
    }

    @Override
    public MessageTypeEnum messageType() {
        this.messageTypeEnum = MessageTypeEnum.WECHAT_WORK_AGENT_TEXTCARD;
        return MessageTypeEnum.WECHAT_WORK_AGENT_TEXTCARD;
    }


    @Override
    public void run() {
        this.handleMessage(messageConfigService, messageRequestDetailService);
    }

    @Override
    protected WxCpMessage buildMsg() {
        WxCpMessage message = WxCpMessage.TEXTCARD()
                // 企业号应用ID
                .agentId(config.getAgentId())
                .toUser(param.getToUser())
                .toParty(param.getToParty())
                .toTag(param.getToTag())
                .title(param.getTitle())
                .description(param.getDescription())
                .url(param.getUrl())
                .btnTxt(param.getBtntxt())
                .build();
        return message;
    }
}
