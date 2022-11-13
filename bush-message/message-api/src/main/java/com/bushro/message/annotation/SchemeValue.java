package com.bushro.message.annotation;


import com.bushro.message.enums.SchemeValueType;

import java.lang.annotation.*;


/**
 * 标记字段为配置值
 *
 * @author luo.qiang
 * @date 2022/11/11
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SchemeValue {

    /**
     * 字段名称
     */
    String value() default "";

    /**
     * 字段描述
     */
    String description() default "";

    /**
     * 默认取对应的java类型
     */
    SchemeValueType type() default SchemeValueType.AUTO;

    /**
     * 展示的顺序
     */
    int order() default Integer.MAX_VALUE;

    /**
     * 如果是选项型，这里可以直接指定有哪些选项
     */
    SchemeValueOption[] options() default {};

}
