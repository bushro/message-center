package com.bushro.message.properties;

import lombok.*;
import com.bushro.message.base.Config;

/**
 * @description: 邮箱配置类
 * @author bushro
 * @date: 2022/9/29
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailConfig extends Config {
    private static final long serialVersionUID = 3833630267273040696L;

    /**
     * 设置SMTP服务器域名
     */
    private String host;
    /**
     * SMTP服务端口
     */
    private int port;
    /**
     * 发送方，遵循RFC-822标准
     */
    private String from;
    /**
     * 用户名
     */
    private String user;
    /**
     * 密码
     */
    private String password;
    /**
     * 使用 SSL安全连接
     */
    private Boolean sslEnable;

}
