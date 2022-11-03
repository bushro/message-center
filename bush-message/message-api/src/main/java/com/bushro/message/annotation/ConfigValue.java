package com.bushro.message.annotation;


import com.bushro.message.enums.ConfigValueType;

import java.lang.annotation.*;


/**
 * 标记字段为配置值
 *
 * @author luo.qiang
 * @date 2022/11/03
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ConfigValue {

    /**
     * 字段中文说明, 就是前端的表头的名称
     */
    String value() default "";

    /**
     * 字段描述
     */
    String description() default "";

    /**
     * 默认取对应的java类型
     */
    ConfigValueType type() default ConfigValueType.AUTO;

}
