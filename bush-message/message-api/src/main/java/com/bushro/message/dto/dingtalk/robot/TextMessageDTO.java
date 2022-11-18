package com.bushro.message.dto.dingtalk.robot;

import com.bushro.message.annotation.SchemeValue;
import com.bushro.message.enums.SchemeValueType;
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
    @SchemeValue(value = "请输入内容...", type = SchemeValueType.TEXTAREA, order = 4)
    private String content;
}
