package com.bushro.message.handle.wechat.agent;

import cn.hutool.core.exceptions.ExceptionUtil;
import me.chanjar.weixin.cp.api.impl.WxCpServiceImpl;
import me.chanjar.weixin.cp.bean.message.WxCpMessage;
import me.chanjar.weixin.cp.bean.message.WxCpMessageSendResult;
import me.chanjar.weixin.cp.config.impl.WxCpDefaultConfigImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.bushro.message.dto.wechat.agent.VideoMessageDTO;
import com.bushro.message.entity.MessageRequestDetail;
import com.bushro.message.enums.MessageTypeEnum;
import com.bushro.message.enums.SendStatusEnum;
import com.bushro.message.handle.MessageHandler;
import com.bushro.message.properties.WechatWorkAgentConfig;
import com.bushro.message.service.IMessageConfigService;
import com.bushro.message.service.IMessageRequestDetailService;
import com.bushro.message.utils.SingletonUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 企业微信-视频消息
 *
 **/
@Component
public class AgentVideoMessageHandler extends MessageHandler<VideoMessageDTO> {

    private final static Logger LOGGER = LoggerFactory.getLogger(AgentVideoMessageHandler.class);

    @Autowired
    private IMessageConfigService messageConfigService;

    @Autowired
    private IMessageRequestDetailService messageRequestDetailService;

    @Override
    public MessageTypeEnum messageType() {
        return MessageTypeEnum.WECHAT_WORK_AGENT_VIDEO;
    }

    @Override
    public void handle(VideoMessageDTO param) {
        List<WechatWorkAgentConfig> configs = messageConfigService.queryConfigOrDefault(param, WechatWorkAgentConfig.class);
        for (WechatWorkAgentConfig config : configs) {
            Set<String> receiverUsers = new HashSet<>();
            if (param.getReceiverIds() != null) {
                receiverUsers.addAll(param.getReceiverIds());
            }

            if (receiverUsers.size() <= 0 && StringUtils.isEmpty(param.getToParty())
                && StringUtils.isEmpty(param.getToTag())) {
                LOGGER.warn("请求号：{}，消息配置：{}。没有检测到接收用户", param.getRequestNo(), config.getConfigName());
                return;
            }

            WxCpServiceImpl wxCpService = SingletonUtil.get(config.getCorpId() + config.getSecret() + config.getAgentId(), () -> {
                WxCpDefaultConfigImpl cpConfig = new WxCpDefaultConfigImpl();
                cpConfig.setCorpId(config.getCorpId());
                cpConfig.setCorpSecret(config.getSecret());
                cpConfig.setAgentId(config.getAgentId());
                WxCpServiceImpl wxCpService1 = new WxCpServiceImpl();
                wxCpService1.setWxCpConfigStorage(cpConfig);
                return wxCpService1;
            });
            MessageRequestDetail requestDetail = MessageRequestDetail.builder()
                .platform(messageType().getPlatform().name())
                .messageType(messageType().name())
                .receiverId(String.join(",", receiverUsers))
                .requestNo(param.getRequestNo())
                .configId(config.getConfigId())
                .build();

            WxCpMessage message = WxCpMessage.VIDEO()
                // 企业号应用ID
                .agentId(config.getAgentId())
                .toUser(String.join("|", receiverUsers))
                .toParty(param.getToParty())
                .toTag(param.getToTag())
                .mediaId(param.getMediaId())
                .description(param.getDescription())
                .title(param.getTitle())
                .build();
            try {
                WxCpMessageSendResult rsp = wxCpService.getMessageService().send(message);
                requestDetail.setSendStatus(SendStatusEnum.SEND_STATUS_SUCCESS.getCode());
                requestDetail.setMsgTest(SendStatusEnum.SEND_STATUS_SUCCESS.getDescription());
                LOGGER.info("企业微信发送视频消息响应数据:{}",rsp.getErrMsg());
            } catch (Exception e) {
                LOGGER.error("企业微信发送视频失败",e);
                String eMessage = ExceptionUtil.getMessage(e);
                eMessage = StringUtils.isBlank(eMessage) ? "未知错误" : eMessage;
                requestDetail.setSendStatus(SendStatusEnum.SEND_STATUS_FAIL.getCode());
                requestDetail.setMsgTest(eMessage);
            }
            messageRequestDetailService.logDetail(requestDetail);
        }
    }
}
