package com.bushro.common.core.context;


import com.bushro.common.core.model.dto.JwtCustomUserDto;

/**
 * <p> jwt自定义用户信息上下文 </p>
 *
 * @author luo.qiang
 * @description 请务必在请求结束时, 调用 @Method remove()
 * @date 2022/8/1 19:07
 */
public class JwtCustomUserContext {

    public static final ThreadLocal<JwtCustomUserDto> THREAD_LOCAL = new ThreadLocal<>();

    public static JwtCustomUserDto get() {
        return THREAD_LOCAL.get();
    }

    public static void set(JwtCustomUserDto jwtCustomUserDto) {
        THREAD_LOCAL.set(jwtCustomUserDto);
    }

    public static void remove() {
        THREAD_LOCAL.remove();
    }

}
