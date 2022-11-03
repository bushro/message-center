package com.bushro.message.properties;


import com.bushro.message.annotation.ConfigValue;
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
public class DingTalkCorpConfig extends Config {
    private static final long serialVersionUID = -9206902816158196669L;

    @ConfigValue(value = "应用appKey")
    private String appKey;

    @ConfigValue(value = "应用Secret")
    private String appSecret;

    @ConfigValue(value = "应用agentId")
    private Long agentId;

}
