package com.bushro.message.handle.dingtalk.robot;


import com.bushro.message.dto.dingtalk.robot.LinkMessageDTO;
import com.bushro.message.enums.MessageTypeEnum;
import com.bushro.message.handle.IMessageHandler;
import com.bushro.message.handle.dingtalk.AbstractDingRobotHandler;
import com.bushro.message.service.IMessageConfigService;
import com.bushro.message.service.IMessageRequestDetailService;
import com.dingtalk.api.request.OapiRobotSendRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 钉钉工作通知-链接类型消息处理器
 **/
@Component
@Slf4j
public class DingTalkRobotLinkMessageHandler extends AbstractDingRobotHandler<LinkMessageDTO> implements IMessageHandler, Runnable {


    private LinkMessageDTO param;

    @Resource
    private IMessageConfigService messageConfigService;

    @Resource
    private IMessageRequestDetailService messageRequestDetailService;

    @Override
    public void setBaseMessage(Object object) {
        this.param = (LinkMessageDTO) object;
        this.commonDTO = this.param;
    }

    @Override
    public Runnable getRunnable() {
        return this;
    }

    @Override
    public MessageTypeEnum messageType() {
        this.messageTypeEnum = MessageTypeEnum.DING_TALK_ROBOT_LINK;
        return MessageTypeEnum.DING_TALK_ROBOT_LINK;
    }

    @Override
    public void run() {
        this.handleMessage(messageConfigService, messageRequestDetailService);
    }


    @Override
    protected OapiRobotSendRequest buildRequest() {
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        request.setMsgtype("link");
        OapiRobotSendRequest.Link link = new OapiRobotSendRequest.Link();
        link.setMessageUrl(param.getMessageUrl());
        link.setPicUrl(param.getPicUrl());
        link.setTitle(param.getTitle());
        link.setText(param.getText());
        request.setLink(link);
        this.setAt(request);
        return request;
    }
}
