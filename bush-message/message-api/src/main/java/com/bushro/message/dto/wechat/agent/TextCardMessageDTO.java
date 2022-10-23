package com.bushro.message.dto.wechat.agent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import com.bushro.message.base.BaseMessage;

import java.util.List;

/**
 * 企业微信-文本卡片
 *
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class TextCardMessageDTO extends AgentCommonDTO {
    private static final long serialVersionUID = -5830938694539681793L;

    /**
     * 点击后跳转的链接。最长2048字节，请确保包含了协议头(http/https)
     */
    private String url;

    /**
     * 标题，不超过128个字节，超过会自动截断
     */
    private String title;

    /**
     * 描述，不超过512个字节，超过会自动截断
     */
    private String description;

    /**
     * 按钮文字。 默认为“详情”， 不超过4个文字，超过自动截断。
     */
    private String btntxt;
}
