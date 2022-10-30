package com.bushro.message.base;

import lombok.*;

import java.io.Serializable;

/**
 * @description: DOTO
 * @author bushro
 * @date: 2022/9/29
 */
@Data
public abstract class Config implements Serializable {
    private static final long serialVersionUID = 2765017560754006377L;
    /**
     * 配置id
     */
    private long configId;
    /**
     * 是否使用
     */
    private boolean useFlag;
    /**
     * 配置名称
     */
    private String configName;
}
