package com.bushro.message.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.ReflectUtil;
import com.bushro.message.annotation.ConfigValue;
import com.bushro.message.annotation.MultiObjField;
import com.bushro.message.annotation.SchemeValue;
import com.bushro.message.annotation.SchemeValueOption;
import com.bushro.message.base.Config;
import com.bushro.message.base.IdStrAndName;
import com.bushro.message.enums.ConfigValueType;
import com.bushro.message.enums.MessagePlatformEnum;
import com.bushro.message.enums.MessageTypeEnum;
import com.bushro.message.enums.SchemeValueType;
import com.bushro.message.handle.IMessageHandler;
import com.bushro.message.handle.MessageHandlerHolder;
import com.bushro.message.vo.ConfigFieldVO;
import com.bushro.message.vo.MultiObjFieldVO;
import com.bushro.message.vo.SchemeFieldVO;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

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
     * 方案类型映射
     */
    public static Map<Class<?>, SchemeValueType> JAVA_TYPE_SCHEME_MAP = new HashMap<>();

    static {
        JAVA_TYPE_SCHEME_MAP.put(int.class, SchemeValueType.INTEGER);
        JAVA_TYPE_SCHEME_MAP.put(long.class, SchemeValueType.INTEGER);
        JAVA_TYPE_SCHEME_MAP.put(short.class, SchemeValueType.INTEGER);
        JAVA_TYPE_SCHEME_MAP.put(char.class, SchemeValueType.INTEGER);
        JAVA_TYPE_SCHEME_MAP.put(Integer.class, SchemeValueType.INTEGER);
        JAVA_TYPE_SCHEME_MAP.put(Long.class, SchemeValueType.INTEGER);
        JAVA_TYPE_SCHEME_MAP.put(Short.class, SchemeValueType.INTEGER);
        JAVA_TYPE_SCHEME_MAP.put(Character.class, SchemeValueType.INTEGER);
        JAVA_TYPE_SCHEME_MAP.put(double.class, SchemeValueType.DECIMAL);
        JAVA_TYPE_SCHEME_MAP.put(Double.class, SchemeValueType.DECIMAL);
        JAVA_TYPE_SCHEME_MAP.put(float.class, SchemeValueType.DECIMAL);
        JAVA_TYPE_SCHEME_MAP.put(Float.class, SchemeValueType.DECIMAL);
        JAVA_TYPE_SCHEME_MAP.put(BigDecimal.class, SchemeValueType.DECIMAL);
        JAVA_TYPE_SCHEME_MAP.put(boolean.class, SchemeValueType.BOOLEAN);
        JAVA_TYPE_SCHEME_MAP.put(Boolean.class, SchemeValueType.BOOLEAN);
        JAVA_TYPE_SCHEME_MAP.put(String.class, SchemeValueType.STRING);
        JAVA_TYPE_SCHEME_MAP.put(List.class, SchemeValueType.MULTI_OBJ_INPUT);
        JAVA_TYPE_SCHEME_MAP.put(Set.class, SchemeValueType.MULTI_OBJ_INPUT);
        JAVA_TYPE_SCHEME_MAP.put(Collection.class, SchemeValueType.MULTI_OBJ_INPUT);
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
                List<IdStrAndName> options = new ArrayList<>();
                if (annotation != null) {
                    type = annotation.type();
                    name = annotation.value();
                    description = annotation.description();
                    switch (type) {
                        case AUTO:
                            type = JAVA_TYPE_CONFIG_MAP.get(field.getType());
                            break;
                    }
                    if (ConfigValueType.BOOLEAN.equals(type)) {
                        options.add(IdStrAndName.builder().id("true").name("true").build());
                        options.add(IdStrAndName.builder().id("false").name("false").build());
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
                        .options(options)
                        .build());
            }
            return fieldNames;
        });
    }

    // ====================================================== 方案相关 ==============================================================

    /**
     * 根据消息类型获取对应方案所有字段
     *
     * @param messageType 消息类型
     * @return 字段列表
     */
    public static List<SchemeFieldVO> listSchemeField(MessageTypeEnum messageType) {
        return SingletonUtil.get(messageType + SchemeFieldVO.class.getName(), () -> {
            IMessageHandler messageHandler = MessageHandlerHolder.get(messageType);
            ParameterizedType genericSuperclass = (ParameterizedType) messageHandler.getClass().getGenericSuperclass();
            Class<?> schemeType = (Class<?>) genericSuperclass.getActualTypeArguments()[0];
            Field[] fields = ReflectUtil.getFieldsDirectly(schemeType, true);
            if (fields == null || fields.length <= 0) {
                return Collections.emptyList();
            }
            List<SchemeFieldVO> fieldNames = new ArrayList<>();
            getFieldNames(fieldNames, fields);
            return fieldNames.stream()
                    .sorted(Comparator.comparing(SchemeFieldVO::getOrder))
                    .collect(Collectors.toList());
        });
    }

    private static void getFieldNames(List<SchemeFieldVO> fieldNames, Field[] fields) {
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            if (Modifier.isFinal(field.getModifiers())) {
                continue;
            }
            SchemeValue annotation = field.getAnnotation(SchemeValue.class);
            List<IdStrAndName> idStrAndNames = new ArrayList<>();
            if (annotation == null) {
                continue;
            }
            SchemeValueType type = annotation.type();
            String name = annotation.value();
            String description = annotation.description();
            switch (type) {
                case AUTO:
                    type = JAVA_TYPE_SCHEME_MAP.get(field.getType());
                    break;
            }

            SchemeValueOption[] options = annotation.options();
            for (SchemeValueOption option : options) {
                idStrAndNames.add(new IdStrAndName(option.key(), option.label()));
            }
            List<MultiObjFieldVO> multiObjFields = new ArrayList<>();
            if (SchemeValueType.MULTI_OBJ_INPUT.equals(type)) {
                // 多对象输入
                ParameterizedType genericType = (ParameterizedType) field.getGenericType();
                Class<?> multiObjClass = (Class<?>) genericType.getActualTypeArguments()[0];
                Field[] multiObjFieldArr = ReflectUtil.getFieldsDirectly(multiObjClass, false);
                for (Field fieldMulti : multiObjFieldArr) {
                    if (Modifier.isFinal(field.getModifiers())) {
                        continue;
                    }
                    MultiObjField fieldAnnotation = fieldMulti.getAnnotation(MultiObjField.class);
                    if (fieldAnnotation != null) {
                        multiObjFields.add(MultiObjFieldVO.builder()
                                .description(fieldAnnotation.description())
                                .key(fieldMulti.getName())
                                .label(fieldAnnotation.value())
                                .build());
                    } else {
                        multiObjFields.add(MultiObjFieldVO.builder()
                                .description(fieldMulti.getName())
                                .key(fieldMulti.getName())
                                .label(fieldMulti.getName())
                                .build());
                    }
                }
            }
            name = StringUtils.isBlank(name) ? field.getName() : name;
            fieldNames.add(SchemeFieldVO.builder()
                    .name(name)
                    .description(description)
                    .type(type)
                    .order(annotation.order() == Integer.MAX_VALUE ? i : annotation.order())
                    .key(field.getName())
                    .options(idStrAndNames)
                    .multiObjFields(multiObjFields)
                    .value(field.getName()+ "Value")
                    .build());
        }
    }


}
