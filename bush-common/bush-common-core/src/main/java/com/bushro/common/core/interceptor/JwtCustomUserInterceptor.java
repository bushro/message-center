package com.bushro.common.core.interceptor;

import com.alibaba.fastjson.JSONObject;

import com.bushro.common.core.constant.BaseConstant;
import com.bushro.common.core.context.JwtCustomUserContext;
import com.bushro.common.core.context.SysUserContext;
import com.bushro.common.core.context.UmsUserContext;
import com.bushro.common.core.enums.AuthSourceEnum;
import com.bushro.common.core.enums.IBaseEnum;
import com.bushro.common.core.model.dto.JwtCustomUserDto;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;


/**
 * 拦截器 -- jwt自定义用户信息
 *
 * @author luo.qiang
 * @date 2022/11/18
 */
public class JwtCustomUserInterceptor implements HandlerInterceptor {

    /**
     * 在业务处理器处理请求之前被调用。预处理，可以进行编码、安全控制、权限校验等处理
     * {@link com.bushro.gateway.filter.AuthGlobalFilter#filter }
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userJson = request.getHeader(BaseConstant.REQUEST_HEADER_JWT_USER);
        if (StringUtils.isBlank(userJson)) {
            return true;
        }
        userJson = URLDecoder.decode(userJson, "UTF-8");
        JwtCustomUserDto user = JSONObject.parseObject(userJson, JwtCustomUserDto.class);
        String sysUserId = user.getSysUserId();
        String umsUserId = user.getUmsUserId();
        String username = user.getUsername();
        AuthSourceEnum authSourceEnum = IBaseEnum.getEnumByValue(user.getAuthSource(), AuthSourceEnum.class);
        switch (authSourceEnum) {
            case B:
                SysUserContext.setUserId(Integer.valueOf(sysUserId));
                SysUserContext.setUsername(username);
                break;
            case C:
                UmsUserContext.setUserId(Long.valueOf(umsUserId));
                UmsUserContext.setUsername(username);
                break;
            default:
                break;
        }
        JwtCustomUserContext.set(user);
        return true;
    }

    /**
     * 在业务处理器处理请求执行完成后，生成视图之前执行。
     * 后处理（调用了Service并返回ModelAndView，但未进行页面渲染），有机会修改ModelAndView
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    /**
     * 在DispatcherServlet完全处理完请求后被调用，可用于清理资源等。返回处理（已经渲染了页面）
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
        SysUserContext.remove();
        UmsUserContext.remove();
        JwtCustomUserContext.remove();
    }

}
