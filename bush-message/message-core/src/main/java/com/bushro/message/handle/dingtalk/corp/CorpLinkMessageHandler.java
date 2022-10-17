package com.bushro.message.handle.dingtalk.corp;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import com.bushro.message.dto.dingtalk.corp.LinkMessageDTO;
import com.bushro.message.entity.MessageRequestDetail;
import com.bushro.message.enums.MessageTypeEnum;
import com.bushro.message.enums.MsgTypeEnum;
import com.bushro.message.enums.SendStatusEnum;
import com.bushro.message.handle.AbstractMessageHandler;
import com.bushro.message.handle.IMessageHandler;
import com.bushro.message.handle.dingtalk.AbstractDingHandler;
import com.bushro.message.properties.DingTalkCorpConfig;
import com.bushro.message.service.IMessageConfigService;
import com.bushro.message.service.IMessageRequestDetailService;
import com.bushro.message.utils.AccessTokenUtils;
import com.bushro.message.utils.SingletonUtil;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 钉钉工作通知文本类型消息处理器
 **/
@Component
public class CorpLinkMessageHandler extends AbstractDingHandler<LinkMessageDTO> implements IMessageHandler, Runnable {

    private final static Logger LOGGER = LoggerFactory.getLogger(CorpLinkMessageHandler.class);

    private LinkMessageDTO param;

    @Resource
    private IMessageConfigService messageConfigService;

    @Resource
    private IMessageRequestDetailService messageRequestDetailService;

    @Override
    public MessageTypeEnum messageType() {
        return MessageTypeEnum.DING_TALK_COPR_LINK;
    }

    @Override
    public void run() {
        List<DingTalkCorpConfig> configs = messageConfigService.queryConfigOrDefault(param, DingTalkCorpConfig.class);
        for (DingTalkCorpConfig config : configs) {
            this.config = config;
            this.setReceiverUsers(param);
            this.getClient(config);
            OapiMessageCorpconversationAsyncsendV2Request request = this.getRequest(param);
            OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
            msg.setMsgtype(MsgTypeEnum.LINK.getValue());
            msg.setLink(new OapiMessageCorpconversationAsyncsendV2Request.Link());
            msg.getLink().setTitle(param.getTitle());
            msg.getLink().setText(param.getText());
            msg.getLink().setMessageUrl(param.getMessageUrl());
            msg.getLink().setPicUrl(param.getPicUrl());
            request.setMsg(msg);
            this.execute(param, request);
            //messageRequestDetailService.logDetail(requestDetail);
        }
    }
}
