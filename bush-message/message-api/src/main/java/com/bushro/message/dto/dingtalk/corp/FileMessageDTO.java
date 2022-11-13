package com.bushro.message.dto.dingtalk.corp;

import com.bushro.message.annotation.SchemeValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 钉钉文件消息
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FileMessageDTO extends DingCommonDTO {


    private static final long serialVersionUID = -8709896456192012662L;

    /**
     * 媒体文件mediaid。 可以通过上传媒体文件接口获取。建议宽600像素 x 400像素，宽高比3 : 2。
     */
    @SchemeValue("媒体文件mediaid")
    private String mediaId;
}
