package com.bushro.message.handle.wechat;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.bushro.message.base.BaseMessage;
import com.bushro.message.dto.wechat.agent.AgentCommonDTO;
import com.bushro.message.entity.MessageRequestDetail;
import com.bushro.message.enums.MessageTypeEnum;
import com.bushro.message.enums.SendStatusEnum;
import com.bushro.message.handle.IMessageHandler;
import com.bushro.message.properties.DingTalkCorpConfig;
import com.bushro.message.properties.WechatWorkAgentConfig;
import com.bushro.message.service.IMessageConfigService;
import com.bushro.message.service.IMessageRequestDetailService;
import com.bushro.message.utils.SingletonUtil;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.cp.api.impl.WxCpServiceImpl;
import me.chanjar.weixin.cp.bean.message.WxCpMessage;
import me.chanjar.weixin.cp.bean.message.WxCpMessageSendResult;
import me.chanjar.weixin.cp.config.impl.WxCpDefaultConfigImpl;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 企业微信应用抽象类
 *
 * @author luo.qiang
 * @date 2022/11/13
 */
@Slf4j
public abstract class AbstractWechatAgentHandler<T extends BaseMessage> implements IMessageHandler {

    /**
     * 参数
     */
    public AgentCommonDTO commonDTO;

    /**
     * 配置
     */
    public WechatWorkAgentConfig config;

    /**
     * 消息类型
     */
    public MessageTypeEnum messageTypeEnum;

    /**
     * 发送给所有人
     */
    public static final String ALL = "@all";

    public void checkAndSetUsers(AgentCommonDTO param) {
        if (ALL.equals(param.getToUser())) {
            return;
        }
        if (StrUtil.isEmpty(param.getToUser()) && StrUtil.isEmpty(param.getToParty())) {
            log.warn("请求号：{}，消息配置：{}。没有检测到接收用户", param.getRequestNo(), config.getConfigName());
        }
        return;
    }

    /**
     * 得到服务
     *
     * @return {@link WxCpServiceImpl}
     */
    public WxCpServiceImpl getService() {
        WxCpServiceImpl wxCpService = SingletonUtil.get(config.getCorpId() + config.getSecret() + config.getAgentId(), () -> {
            WxCpDefaultConfigImpl cpConfig = new WxCpDefaultConfigImpl();
            cpConfig.setCorpId(config.getCorpId());
            cpConfig.setCorpSecret(config.getSecret());
            cpConfig.setAgentId(config.getAgentId());
            WxCpServiceImpl service = new WxCpServiceImpl();
            service.setWxCpConfigStorage(cpConfig);
            return service;
        });
        return wxCpService;
    }


    /**
     * 执行
     *
     * @param param   参数
     * @param message 消息
     * @return {@link MessageRequestDetail}
     */
    public MessageRequestDetail execute(AgentCommonDTO param, WxCpMessage message) {
        MessageRequestDetail requestDetail = MessageRequestDetail.builder()
                .platform(messageTypeEnum.getPlatform().name())
                .messageType(messageTypeEnum.name())
                .receiverId(String.format("%s-%s", param.getToUser(), param.getToParty()))
                .requestNo(param.getRequestNo())
                .configId(config.getConfigId())
                .build();

        try {
            WxCpServiceImpl wxCpService = getService();
            WxCpMessageSendResult rsp = wxCpService.getMessageService().send(message);
            requestDetail.setSendStatus(SendStatusEnum.SEND_STATUS_SUCCESS.getCode());
            requestDetail.setMsgTest(SendStatusEnum.SEND_STATUS_SUCCESS.getDescription());
            log.info("{}发送消息响应数据:{}", messageTypeEnum.getName(), rsp.getErrMsg());
        } catch (Exception e) {
            log.error(messageTypeEnum.getName() + "发送消息失败", e);
            String eMessage = ExceptionUtil.getMessage(e);
            eMessage = StringUtils.isBlank(eMessage) ? "未知错误" : eMessage;
            requestDetail.setSendStatus(SendStatusEnum.SEND_STATUS_FAIL.getCode());
            requestDetail.setMsgTest(eMessage);
        }
        return requestDetail;
    }

    /**
     * 处理消息
     *
     * @param messageConfigService        消息配置服务
     * @param messageRequestDetailService 消息请求细节服务
     */
    public void handleMessage(IMessageConfigService messageConfigService, IMessageRequestDetailService messageRequestDetailService) {
        log.info("发送{}消息开始: 参数-{}-------------------------------", messageTypeEnum.getName(), JSONUtil.toJsonStr(commonDTO));
        List<WechatWorkAgentConfig> configs = messageConfigService.queryConfigOrDefault(commonDTO, WechatWorkAgentConfig.class);
        for (WechatWorkAgentConfig config : configs) {
            this.config = config;
            //校验用户
            this.checkAndSetUsers(commonDTO);
            WxCpMessage message = buildMsg();
            MessageRequestDetail requestDetail = this.execute(commonDTO, message);
            messageRequestDetailService.logDetail(requestDetail);
        }
        log.info("发送{}消息结束-------------------------------", messageType().getName());
    }

    /**
     * 构建消息
     *
     * @return {@link WxCpMessage}
     */
    protected abstract WxCpMessage buildMsg();

}
