package com.bushro.message.vo;

import com.bushro.common.core.util.MessagePage;
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

    private MessagePage<Map> page;

    private List<ConfigFieldVO> columnList;

}
