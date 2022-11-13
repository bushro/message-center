package com.bushro.message.base;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 消息
 *
 * @author luo.qiang
 * @date 2022/11/11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IdStrAndName implements Serializable {
    private static final long serialVersionUID = -8247431937045453249L;

    @ApiModelProperty("消息代码")
    private String id;

    @ApiModelProperty("消息名称")
    private String name;

}
