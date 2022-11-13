package com.bushro.message.form;

import com.bushro.common.core.util.CommonPage;
import com.bushro.message.enums.MessagePlatformEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

/**
 * 查询配置形式
 *
 * @author luo.qiang
 * @date 2022/11/05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QueryConfigForm extends CommonPage {

    @NotNull(message = "平台代码不能为空")
    @ApiModelProperty("平台类型")
    private MessagePlatformEnum platform;

    @ApiModelProperty("配置名称")
    private String configName;

}
