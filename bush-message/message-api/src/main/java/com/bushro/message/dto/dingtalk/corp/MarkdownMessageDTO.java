package com.bushro.message.dto.dingtalk.corp;

import com.bushro.message.annotation.SchemeValue;
import com.bushro.message.enums.SchemeValueType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;


/**
 * 减价消息dto
 * 钉钉工作通知Markdown
 * *
 *
 * @author luo.qiang
 * @date 2022/11/18
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MarkdownMessageDTO extends DingCommonDTO {

    private static final long serialVersionUID = -2902139799506451209L;


    @SchemeValue(value = "首屏会话透出的展示内容", order = 4)
    private String title;

    @SchemeValue(type = SchemeValueType.TEXTAREA, description = "请输入Markdown内容...", order = 5)
    private String text;

}
