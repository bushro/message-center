package com.bushro.message.properties;


import com.bushro.message.annotation.ConfigValue;
import com.bushro.message.base.Config;
import lombok.*;

/**
 * 企业微信-群机器人配置
 *
 * @author luo.qiang
 * @date 2022/10/26
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WechatWorkRobotConfig extends Config {
    private static final long serialVersionUID = -9206902816158196669L;

    @ConfigValue(value = "webhook", description = "群机器人的webhook的key")
    private String webhookKey;

}
