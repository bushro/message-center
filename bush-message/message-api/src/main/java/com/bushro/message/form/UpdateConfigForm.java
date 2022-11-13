package com.bushro.message.form;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.bushro.message.enums.MessagePlatformEnum;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;

/**
 * 更新配置DTO
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateConfigForm implements Serializable {
    private static final long serialVersionUID = -1623744940584029569L;

    @NotNull(message = "未知平台")
    @ApiModelProperty("平台类型")
    private MessagePlatformEnum platform;

    @ApiModelProperty("配置id")
    private Long configId;

    @ApiModelProperty("配置名称")
    private String configName;

    private Boolean useFlag;
    @ApiModelProperty("键对应配置的key，值对应配置的value")
    private Map<String, String> config;

}
