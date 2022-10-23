package com.bushro.message.handle.email;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.mail.MailAccount;
import com.bushro.message.base.BaseMessage;
import com.bushro.message.dto.email.EmailCommonDTO;
import com.bushro.message.enums.MessageTypeEnum;
import com.bushro.message.handle.IMessageHandler;
import com.bushro.message.properties.EmailConfig;
import com.dingtalk.api.DingTalkClient;
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
public abstract class AbstractEmailHandler<T extends BaseMessage> implements IMessageHandler {

    /**
     * 接收人
     */
    public Set<String> receiverUsers;

    /**
     * 配置
     */
    public EmailConfig config;

    /**
     * 消息类型
     */
    public MessageTypeEnum messageTypeEnum;


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
