package com.bushro.message.dto.wechat.robot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 图文消息
 *
 * @author luo.qiang
 * @date 2022/10/26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ArticleDTO {

    /**
     * 标题
     */
    private String title;

    /**
     * 描述 超过512个字节，超过会自动截断
     */
    private String description;

    /**
     * 点击跳转链接
     */
    private String url;

    /**
     * 图片链接", description = "图文消息的图片链接，支持JPG、PNG格式，较好的效果为大图1068*455，小图150*150。
     */
    private String picurl;

}
