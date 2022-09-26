package com.bushro.message.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * 消息基类
 **/
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BaseMessage extends BaseParam {
    private static final long serialVersionUID = 437493036483567460L;
    /**
     * 配置
     */
    private List<Long> configIds;

}
