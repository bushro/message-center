package com.bushro.message.dto.dingtalk.corp;

import com.bushro.message.annotation.SchemeValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 钉钉链接消息
 *
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class LinkMessageDTO extends DingCommonDTO {
    private static final long serialVersionUID = 6529460286674167742L;

    /**
     * 消息点击链接地址，当发送消息为小程序时支持小程序跳转链接。
     */
    @SchemeValue("消息点击链接地址，当发送消息为小程序时支持小程序跳转链接。")
    private String messageUrl;

    /**
     * 图片地址，可以通过上传媒体文件接口获取。
     */
    @SchemeValue("图片地址，可以通过上传媒体文件接口获取。")
    private String picUrl;

    /**
     * 消息标题，建议100字符以内。
     */
    @SchemeValue("消息标题，建议100字符以内。")
    private String title;

    /**
     * 消息描述，建议500字符以内。
     */
    @SchemeValue("消息描述，建议500字符以内。")
    private String text;

}
