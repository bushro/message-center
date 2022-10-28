package com.bushro.message.handle.dingtalk;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.json.JSONUtil;
import com.bushro.common.core.exception.BusinessException;
import com.bushro.message.base.BaseMessage;
import com.bushro.message.dto.dingtalk.robot.RobotCommonDTO;
import com.bushro.message.entity.MessageRequestDetail;
import com.bushro.message.enums.MessageTypeEnum;
import com.bushro.message.enums.SendStatusEnum;
import com.bushro.message.handle.IMessageHandler;
import com.bushro.message.properties.DingTalkRobotConfig;
import com.bushro.message.service.IMessageConfigService;
import com.bushro.message.service.IMessageRequestDetailService;
import com.bushro.message.utils.SingletonUtil;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.util.List;

/**
 * 钉钉-抽象方法
 *
 * @author luo.qiang
 * @date 2022/10/28
 */
@Slf4j
public abstract class AbstractDingRobotHandler<T extends BaseMessage> implements IMessageHandler {

    /**
     * 接收人
     */
    public RobotCommonDTO commonDTO;

    /**
     * 配置
     */
    public DingTalkRobotConfig config;

    /**
     * 消息类型
     */
    public MessageTypeEnum messageTypeEnum;

    /**
     * 拼接到webhook后面的加签信息
     */
    private static final String URL = "&timestamp=%s&sign=%s";


    /**
     * 获取client
     *
     * @param config 配置
     * @return {@link DingTalkClient}
     */
    public DingTalkClient getClient(DingTalkRobotConfig config) {
        DingTalkClient client = SingletonUtil.get("dinging-" + config.getSecret(),
                (SingletonUtil.Factory<DingTalkClient>) () -> {
                    try {
                        return new DefaultDingTalkClient(config.getWebhook() + getSign());
                    } catch (Exception e) {
                        log.error("获取加签信息失败", e);
                        throw new BusinessException("获取加签信息失败");
                    }
                });
        return client;
    }

    /**
     * 执行
     *
     * @param request 请求
     */
    public MessageRequestDetail execute(OapiRobotSendRequest request) {
        MessageRequestDetail requestDetail = MessageRequestDetail.builder()
                .platform(messageTypeEnum.getPlatform().name())
                .messageType(messageTypeEnum.name())
                .receiverId("all")
                .requestNo(commonDTO.getRequestNo())
                .configId(config.getConfigId())
                .build();
        try {
            DingTalkClient client = this.getClient(config);
            OapiRobotSendResponse rsp = client.execute(request);
            if (!rsp.isSuccess()) {
                throw new IllegalStateException(rsp.getBody());
            }
            requestDetail.setSendStatus(SendStatusEnum.SEND_STATUS_SUCCESS.getCode());
            requestDetail.setMsgTest(SendStatusEnum.SEND_STATUS_SUCCESS.getDescription());
            log.info("{}发送消息响应数据:{}", messageTypeEnum.getName(), rsp.getBody());
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
    protected void handleMessage(IMessageConfigService messageConfigService, IMessageRequestDetailService messageRequestDetailService) {
        log.info("发送{}消息开始: 参数-{}-------------------------------", messageTypeEnum.getName(), JSONUtil.toJsonStr(commonDTO));
        List<DingTalkRobotConfig> configs = messageConfigService.queryConfigOrDefault(commonDTO, DingTalkRobotConfig.class);
        for (DingTalkRobotConfig config : configs) {
            this.config = config;
            OapiRobotSendRequest request = buildRequest();
            MessageRequestDetail requestDetail = this.execute(request);
            //记录发送情况
            messageRequestDetailService.logDetail(requestDetail);
        }
        log.info("发送{}消息结束-------------------------------", messageType().getName());
    }

    /**
     * 构建请求
     *
     * @return {@link OapiRobotSendRequest}
     */
    protected abstract OapiRobotSendRequest buildRequest();

    /**
     * 得到签
     *
     * @return {@link String}
     * @throws Exception 异常
     */
    protected String getSign() throws Exception {
        Long timestamp = System.currentTimeMillis();
        String stringToSign = timestamp + "\n" + config.getSecret();
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(config.getSecret().getBytes("UTF-8"), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
        String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)), "UTF-8");
        return String.format(URL, timestamp, sign);
    }
}
