package com.bushro.message.handle.wechat.officialaccount;

import cn.hutool.core.exceptions.ExceptionUtil;

import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.bushro.message.dto.wechat.officialaccount.TextMessageDTO;
import com.bushro.message.entity.MessageRequestDetail;
import com.bushro.message.enums.MessageTypeEnum;
import com.bushro.message.enums.SendStatusEnum;
import com.bushro.message.handle.AbstractMessageHandler;
import com.bushro.message.properties.WechatOfficialAccountConfig;
import com.bushro.message.service.IMessageConfigService;
import com.bushro.message.service.IMessageRequestDetailService;
import com.bushro.message.utils.SingletonUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 微信公众号文本消息handler
 *
 **/
@Component
public class MpTextMessageHandler extends AbstractMessageHandler<TextMessageDTO> {

    private final static Logger LOGGER = LoggerFactory.getLogger(MpTextMessageHandler.class);

    @Autowired
    private IMessageConfigService messageConfigService;

    @Autowired
    private IMessageRequestDetailService messageRequestDetailService;

    @Override
    public MessageTypeEnum messageType() {
        return MessageTypeEnum.WECHAT_OFFICIAL_ACCOUNT_TEXT;
    }

    @Override
    public void handle(TextMessageDTO param) {
        List<WechatOfficialAccountConfig> configs = messageConfigService.queryConfigOrDefault(param, WechatOfficialAccountConfig.class);
        for (WechatOfficialAccountConfig config : configs) {
            Set<String> receiverUsers = new HashSet<>();
            if (param.getReceiverIds() != null) {
                receiverUsers.addAll(param.getReceiverIds());
            }

            if (receiverUsers.size() <= 0) {
                LOGGER.warn("请求号：{}，消息配置：{}。没有检测到接收用户", param.getRequestNo(), config.getConfigName());
                return;
            }

            WxMpService wxService = SingletonUtil.get(config.getAppId() + config.getSecret(), () -> {
                WxMpDefaultConfigImpl mpConfig = new WxMpDefaultConfigImpl();
                mpConfig.setAppId(config.getAppId());
                mpConfig.setSecret(config.getSecret());
                WxMpService wxService1 = new WxMpServiceImpl();
                wxService1.setWxMpConfigStorage(mpConfig);
                return wxService1;
            });

            for (String receiverUser : receiverUsers) {
                WxMpKefuMessage message = WxMpKefuMessage.TEXT()
                    .toUser(receiverUser)
                    .content(param.getContent()).build();

                MessageRequestDetail requestDetail = MessageRequestDetail.builder()
                    .platform(messageType().getPlatform().name())
                    .messageType(messageType().name())
                    .receiverId(receiverUser)
                    .requestNo(param.getRequestNo())
                    .configId(config.getConfigId())
                    .build();

                try {
                    wxService.getKefuService().sendKefuMessage(message);
                    requestDetail.setSendStatus(SendStatusEnum.SEND_STATUS_SUCCESS.getCode());
                } catch (Exception e) {
                    LOGGER.error("微信公众号发送文本信息失败",e);
                    String eMessage = ExceptionUtil.getMessage(e);
                    eMessage = StringUtils.isBlank(eMessage) ? "未知错误" : eMessage;
                    requestDetail.setSendStatus(SendStatusEnum.SEND_STATUS_FAIL.getCode());
                    requestDetail.setMsgTest(eMessage);
                }
                messageRequestDetailService.logDetail(requestDetail);
            }
        }
    }
}
