package com.bushro.message.handle.dingtalk.corp;

import cn.hutool.json.JSONUtil;
import com.bushro.message.dto.dingtalk.corp.ActionCardSingleMessageDTODing;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 钉钉工作通知-整体跳转ActionCard样式，支持一个点击Action
 *
 **/
@Component
@Slf4j
public class CorpActionCardSingleMessageHandler extends AbstractDingHandler implements IMessageHandler<ActionCardSingleMessageDTODing>, Runnable {

    private final static Logger LOGGER = LoggerFactory.getLogger(CorpActionCardSingleMessageHandler.class);

    private ActionCardSingleMessageDTODing param;

    @Autowired
    private IMessageConfigService messageConfigService;

    @Autowired
    private IMessageRequestDetailService messageRequestDetailService;

    @Override
    public MessageTypeEnum messageType() {
        return MessageTypeEnum.DING_TALK_COPR_ACTION_CARD_SINGLE;
    }

    @Override
    public void setBaseMessage(ActionCardSingleMessageDTODing actionCardSingleMessageDTO) {
        this.param = actionCardSingleMessageDTO;
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

            msg.setActionCard(new OapiMessageCorpconversationAsyncsendV2Request.ActionCard());
            msg.getActionCard().setTitle(param.getTitle());
            msg.getActionCard().setMarkdown(param.getMarkdown());
            msg.getActionCard().setSingleTitle(param.getSingleTitle());
            msg.getActionCard().setSingleUrl(param.getSingleUrl());
            msg.setMsgtype(MsgTypeEnum.ACTION_CARD.getValue());
            request.setMsg(msg);

            MessageRequestDetail requestDetail = this.execute(param, request);
            //记录发送情况
            messageRequestDetailService.logDetail(requestDetail);
        }
        log.info("发送{}消息结束-------------------------------", messageType().getName());
    }
}
