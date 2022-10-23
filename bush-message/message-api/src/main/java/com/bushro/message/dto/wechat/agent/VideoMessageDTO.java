package com.bushro.message.dto.wechat.agent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import com.bushro.message.base.BaseMessage;

import java.util.List;

/**
 * 企业微信视频消息类型DTO
 *
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class VideoMessageDTO extends AgentCommonDTO {
    private static final long serialVersionUID = -5830938694539681793L;

    /**
     * 视频素材id
     */
    private String mediaId;

    /**
     * 视频消息的标题，不超过128个字节，超过会自动截断
     */
    private String title;

    /**
     * 视频消息的标题，不超过128个字节，超过会自动截断
     */
    private String description;
}
