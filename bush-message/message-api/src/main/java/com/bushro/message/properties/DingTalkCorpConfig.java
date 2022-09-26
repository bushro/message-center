package com.bushro.message.properties;


import lombok.*;
import com.bushro.message.base.Config;

/**
 * 钉钉工作通知配置
 *
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class    DingTalkCorpConfig extends Config {
    private static final long serialVersionUID = -9206902816158196669L;

    /**
     * 应用appKey
     */
    private String appKey;
    /**
     * 应用Secret
     */
    private String appSecret;
    /**
     * 应用agentId
     */
    private Integer agentId;

}
