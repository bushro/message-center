package com.bushro.common.core.constant;


/**
 * <p>
 * 全局常用变量 - base
 * </p>
 *
 * @author luo.qiang
 * @description
 * @date 2019/10/12 14:47
 */
public interface BaseConstant {

    /**
     * 第一级父类id
     */
    Integer PARENT_ID = 0;

    /**
     * 用户ID、用户名、消息上下文Key 【注：key名称一定不要和前端请求参数中的属性名一样，否则会拿不到真正的值！！！】
     */
    String CONTEXT_KEY_SYS_USER_ID = "bush_sys_user_id";
    String CONTEXT_KEY_UMS_USER_ID = "bush_ums_user_id";
    String CONTEXT_KEY_USERNAME = "bush_username";
    String DEFAULT_CONTEXT_KEY_USER_ID = "0";
    String DEFAULT_CONTEXT_KEY_USERNAME = "未知";

    /**
     * 请求头中的用户信息标识
     */
    String REQUEST_HEADER_JWT_USER = "JWT_USER";

}
