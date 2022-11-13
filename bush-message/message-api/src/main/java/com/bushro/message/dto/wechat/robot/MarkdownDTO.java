package com.bushro.message.dto.wechat.robot;

import com.bushro.message.annotation.SchemeValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MarkdownDTO extends RobotCommonDTO {

    /**
     * markdown内容，最长不超过4096个字节，必须是utf8编码
     */
    @SchemeValue("markdown内容，最长不超过4096个字节，必须是utf8编码")
    private String content;
}
