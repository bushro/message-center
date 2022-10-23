package com.bushro.message.handle.dingtalk.corp;

import cn.hutool.json.JSONUtil;
import com.bushro.message.dto.dingtalk.corp.ImageMessageDTO;
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
 * 钉钉工作通知图片消息处理器
 **/
@Component
@Slf4j
public class CorpImageMessageHandler extends AbstractDingHandler<ImageMessageDTO> implements IMessageHandler, Runnable {

    private ImageMessageDTO param;

    @Resource
    private IMessageConfigService messageConfigService;

    @Resource
    private IMessageRequestDetailService messageRequestDetailService;

    @Override
    public void setBaseMessage(Object object) {
        this.param = (ImageMessageDTO) object;
    }

    @Override
    public Runnable getRunnable() {
        return this;
    }

    @Override
    public MessageTypeEnum messageType() {
        this.messageTypeEnum = MessageTypeEnum.DING_TALK_COPR_IMAGE;
        return MessageTypeEnum.DING_TALK_COPR_IMAGE;
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

            msg.setMsgtype(MsgTypeEnum.IMAGE.getValue());
            msg.setImage(new OapiMessageCorpconversationAsyncsendV2Request.Image());
            msg.getImage().setMediaId(param.getMediaId());

            request.setMsg(msg);
            MessageRequestDetail requestDetail = this.execute(param, request);
            //记录发送情况
            messageRequestDetailService.logDetail(requestDetail);
        }
        log.info("发送{}消息结束-------------------------------", messageType().getName());
    }
}
