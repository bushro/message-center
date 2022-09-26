package com.bushro.message.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: 消息类型枚举
 * @author bushro
 * @date: 2021/9/29
 */
@Getter
@AllArgsConstructor
public enum MessageTypeEnum {
    // ================================邮件====================================
    EMAIL("邮件 ", MessagePlatformEnum.EMAIL),
    // ================================企业微信-应用====================================
    WECHAT_WORK_AGENT_TEXT("文本", MessagePlatformEnum.WECHAT_WORK_AGENT),
    WECHAT_WORK_AGENT_IMAGE("图片", MessagePlatformEnum.WECHAT_WORK_AGENT),
    WECHAT_WORK_AGENT_VIDEO("视频", MessagePlatformEnum.WECHAT_WORK_AGENT),
    WECHAT_WORK_AGENT_FILE("文件", MessagePlatformEnum.WECHAT_WORK_AGENT),
    WECHAT_WORK_AGENT_TEXTCARD("文本卡片", MessagePlatformEnum.WECHAT_WORK_AGENT),

    // ================================微信公众号====================================
    WECHAT_OFFICIAL_ACCOUNT_TEXT("文本", MessagePlatformEnum.WECHAT_OFFICIAL_ACCOUNT),
    WECHAT_OFFICIAL_ACCOUNT_NEWS("图文消息", MessagePlatformEnum.WECHAT_OFFICIAL_ACCOUNT),
    WECHAT_OFFICIAL_ACCOUNT_TEMPLATE("模板消息", MessagePlatformEnum.WECHAT_OFFICIAL_ACCOUNT),

    // ================================钉钉-工作通知====================================
    /**
     * 参考：https://developers.dingtalk.com/document/app/asynchronous-sending-of-enterprise-session-messages
     * 工作通知消息是以某个微应用的名义推送到员工的工作通知消息，例如生日祝福、入职提醒等。
     * 发送工作通知消息需要注意以下事项：
     * 同一个应用相同内容的消息，同一个用户一天只能接收一次。
     * 同一个企业内部应用在一天之内，最多可以给一个用户发送500条消息通知。
     * 通过设置to_all_user参数全员推送消息，一天最多3次。且企业发送消息单次最多只能给5000人发送，ISV发送消息单次最多能给1000人发送。
     * 超出以上限制次数后，接口返回成功，但用户无法接收到。详细的限制说明，请参考工作通知消息限制。
     */
    DING_TALK_COPR_TEXT("文本", MessagePlatformEnum.DING_TALK_CORP),
    DING_TALK_COPR_MARKDOWN("Markdown", MessagePlatformEnum.DING_TALK_CORP),
    DING_TALK_COPR_LINK("链接消息", MessagePlatformEnum.DING_TALK_CORP),
    DING_TALK_COPR_IMAGE("图片消息", MessagePlatformEnum.DING_TALK_CORP),
    DING_TALK_COPR_FILE("文件消息", MessagePlatformEnum.DING_TALK_CORP),
    DING_TALK_COPR_ACTION_CARD_SINGLE("卡片-单按钮", MessagePlatformEnum.DING_TALK_CORP),
    DING_TALK_COPR_ACTION_CARD_MULTI("卡片-多按钮", MessagePlatformEnum.DING_TALK_CORP),
    ;
    /**
     * 类型名称
     */
    private final String name;
    /**
     * 所属平台
     */
    private final MessagePlatformEnum platform;



}
