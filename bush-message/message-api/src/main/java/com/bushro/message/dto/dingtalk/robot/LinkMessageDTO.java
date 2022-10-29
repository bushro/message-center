package com.bushro.message.dto.dingtalk.robot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 钉钉群消息link类型DTO
 *
 * @author 钟宝林
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class LinkMessageDTO extends RobotCommonDTO {
    private static final long serialVersionUID = -3289428483627765265L;

    /**
     * 消息标题
     */
    private String title;

    /**
     * 消息内容。如果太长只会部分展示
     */
    private String text;

    /**
     * 点击消息跳转的URL，打开方式如下：
     * 移动端，在钉钉客户端内打开
     * PC端
     * 默认侧边栏打开
     * 希望在外部浏览器打开，请参考消息链接说明
     */
    private String messageUrl;

    /**
     * 图片URL。
     */
    private String picUrl;

}
