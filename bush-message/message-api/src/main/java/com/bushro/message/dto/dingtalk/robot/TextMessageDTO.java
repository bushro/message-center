package com.bushro.message.dto.dingtalk.robot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 钉钉自定义机器人-文本信息
 *
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TextMessageDTO extends RobotCommonDTO {
    private static final long serialVersionUID = -7823428483627765265L;

    /**
     * 请输入内容...
     */
    private String content;
}