package com.bushro.message.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.bushro.message.base.IdStrAndName;
import com.bushro.message.enums.ConfigValueType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 配置的字段
 *
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConfigFieldVO implements Serializable {
    private static final long serialVersionUID = -1203229844327500120L;

    @ApiModelProperty("字段名称")
    private String name;

    @ApiModelProperty("字段key")
    private String key;

    @ApiModelProperty("字段描述")
    private String description;

    @ApiModelProperty("字段类型")
    private ConfigValueType type;

    @ApiModelProperty("选项（如果是选择型字段）")
    private List<IdStrAndName> options = new ArrayList<>();

}
