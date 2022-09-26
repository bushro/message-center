package com.bushro.message.dto.wechat.agent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import com.bushro.message.base.BaseMessage;

import java.util.List;

/**
 * 企业微信文本消息发送DTO
 *
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TextMessageDTO extends BaseMessage {
    private static final long serialVersionUID = -3289428483627765265L;

    /**
     * 接收人分组列表
     */
    private List<Long> receiverGroupIds;

    /**
     * 接收人列表
     */
    private List<String> receiverIds;

    /**
     * PartyID列表，非必填，多个接受者用‘|’分隔。当touser为@all时忽略本参数
     */
    private String toParty;

    /**
     * TagID列表，非必填，多个接受者用‘|’分隔。当touser为@all时忽略本参数
     */
    private String toTag;

    /**
     * 请输入内容...
     */
    private String content;

}
