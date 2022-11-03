package com.bushro.message.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.ReflectUtil;
import com.bushro.message.annotation.ConfigValue;
import com.bushro.message.base.Config;
import com.bushro.message.enums.ConfigValueType;
import com.bushro.message.enums.MessagePlatformEnum;
import com.bushro.message.vo.ConfigFieldVO;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.*;

/**
 * 消息处理器相关工具
 **/
public final class MessageHandlerUtils {


    /**
     * 配置类型映射
     */
    public static Map<Class<?>, ConfigValueType> JAVA_TYPE_CONFIG_MAP = new HashMap<>();

    static {
        JAVA_TYPE_CONFIG_MAP.put(int.class, ConfigValueType.INTEGER);
        JAVA_TYPE_CONFIG_MAP.put(long.class, ConfigValueType.INTEGER);
        JAVA_TYPE_CONFIG_MAP.put(short.class, ConfigValueType.INTEGER);
        JAVA_TYPE_CONFIG_MAP.put(char.class, ConfigValueType.INTEGER);
        JAVA_TYPE_CONFIG_MAP.put(Integer.class, ConfigValueType.INTEGER);
        JAVA_TYPE_CONFIG_MAP.put(Long.class, ConfigValueType.INTEGER);
        JAVA_TYPE_CONFIG_MAP.put(Short.class, ConfigValueType.INTEGER);
        JAVA_TYPE_CONFIG_MAP.put(Character.class, ConfigValueType.INTEGER);
        JAVA_TYPE_CONFIG_MAP.put(double.class, ConfigValueType.DECIMAL);
        JAVA_TYPE_CONFIG_MAP.put(Double.class, ConfigValueType.DECIMAL);
        JAVA_TYPE_CONFIG_MAP.put(float.class, ConfigValueType.DECIMAL);
        JAVA_TYPE_CONFIG_MAP.put(Float.class, ConfigValueType.DECIMAL);
        JAVA_TYPE_CONFIG_MAP.put(BigDecimal.class, ConfigValueType.DECIMAL);
        JAVA_TYPE_CONFIG_MAP.put(boolean.class, ConfigValueType.BOOLEAN);
        JAVA_TYPE_CONFIG_MAP.put(Boolean.class, ConfigValueType.BOOLEAN);
        JAVA_TYPE_CONFIG_MAP.put(String.class, ConfigValueType.STRING);
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

    /**
     * 获取消息处理的的配置的所有字段名称
     */
    public static List<ConfigFieldVO> listConfigFieldName(MessagePlatformEnum platform) {
        return SingletonUtil.get("config-field-names-" + platform.name(), () -> {
            Class<? extends Config> configType = platform.getConfigType();
            Field[] fields = ReflectUtil.getFieldsDirectly(configType, false);
            if (fields == null || fields.length <= 0) {
                return Collections.emptyList();
            }


            List<ConfigFieldVO> fieldNames = new ArrayList<>();
            for (Field field : fields) {
                /**
                 * 判断是否是final的字段
                 */
                if (Modifier.isFinal(field.getModifiers())) {
                    continue;
                }
                ConfigValue annotation = field.getAnnotation(ConfigValue.class);
                ConfigValueType type = null;
                String name = "";
                String description = "";
                if (annotation != null) {
                    type = annotation.type();
                    name = annotation.value();
                    description = annotation.description();
                    switch (type) {
                        case AUTO:
                            type = JAVA_TYPE_CONFIG_MAP.get(field.getType());
                            break;
                    }
                }
                if (annotation == null) {
                    Class<?> javaType = field.getType();
                    type = JAVA_TYPE_CONFIG_MAP.get(javaType);
                }
                name = StringUtils.isBlank(name) ? field.getName() : name;
                fieldNames.add(ConfigFieldVO.builder()
                        .name(name)
                        .description(description)
                        .type(type)
                        .key(field.getName())
                        .build());
            }
            return fieldNames;
        });
    }

}
