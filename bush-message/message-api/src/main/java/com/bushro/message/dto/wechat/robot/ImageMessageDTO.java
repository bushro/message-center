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
public class ImageMessageDTO extends RobotCommonDTO {

    /**
     * 图片内容的base64编码
     */
    @SchemeValue(value = "图片内容的base64编码", order = 1)
    private String base64;

    /**
     * 图片内容（base64编码前）的md5值
     */
    @SchemeValue(value = "图片内容（base64编码前）的md5值", order = 2)
    private String md5;
}
