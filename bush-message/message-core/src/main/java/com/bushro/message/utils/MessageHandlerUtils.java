package com.bushro.message.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.bushro.message.base.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 消息处理器相关工具
 **/
public final class MessageHandlerUtils {


    /**
     * 把数据库里的配置数据转成具体的配置类
     *
     * @param configType 配置类型
     * @param configMap  数据库里的配置数据
     */
    public static List<Config> convertConfig(Class<?> configType, Map<Long, Map<String, Object>> configMap) {
        try {
            List<Config> configs = new ArrayList<>();
            configMap.forEach((k, v) -> {
                Config config = (Config) BeanUtil.mapToBean(v, configType, true, CopyOptions.create());
                config.setConfigId(k);
                configs.add(config);
            });
            return configs;
        } catch (Exception e) {
            throw new IllegalStateException(configType.getName() + "配置字段值转换失败，请检查", e);
        }
    }

}
