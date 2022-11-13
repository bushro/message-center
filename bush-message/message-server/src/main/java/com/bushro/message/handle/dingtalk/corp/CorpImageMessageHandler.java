package com.bushro.message.handle.dingtalk.corp;

import com.bushro.message.dto.dingtalk.corp.ImageMessageDTO;
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
 * 钉钉工作通知图片消息处理器
 **/
@Component
@Slf4j
public class CorpImageMessageHandler extends AbstractDingHandler<ImageMessageDTO> implements IMessageHandler, Runnable {

    private ImageMessageDTO param;

    @Resource
    private IMessageConfigService messageConfigService;

    @Resource
    private IMessageRequestDetailService messageRequestDetailService;

    @Override
    public void setBaseMessage(Object object) {
        this.param = (ImageMessageDTO) object;
        this.commonDTO = this.param;
    }

    @Override
    public Runnable getRunnable() {
        return this;
    }

    @Override
    public MessageTypeEnum messageType() {
        this.messageTypeEnum = MessageTypeEnum.DING_TALK_COPR_IMAGE;
        return MessageTypeEnum.DING_TALK_COPR_IMAGE;
    }

    @Override
    public void run() {
        this.handleMessage(messageConfigService, messageRequestDetailService);
    }

    @Override
    protected OapiMessageCorpconversationAsyncsendV2Request.Msg buildMsg() {
        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();

        msg.setMsgtype(MsgTypeEnum.IMAGE.getValue());
        msg.setImage(new OapiMessageCorpconversationAsyncsendV2Request.Image());
        msg.getImage().setMediaId(param.getMediaId());
        return msg;
    }
}
