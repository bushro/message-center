package com.bushro.message.handle.dingtalk.robot;


import com.bushro.message.dto.dingtalk.robot.TextMessageDTO;
import com.bushro.message.enums.MessageTypeEnum;
import com.bushro.message.handle.IMessageHandler;
import com.bushro.message.handle.dingtalk.AbstractDingRobotHandler;
import com.bushro.message.service.IMessageConfigService;
import com.bushro.message.service.IMessageRequestDetailService;
import com.dingtalk.api.request.OapiRobotSendRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 钉钉工作通知-文本类型消息处理器
 **/
@Component
@Slf4j
public class DingTalkRobotTextMessageHandler extends AbstractDingRobotHandler<TextMessageDTO> implements IMessageHandler, Runnable {


    private TextMessageDTO param;

    @Resource
    private IMessageConfigService messageConfigService;

    @Resource
    private IMessageRequestDetailService messageRequestDetailService;

    @Override
    public void setBaseMessage(Object object) {
        this.param = (TextMessageDTO) object;
        this.commonDTO = this.param;
    }

    @Override
    public Runnable getRunnable() {
        return this;
    }

    @Override
    public MessageTypeEnum messageType() {
        this.messageTypeEnum = MessageTypeEnum.DING_TALK_ROBOT_TEXT;
        return MessageTypeEnum.DING_TALK_ROBOT_TEXT;
    }

    @Override
    public void run() {
        this.handleMessage(messageConfigService, messageRequestDetailService);
    }


    @Override
    protected OapiRobotSendRequest buildRequest() {
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        request.setMsgtype("text");
        OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
        text.setContent(param.getContent());
        request.setText(text);
        this.setAt(request);
        return request;
    }
}
