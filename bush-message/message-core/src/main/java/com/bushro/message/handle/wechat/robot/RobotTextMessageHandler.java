package com.bushro.message.handle.wechat.robot;

import com.bushro.message.dto.wechat.robot.TextMessageDTO;
import com.bushro.message.enums.MessageTypeEnum;
import com.bushro.message.handle.wechat.AbstractWechatRobotHandler;
import com.bushro.message.service.IMessageConfigService;
import com.bushro.message.service.IMessageRequestDetailService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpGroupRobotService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 企业微信-群机器人-文本消息
 *
 * @author luo.qiang
 * @date 2022/10/27
 */
@Slf4j
@Component
public class RobotTextMessageHandler extends AbstractWechatRobotHandler<TextMessageDTO> implements Runnable {

    private TextMessageDTO param;

    @Resource
    private IMessageConfigService messageConfigService;

    @Resource
    private IMessageRequestDetailService messageRequestDetailService;

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
        this.messageTypeEnum = MessageTypeEnum.WECHAT_WORK_ROBOT_TEXT;
        return MessageTypeEnum.WECHAT_WORK_ROBOT_TEXT;
    }

    @Override
    public void run() {
        handle(messageConfigService, messageRequestDetailService, param);
    }

    @Override
    public void sendMessage() throws WxErrorException {
        WxCpGroupRobotService robotService = this.getService().getGroupRobotService();
        robotService.sendText(param.getContent(), param.getMentionedList(), param.getMentionedMobileList());
    }
}
