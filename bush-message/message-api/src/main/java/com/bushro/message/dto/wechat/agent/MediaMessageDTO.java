package com.bushro.message.dto.wechat.agent;

import com.bushro.message.annotation.SchemeValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 图片、文件消息DTO
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class MediaMessageDTO extends AgentCommonDTO {

    private static final long serialVersionUID = 7412950115675650317L;

    /**
     * 素材id
     */
    @SchemeValue("素材id")
    private String mediaId;

}
