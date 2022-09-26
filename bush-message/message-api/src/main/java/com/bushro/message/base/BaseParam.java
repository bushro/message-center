package com.bushro.message.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 请求基类
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseParam implements Serializable {

    private static final long serialVersionUID = 7375883597696884060L;

    @ApiModelProperty(value = "请求编号（幂等）")
    private String requestNo;

}
