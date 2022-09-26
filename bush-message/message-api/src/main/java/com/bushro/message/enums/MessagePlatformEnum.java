package com.bushro.message.enums;


import com.bushro.message.properties.DingTalkCorpConfig;
import com.bushro.message.properties.EmailConfig;
import com.bushro.message.properties.WechatOfficialAccountConfig;
import com.bushro.message.properties.WechatWorkAgentConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import com.bushro.message.base.Config;

/**
 * @description: 消息平台枚举
 * @author bushro
 * @date: 2021/9/29
 */
@Getter
@AllArgsConstructor
public enum MessagePlatformEnum {
    EMAIL(EmailConfig.class, "邮箱"),
    WECHAT_WORK_AGENT(WechatWorkAgentConfig.class, "企业微信-应用消息"),
    WECHAT_OFFICIAL_ACCOUNT(WechatOfficialAccountConfig.class, "微信公众号"),
    DING_TALK_CORP(DingTalkCorpConfig.class, "钉钉-工作通知");

    /**
     * 配置类型
     */
    private Class<? extends Config> configType;
    /**
     * 消息平台名称
     */
    private final String name;
}
