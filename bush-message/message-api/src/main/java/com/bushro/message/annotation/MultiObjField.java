package com.bushro.message.annotation;

import java.lang.annotation.*;

/**
 * 多对象输入字段
 *
 * @author luo.qiang
 * @date 2022/11/11
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MultiObjField {

    /**
     * 字段名称
     */
    String value() default "";

    /**
     * 字段描述
     */
    String description() default "";

}
