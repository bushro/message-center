package com.bushro.common.core.context;

import com.bushro.common.core.enums.AuthSourceEnum;
import lombok.extern.slf4j.Slf4j;

/**
 * <p> 认证来源上下文 </p>
 *
 * @author luo.qiang
 * @description {@link AuthSourceEnum}
 * @date 2021/6/30 9:24 下午
 */
@Slf4j
public class AuthSourceContext {

    public static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<>();

    public static void set(String authSource) {
        THREAD_LOCAL.set(authSource);
    }

    public static String get() {
        return THREAD_LOCAL.get();
    }

    public static void remove() {
        THREAD_LOCAL.remove();
    }

}
