package com.bushro.message.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @description: 平台类型
 * @author: luoq
 * @date: 2022/11/14
 */
@Builder
@Data
public class PlatformVo implements Serializable {

    private static final long serialVersionUID = 3694119451882625645L;

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty(value = "平台中文名称")
    private String name;
    @ApiModelProperty(value = "描述")
    private String description;
    @ApiModelProperty(value = "格式校验用的正则表达式")
    private String validateReg;
    @ApiModelProperty(value = "是否启用")
    private boolean enable;
}
