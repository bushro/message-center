package com.bushro.common.core.context;


import com.bushro.common.core.constant.BaseConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * <p> C端用户上下文 </p>
 *
 * @author luo.qiang
 * @description 请务必在请求结束时, 调用 @Method remove()
 * @date 2022/8/1 19:07
 */
public class UmsUserContext {

    public static final ThreadLocal<Map<String, Object>> THREAD_LOCAL = new ThreadLocal<>();

    public static Object get(String key) {
        Map<String, Object> map = THREAD_LOCAL.get();
        if (map == null) {
            return null;
        }
        return map.get(key);
    }

    public static Long getUserId() {
        Object value = get(BaseConstant.CONTEXT_KEY_UMS_USER_ID);
        return value == null ? Long.valueOf(BaseConstant.DEFAULT_CONTEXT_KEY_USER_ID) : (Long) value;
    }

    public static String getUsername() {
        Object value = get(BaseConstant.CONTEXT_KEY_USERNAME);
        return value == null ? BaseConstant.DEFAULT_CONTEXT_KEY_USERNAME : (String) value;
    }

    public static void setUserId(Long userId) {
        set(BaseConstant.CONTEXT_KEY_UMS_USER_ID, userId);
    }

    public static void setUsername(String username) {
        set(BaseConstant.CONTEXT_KEY_USERNAME, username);
    }

    private static void set(String key, Object value) {
        Map<String, Object> map = THREAD_LOCAL.get();
        if (map == null) {
            map = new HashMap<>(1);
            THREAD_LOCAL.set(map);
        }
        map.put(key, value);
    }

    public static void remove() {
        THREAD_LOCAL.remove();
    }

}
