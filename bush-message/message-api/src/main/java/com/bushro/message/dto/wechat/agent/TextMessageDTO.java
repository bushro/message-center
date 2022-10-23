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
public class TextMessageDTO extends AgentCommonDTO {
    private static final long serialVersionUID = -3289428483627765265L;

    /**
     * 请输入内容...
     */
    private String content;

}
