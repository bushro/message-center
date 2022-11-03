package com.bushro.message.properties;


import com.bushro.message.annotation.ConfigValue;
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


    @ConfigValue(value = "加签")
    private String secret;

    @ConfigValue(value = "webhook", description = "https://oapi.dingtalk.com/robot/send?access_token=xxx")
    private String webhook;

}
