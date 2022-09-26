package com.bushro.message.utils;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ReflectUtil;
import com.bushro.message.base.Config;
import com.bushro.message.enums.ConfigValueType;
import com.bushro.message.handle.MessageHandler;
import com.bushro.message.vo.ConfigFieldVO;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 消息处理器相关工具
 *
 **/
public final class MessageHandlerUtils {


    /**
     * 获取消息处理器的参数类型
     */
    public static Class<?> getParamType(MessageHandler<?> messageHandler) {
        // 缓存单例，避免每次都执行反射去获取参数类型
        return SingletonUtil.get("param-type-" + messageHandler.getClass().getName(), () -> {
            ParameterizedType superclass = (ParameterizedType) messageHandler.getClass().getGenericSuperclass();
            return (Class<?>) superclass.getActualTypeArguments()[0];
        });
    }

    /**
     * 把数据库里的配置数据转成具体的配置类
     *
     * @param configType 配置类型
     * @param configMap  数据库里的配置数据
     */
    public static List<Config> convertConfig(Class<?> configType, Map<Long, Map<String, Object>> configMap) {
        try {
            List<Config> configs = new ArrayList<>();
            for (Map.Entry<Long, Map<String, Object>> entry : configMap.entrySet()) {
                Long configId = entry.getKey();
                Map<String, Object> valueMap = entry.getValue();

                Config configObj = (Config) configType.newInstance();
                configObj.setConfigId(configId);
                configObj.setConfigName((String) valueMap.get("configName"));
                for (String key : valueMap.keySet()) {
                    if (!ReflectUtil.hasField(configType, key) || "configName".equals(key)) {
                        continue;
                    }
                    Field declaredField = configType.getDeclaredField(key);
                    Class<?> fieldType = declaredField.getType();
                    ReflectUtil.setFieldValue(configObj, key, MapUtil.get(valueMap, key, fieldType));
                }
                configs.add(configObj);
            }
            return configs;
        } catch (Exception e) {
            throw new IllegalStateException(configType.getName() + "配置字段值转换失败，请检查", e);
        }
    }

}
