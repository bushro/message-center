package com.bushro.message.handle.dingtalk;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.StrUtil;
import com.bushro.message.base.BaseMessage;
import com.bushro.message.dto.dingtalk.corp.DingCommonDTO;
import com.bushro.message.entity.MessageRequestDetail;
import com.bushro.message.enums.MessageTypeEnum;
import com.bushro.message.enums.SendStatusEnum;
import com.bushro.message.handle.IMessageHandler;
import com.bushro.message.properties.DingTalkCorpConfig;
import com.bushro.message.utils.AccessTokenUtils;
import com.bushro.message.utils.SingletonUtil;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * 钉钉-抽象方法
 *
 * @author luo.qiang
 * @date 2022/10/17
 */
@Slf4j
public abstract class AbstractDingHandler<T extends BaseMessage> implements IMessageHandler {

    /**
     * 接收人
     */
    public DingCommonDTO param;

    /**
     * 配置
     */
    public DingTalkCorpConfig config;

    /**
     * 消息类型
     */
    public MessageTypeEnum messageTypeEnum;


    public void checkAndSetUsers(DingCommonDTO param) {
        if (param.isToAllUser()) {
            return;
        }
        if (StrUtil.isEmpty(param.getUseridList()) && StrUtil.isEmpty(param.getDeptIdList())) {
            log.warn("请求号：{}，消息配置：{}。没有检测到接收用户", param.getRequestNo(), config.getConfigName());
        }
        this.param = param;
        return;
    }


    /**
     * 请求参数封装
     *
     * @param param 参数
     * @return {@link OapiMessageCorpconversationAsyncsendV2Request}
     */
    public OapiMessageCorpconversationAsyncsendV2Request getRequest(DingCommonDTO param) {
        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        request.setAgentId(config.getAgentId());
        request.setUseridList(param.getUseridList());
        request.setDeptIdList(param.getDeptIdList());
        request.setToAllUser(param.isToAllUser());
        return request;
    }

    /**
     * 获取client
     *
     * @param config 配置
     * @return {@link DingTalkClient}
     */
    public DingTalkClient getClient(DingTalkCorpConfig config) {
        DingTalkClient client = SingletonUtil.get("dinging-" + config.getAppKey() + config.getAppSecret(),
                (SingletonUtil.Factory<DingTalkClient>) () -> new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2"));
        return client;
    }

    /**
     * 执行
     *
     * @param param   参数
     * @param request 请求
     */
    public MessageRequestDetail execute(DingCommonDTO param, OapiMessageCorpconversationAsyncsendV2Request request) {
        MessageRequestDetail requestDetail = MessageRequestDetail.builder()
                .platform(messageTypeEnum.getPlatform().name())
                .messageType(messageTypeEnum.name())
                .receiverId(param.isToAllUser() ? "all" : param.getUseridList() + "-" + param.getDeptIdList())
                .requestNo(param.getRequestNo())
                .configId(config.getConfigId())
                .build();
        try {
            DingTalkClient client = getClient(config);
            OapiMessageCorpconversationAsyncsendV2Response rsp = client.execute(request
                    , AccessTokenUtils.getAccessToken(config.getAppKey(), config.getAppSecret()));
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
}
