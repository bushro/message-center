package com.bushro.common.core.util;

import cn.hutool.core.util.StrUtil;
import com.bushro.common.core.enums.MessageEnum;
import com.bushro.common.core.exception.BusinessException;

/**
 * 断言工具类
 */
public class AssertUtil {

    /**
     * 必须登录
     *
     * @param accessToken
     */
    public static void mustLogin(String accessToken) {
        if (StrUtil.isBlank(accessToken)) {
            throw new BusinessException(MessageEnum.UN_AUTHORIZED);
        }
    }

    /**
     * 判断字符串非空
     *
     * @param str
     * @param message
     */
    public static void isNotEmpty(String str, String... message) {
        if (StrUtil.isBlank(str)) {
            execute(message);
        }
    }

    /**
     * 判断对象非空
     *
     * @param obj
     * @param message
     */
    public static void isNotNull(Object obj, String... message) {
        if (obj == null) {
            execute(message);
        }
    }

    /**
     * 判断结果是否为真
     *
     * @param isTrue
     * @param message
     */
    public static void isTrue(boolean isTrue, String... message) {
        if (isTrue) {
            execute(message);
        }
    }

    /**
     * 最终执行方法
     *
     * @param message
     */
    private static void execute(String... message) {
        String msg = MessageEnum.ERROR.message();
        if (message != null && message.length > 0) {
            msg = message[0];
        }
        throw new BusinessException(msg);
    }

}
