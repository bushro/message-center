package com.bushro.message.handle.email;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.mail.MailAccount;
import com.bushro.message.dto.email.EmailCommonDTO;
import com.bushro.message.entity.MessageRequestDetail;
import com.bushro.message.enums.MessageTypeEnum;
import com.bushro.message.properties.EmailConfig;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * 邮件-抽象方法
 *
 * @author luo.qiang
 * @date 2022/10/17
 */
@Slf4j
public abstract class AbstractEmailHandler {

    public Set<String> receiverUsers;

    public EmailConfig config;

    /**
     * 所有消息处理器必须实现这个接口，标识自己处理的是哪个消息类型
     */
    public abstract MessageTypeEnum messageType();

    public void setReceiverUsers(EmailCommonDTO param) {
        Set<String> receiverUsers = new HashSet<>();
        if (CollUtil.isNotEmpty(param.getReceiverIds())) {
            receiverUsers.addAll(param.getReceiverIds());
        }
        this.receiverUsers = receiverUsers;
        if (receiverUsers.size() <= 0) {
            log.warn("请求号：{}，消息配置：{}。没有检测到接收用户", param.getRequestNo(), config.getConfigName());
            return;
        }
    }


    /**
     * 获取client
     *
     * @param config 配置
     * @return {@link DingTalkClient}
     */
    public MailAccount getClient(EmailConfig config) {
        MailAccount account = new MailAccount();
        account.setHost(config.getHost());
        account.setPort(config.getPort());
        account.setAuth(true);
        account.setFrom(config.getFrom());
        account.setUser(config.getUser());
        account.setPass(config.getPassword());
        account.setSslEnable(Optional.ofNullable(config.getSslEnable()).orElse(false));
        return account;
    }
}
