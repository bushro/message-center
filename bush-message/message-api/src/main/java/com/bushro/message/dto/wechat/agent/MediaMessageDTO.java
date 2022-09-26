package com.bushro.message.dto.wechat.agent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import com.bushro.message.base.BaseMessage;

import java.util.List;

/**
 * 图片、文件消息DTO
 *
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class MediaMessageDTO extends BaseMessage {
    private static final long serialVersionUID = 7412950115675650317L;

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
     * PartyID列表，非必填，多个接受者用‘|’分隔。当touser为@all时忽略本参数
     */
    private String toTag;

    /**
     * 素材id
     */
    private String mediaId;

}
