package com.bushro.message.dto.dingtalk.robot;

import com.bushro.message.annotation.SchemeValue;
import com.bushro.message.enums.SchemeValueType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 钉钉群消息markdown类型DTO
 *
 * @author bushro
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MarkdownMessageDTO extends RobotCommonDTO {
    private static final long serialVersionUID = -3289428483627765265L;

    /**
     * 首屏会话透出的展示内容
     */
    @SchemeValue(value = "首屏会话透出的展示内容", type = SchemeValueType.STRING, order = 4)
    private String title;

    /**
     * markdown格式的消息
     */
    @SchemeValue(value = "markdown格式的消息", type = SchemeValueType.TEXTAREA, order = 5)
    private String text;

}
