package com.bushro.message.handle.dingtalk.corp;

import com.bushro.message.dto.dingtalk.corp.MarkdownMessageDTO;
import com.bushro.message.dto.dingtalk.corp.TextMessageDTO;
import com.bushro.message.enums.MessageTypeEnum;
import com.bushro.message.enums.MsgTypeEnum;
import com.bushro.message.handle.IMessageHandler;
import com.bushro.message.handle.dingtalk.AbstractDingHandler;
import com.bushro.message.service.IMessageConfigService;
import com.bushro.message.service.IMessageRequestDetailService;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 钉钉工作通知-markdown类型消息处理器
 *
 * @author luo.qiang
 * @date 2022/11/18
 */
@Component
@Slf4j
public class CorpMarkdownMessageHandler extends AbstractDingHandler<MarkdownMessageDTO> implements IMessageHandler, Runnable {


    private MarkdownMessageDTO param;

    @Resource
    private IMessageConfigService messageConfigService;

    @Resource
    private IMessageRequestDetailService messageRequestDetailService;

    @Override
    public void setBaseMessage(Object object) {
        this.param = (MarkdownMessageDTO) object;
        this.commonDTO = this.param;
    }

    @Override
    public Runnable getRunnable() {
        return this;
    }

    @Override
    public MessageTypeEnum messageType() {
        this.messageTypeEnum = MessageTypeEnum.DING_TALK_COPR_MARKDOWN;
        return MessageTypeEnum.DING_TALK_COPR_MARKDOWN;
    }

    @Override
    public void run() {
        this.handleMessage(messageConfigService, messageRequestDetailService);
    }

    @Override
    protected OapiMessageCorpconversationAsyncsendV2Request.Msg buildMsg() {
        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setMsgtype(MsgTypeEnum.MARKDOWN.getValue());
        msg.setMarkdown(new OapiMessageCorpconversationAsyncsendV2Request.Markdown());
        msg.getMarkdown().setTitle(param.getTitle());
        msg.getMarkdown().setText(param.getText());
        return msg;
    }
}
