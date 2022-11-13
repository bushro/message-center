package com.bushro.message.annotation;

import java.lang.annotation.*;


/**
 * 方案价值选择
 *
 * @author luo.qiang
 * @date 2022/11/11
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface SchemeValueOption {

    String key();

    String label();

}
