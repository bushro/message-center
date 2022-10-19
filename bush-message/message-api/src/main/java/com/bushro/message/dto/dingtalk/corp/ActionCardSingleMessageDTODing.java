package com.bushro.message.dto.dingtalk.corp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 钉钉卡片消息-支持一个点击Action，必须传入参数 single_title和 single_url。
 *
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ActionCardSingleMessageDTODing extends DingCommonDTO {
    private static final long serialVersionUID = -3289428483627765265L;

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
