package com.bushro.message.dto.dingtalk.corp;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import com.bushro.message.base.BaseMessage;

import java.util.List;

/**
 * 钉钉工作通知发送-文本信息
 *
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TextMessageDTO extends BaseMessage {
    private static final long serialVersionUID = -3289428483627765265L;

    /**
     * 接收人分组列表
     */
    private List<Long> receiverGroupIds;

    /**
     * 是否发送给企业全部用户，注意钉钉限制只能发3次全员消息
     */
    private boolean toAllUser;

    /**
     * 接收人列表
     */
    private List<String> receiverIds;

    /**
     * 接收者的部门id列表，最大列表长度20。接收者是部门ID时，包括子部门下的所有用户。多个用,隔开
     *
     */
    private String deptIdList;

    /**
     * 请输入内容...
     */
    private String content;

}
