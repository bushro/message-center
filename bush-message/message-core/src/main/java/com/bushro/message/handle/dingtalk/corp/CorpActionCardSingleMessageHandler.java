package com.bushro.message.handle.dingtalk.corp;

import cn.hutool.json.JSONUtil;
import com.bushro.message.dto.dingtalk.corp.ActionCardSingleMessageDTO;
import com.bushro.message.entity.MessageRequestDetail;
import com.bushro.message.enums.MessageTypeEnum;
import com.bushro.message.enums.MsgTypeEnum;
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
 * 钉钉工作通知-整体跳转ActionCard样式，支持一个点击Action
 **/
@Component
@Slf4j
public class CorpActionCardSingleMessageHandler extends AbstractDingHandler<ActionCardSingleMessageDTO> implements Runnable {

    private ActionCardSingleMessageDTO param;

    @Resource
    private IMessageConfigService messageConfigService;

    @Resource
    private IMessageRequestDetailService messageRequestDetailService;

    @Override
    public MessageTypeEnum messageType() {
        this.messageTypeEnum = MessageTypeEnum.DING_TALK_COPR_ACTION_CARD_SINGLE;
        return MessageTypeEnum.DING_TALK_COPR_ACTION_CARD_SINGLE;
    }


    @Override
    public void setBaseMessage(Object object) {
        this.param = (ActionCardSingleMessageDTO) object;
    }

    @Override
    public Runnable getRunnable() {
        return this;
    }

    @Override
    public void run() {
        log.info("发送{}消息开始: 参数-{}-------------------------------", messageType().getName(), JSONUtil.toJsonStr(param));
        List<DingTalkCorpConfig> configs = messageConfigService.queryConfigOrDefault(param, DingTalkCorpConfig.class);
        for (DingTalkCorpConfig config : configs) {
            this.config = config;
            this.checkAndSetUsers(param);
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
