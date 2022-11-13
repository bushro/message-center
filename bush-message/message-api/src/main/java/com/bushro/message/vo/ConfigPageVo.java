package com.bushro.message.vo;

import com.bushro.common.core.util.MessagePage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 配置页面
 *
 * @author luo.qiang
 * @date 2022/11/05
 */
@Data
public class ConfigPageVo {

    @ApiModelProperty("分页数据")
    private MessagePage<Map> page;

    @ApiModelProperty("表头")
    private List<ConfigFieldVO> columnList;

}
