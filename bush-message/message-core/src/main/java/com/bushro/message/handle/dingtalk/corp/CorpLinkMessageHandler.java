package com.bushro.message.handle.dingtalk.corp;

import cn.hutool.json.JSONUtil;
import com.bushro.message.dto.dingtalk.corp.LinkMessageDTO;
import com.bushro.message.entity.MessageRequestDetail;
import com.bushro.message.enums.MessageTypeEnum;
import com.bushro.message.enums.MsgTypeEnum;
import com.bushro.message.handle.IMessageHandler;
import com.bushro.message.handle.dingtalk.AbstractDingHandler;
import com.bushro.message.properties.DingTalkCorpConfig;
import com.bushro.message.service.IMessageConfigService;
import com.bushro.message.service.IMessageRequestDetailService;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 钉钉工作通知文本类型消息处理器
 **/
@Component
@Slf4j
public class CorpLinkMessageHandler extends AbstractDingHandler<LinkMessageDTO> implements IMessageHandler, Runnable {

    private LinkMessageDTO param;

    @Resource
    private IMessageConfigService messageConfigService;

    @Resource
    private IMessageRequestDetailService messageRequestDetailService;

    @Override
    public MessageTypeEnum messageType() {
        this.messageTypeEnum = MessageTypeEnum.DING_TALK_COPR_LINK;
        return MessageTypeEnum.DING_TALK_COPR_LINK;
    }

    @Override
    public Runnable getRunnable() {
        return this;
    }

    @Override
    public void setBaseMessage(Object object) {
        this.param = (LinkMessageDTO) object;
    }

    @Override
    public void run() {
        log.info("发送{}消息开始: 参数-{}-------------------------------", messageType().getName(), JSONUtil.toJsonStr(param));
        List<DingTalkCorpConfig> configs = messageConfigService.queryConfigOrDefault(param, DingTalkCorpConfig.class);
        for (DingTalkCorpConfig config : configs) {
            this.config = config;
            this.setReceiverUsers(param);
            OapiMessageCorpconversationAsyncsendV2Request request = this.getRequest(param);
            OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
            msg.setMsgtype(MsgTypeEnum.LINK.getValue());
            msg.setLink(new OapiMessageCorpconversationAsyncsendV2Request.Link());
            msg.getLink().setTitle(param.getTitle());
            msg.getLink().setText(param.getText());
            msg.getLink().setMessageUrl(param.getMessageUrl());
            msg.getLink().setPicUrl(param.getPicUrl());
            request.setMsg(msg);
            MessageRequestDetail requestDetail = this.execute(param, request);
            //记录发送情况
            messageRequestDetailService.logDetail(requestDetail);
        }
        log.info("发送{}消息结束-------------------------------", messageType().getName());
    }

}
