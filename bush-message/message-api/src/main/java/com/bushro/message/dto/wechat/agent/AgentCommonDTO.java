package com.bushro.message.dto.wechat.agent;

import com.bushro.message.base.BaseMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AgentCommonDTO extends BaseMessage {

    /**
     * 接收人分组列表
     */
    private List<Long> receiverGroupIds;

    /**
     * 成员ID列表（消息接收者，多个接收者用‘|’分隔，最多支持1000个）。特殊情况：指定为@all，则向关注该企业应用的全部成员发送
     */
    private String toUser;

    /**
     * PartyID列表，非必填，多个接受者用‘|’分隔。当touser为@all时忽略本参数
     */
    private String toParty;

    /**
     * TagID列表，非必填，指定接收消息的标签，标签ID列表，多个接收者用‘|’分隔，最多支持100个。
     * 当touser为"@all"时忽略本参数
     */
    private String toTag;

    /**
     * 表示是否开启重复消息检查，0表示否，1表示是，默认0
     */
    private int enableDuplicateCheck = 0;

    /**
     * 表示是否重复消息检查的时间间隔，默认1800s，最大不超过4小时
     */
    private int duplicateCheckInterval = 1800;


}
