package com.bushro.message.handle.wechat;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.StrUtil;
import com.bushro.message.base.BaseMessage;
import com.bushro.message.dto.wechat.agent.AgentCommonDTO;
import com.bushro.message.entity.MessageRequestDetail;
import com.bushro.message.enums.MessageTypeEnum;
import com.bushro.message.enums.SendStatusEnum;
import com.bushro.message.handle.IMessageHandler;
import com.bushro.message.properties.WechatWorkAgentConfig;
import com.bushro.message.utils.SingletonUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.cp.api.impl.WxCpServiceImpl;
import me.chanjar.weixin.cp.bean.message.WxCpMessage;
import me.chanjar.weixin.cp.bean.message.WxCpMessageSendResult;
import me.chanjar.weixin.cp.config.impl.WxCpDefaultConfigImpl;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public abstract class AbstractWechatAgentHandler<T extends BaseMessage> implements IMessageHandler {

    /**
     * 参数
     */
    public AgentCommonDTO agentCommonDTO;

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
        this.agentCommonDTO = param;
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
            WxCpServiceImpl wxCpService1 = new WxCpServiceImpl();
            wxCpService1.setWxCpConfigStorage(cpConfig);
            return wxCpService1;
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
}
