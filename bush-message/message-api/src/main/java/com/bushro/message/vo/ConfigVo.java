package com.bushro.message.vo;

import lombok.Data;

/**
 * 配置列表
 *
 * @author luo.qiang
 * @date 2022/11/05
 */
@Data
public class ConfigVo {
    private Long configId;
    private String configName;
    private String key;
    private String value;
}
