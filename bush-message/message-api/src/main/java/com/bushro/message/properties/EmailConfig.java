package com.bushro.message.properties;

import com.bushro.message.annotation.ConfigValue;
import lombok.*;
import com.bushro.message.base.Config;

/**
 * @author bushro
 * @description: 邮箱配置类
 * @date: 2022/9/29
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailConfig extends Config {
    private static final long serialVersionUID = 3833630267273040696L;

    @ConfigValue(value = "服务器", description = "服务器域名/ip")
    private String host;

    @ConfigValue(value = "端口", description = "服务器域名/ip")
    private int port;

    @ConfigValue(value = "发送方", description = "发送方，遵循RFC-822标准")
    private String from;

    @ConfigValue(value = "用户名")
    private String user;

    @ConfigValue(value = "密码")
    private String password;

    @ConfigValue(value = "SSL安全连接", description = "发送方，遵循RFC-822标准")
    private Boolean sslEnable;
}
