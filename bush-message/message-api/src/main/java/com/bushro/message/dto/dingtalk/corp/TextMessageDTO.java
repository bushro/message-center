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
public class TextMessageDTO extends CommonDTO {
    private static final long serialVersionUID = -3289428483627765265L;

    /**
     * 请输入内容...
     */
    private String content;

}
