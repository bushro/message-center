package com.bushro.message.handle.dingtalk.corp;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import com.bushro.message.handle.MessageHandler;
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
import com.bushro.message.dto.dingtalk.corp.ImageMessageDTO;
import com.bushro.message.dto.dingtalk.corp.LinkMessageDTO;
import com.bushro.message.entity.MessageRequestDetail;
import com.bushro.message.enums.MessageTypeEnum;
import com.bushro.message.enums.MsgTypeEnum;
import com.bushro.message.enums.SendStatusEnum;
import com.bushro.message.properties.DingTalkCorpConfig;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 钉钉工作通知图片消息处理器
 *
 **/
@Component
public class CorpImageMessageHandler extends MessageHandler<ImageMessageDTO> {

    private final static Logger LOGGER = LoggerFactory.getLogger(CorpImageMessageHandler.class);

    @Autowired
    private IMessageConfigService messageConfigService;

    @Autowired
    private IMessageRequestDetailService messageRequestDetailService;

    @Override
    public MessageTypeEnum messageType() {
        return MessageTypeEnum.DING_TALK_COPR_IMAGE;
    }

    @Override
    public void handle(ImageMessageDTO param) {
        List<DingTalkCorpConfig> configs = messageConfigService.queryConfigOrDefault(param, DingTalkCorpConfig.class);
        for (DingTalkCorpConfig config : configs) {
            Set<String> receiverUsers = new HashSet<>();
            if (CollUtil.isNotEmpty(param.getReceiverIds())) {
                receiverUsers.addAll(param.getReceiverIds());
            }

            if (receiverUsers.size() <= 0) {
                LOGGER.warn("请求号：{}，消息配置：{}。没有检测到接收用户", param.getRequestNo(), config.getConfigName());
                return;
            }

            DingTalkClient client = SingletonUtil.get("dinging-" + config.getAppKey() + config.getAppSecret(),
                    (SingletonUtil.Factory<DingTalkClient>) () -> new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2"));
            OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
            request.setAgentId(Long.valueOf(config.getAgentId()));
            request.setUseridList(String.join(",", receiverUsers));
            request.setDeptIdList(param.getDeptIdList());
            request.setToAllUser(param.isToAllUser());

            OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
            msg.setMsgtype(MsgTypeEnum.IMAGE.getValue());
            msg.setImage(new OapiMessageCorpconversationAsyncsendV2Request.Image());
            msg.getImage().setMediaId(param.getMediaId());
            request.setMsg(msg);

            MessageRequestDetail requestDetail = MessageRequestDetail.builder()
                .platform(messageType().getPlatform().name())
                .messageType(messageType().name())
                .receiverId(param.isToAllUser() ? "所有人" : request.getUseridList())
                .requestNo(param.getRequestNo())
                .configId(config.getConfigId())
                .build();
            try {
                OapiMessageCorpconversationAsyncsendV2Response rsp = client.execute(request, AccessTokenUtils.getAccessToken(config.getAppKey(), config.getAppSecret()));
                if (!rsp.isSuccess()) {
                    throw new IllegalStateException(rsp.getBody());
                }
                requestDetail.setSendStatus(SendStatusEnum.SEND_STATUS_SUCCESS.getCode());
                requestDetail.setMsgTest(SendStatusEnum.SEND_STATUS_SUCCESS.getDescription());
                LOGGER.info("钉钉图片发送消息响应数据:{}",rsp.getBody());
            } catch (Exception e) {
                LOGGER.error("钉钉图片发送消息失败",e);
                String eMessage = ExceptionUtil.getMessage(e);
                eMessage = StringUtils.isBlank(eMessage) ? "未知错误" : eMessage;
                requestDetail.setSendStatus(SendStatusEnum.SEND_STATUS_FAIL.getCode());
                requestDetail.setMsgTest(eMessage);
            }
            messageRequestDetailService.logDetail(requestDetail);
        }
    }
}
