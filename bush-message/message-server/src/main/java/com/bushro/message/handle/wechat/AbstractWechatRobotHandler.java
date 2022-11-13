package com.bushro.message.handle.wechat;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.json.JSONUtil;
import com.bushro.message.base.BaseMessage;
import com.bushro.message.dto.wechat.robot.RobotCommonDTO;
import com.bushro.message.entity.MessageRequestDetail;
import com.bushro.message.enums.MessageTypeEnum;
import com.bushro.message.enums.SendStatusEnum;
import com.bushro.message.handle.IMessageHandler;
import com.bushro.message.properties.WechatWorkRobotConfig;
import com.bushro.message.service.IMessageConfigService;
import com.bushro.message.service.IMessageRequestDetailService;
import com.bushro.message.utils.SingletonUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.api.impl.WxCpServiceImpl;
import me.chanjar.weixin.cp.config.impl.WxCpDefaultConfigImpl;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 企业微信-群聊机器人抽象类
 *
 * @author luo.qiang
 * @date 2022/11/13
 */
@Slf4j
public abstract class AbstractWechatRobotHandler<T extends BaseMessage> implements IMessageHandler {

    /**
     * 配置
     */
    public WechatWorkRobotConfig config;

    /**
     * 消息类型
     */
    public MessageTypeEnum messageTypeEnum;

    /**
     * 得到服务
     *
     * @return {@link WxCpServiceImpl}
     */
    public WxCpServiceImpl getService() {
        WxCpServiceImpl wxCpService = SingletonUtil.get(config.getWebhookKey() + config.getConfigId(), () -> {
            WxCpDefaultConfigImpl cpConfig = new WxCpDefaultConfigImpl();
            cpConfig.setWebhookKey(config.getWebhookKey());
            WxCpServiceImpl service = new WxCpServiceImpl();
            service.setWxCpConfigStorage(cpConfig);
            return service;
        });
        return wxCpService;
    }

    public void handle(IMessageConfigService messageConfigService, IMessageRequestDetailService messageRequestDetailService, RobotCommonDTO param) {
        List<WechatWorkRobotConfig> configs = messageConfigService.queryConfigOrDefault(param, WechatWorkRobotConfig.class);
        for (WechatWorkRobotConfig config : configs) {
            this.config = config;
            MessageRequestDetail requestDetail = MessageRequestDetail.builder()
                    .platform(messageTypeEnum.getPlatform().name())
                    .messageType(messageTypeEnum.name())
                    .receiverId("all")
                    .requestNo(param.getRequestNo())
                    .configId(config.getConfigId())
                    .build();
            try {
                sendMessage();
                requestDetail.setSendStatus(SendStatusEnum.SEND_STATUS_SUCCESS.getCode());
                requestDetail.setMsgTest(SendStatusEnum.SEND_STATUS_SUCCESS.getDescription());
            } catch (Exception e) {
                log.error(messageTypeEnum.getName() + "发送消息失败", e);
                String eMessage = ExceptionUtil.getMessage(e);
                eMessage = StringUtils.isBlank(eMessage) ? "未知错误" : eMessage;
                requestDetail.setSendStatus(SendStatusEnum.SEND_STATUS_FAIL.getCode());
                requestDetail.setMsgTest(eMessage);
            }
            messageRequestDetailService.logDetail(requestDetail);
        }
    }

    /**
     * 机器人发送消息
     * @param param
     * @throws WxErrorException
     */
    public abstract void sendMessage() throws WxErrorException;
}
