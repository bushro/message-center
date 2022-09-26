package com.bushro.message.dto.dingtalk.corp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import com.bushro.message.base.BaseMessage;

import java.util.List;

/**
 * 钉钉卡片消息-支持一个点击Action，必须传入参数 single_title和 single_url。
 *
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ActionCardSingleMessageDTO extends BaseMessage {
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
     * 接收人的部门id列表，接收者的部门id列表，多个用,隔开
     */
    private String deptIdList;

    /**
     * 透出到会话列表和通知的文案。
     */
    private String title;

    /**
     * 使用整体跳转ActionCard样式时的标题。必须与single_url同时设置，最长20个字符。
     */
    private String singleTitle;

    /**
     * 跳转链接
     */
    private String singleUrl;

    /**
     * 消息内容，支持markdown，语法参考标准markdown语法。建议1000个字符以内。
     */
    private String markdown;
}
