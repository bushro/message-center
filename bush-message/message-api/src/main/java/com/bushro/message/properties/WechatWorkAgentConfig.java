package com.bushro.message.properties;

import lombok.*;
import com.bushro.message.base.Config;


/**
 * @description: 企业微信配置
 * @author bushro
 * @date: 2021/9/29
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WechatWorkAgentConfig extends Config {
    private static final long serialVersionUID = -9206902816158196669L;

    /**
     * 企业ID 在此页面查看：https://work.weixin.qq.com/wework_admin/frame#profile
     */
    private String corpId;
    /**
     * 应用Secret
     */
    private String secret;
    /**
     * 应用agentId
     */
    private Integer agentId;

}
