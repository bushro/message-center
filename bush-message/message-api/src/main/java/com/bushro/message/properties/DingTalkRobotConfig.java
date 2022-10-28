package com.bushro.message.properties;


import com.bushro.message.base.Config;
import lombok.*;

/**
 * 钉钉自定义机器人配置
 *
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DingTalkRobotConfig extends Config {
    private static final long serialVersionUID = -6726902816158196669L;

    /**
     * 加签
     */
    private String secret;
    /**
     * webhook 自定义机器人 https://oapi.dingtalk.com/robot/send?access_token=xxx 格式
     */
    private String webhook;

}
