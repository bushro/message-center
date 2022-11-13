package com.bushro.message.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 配置列表
 *
 * @author luo.qiang
 * @date 2022/11/05
 */
@Data
public class ConfigVo {

    @ApiModelProperty("配置id")
    private Long configId;

    @ApiModelProperty("配置名称")
    private String configName;

    @ApiModelProperty("key")
    private String key;

    @ApiModelProperty("value")
    private String value;
}
