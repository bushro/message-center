package com.bushro.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bushro.common.core.enums.MessageEnum;
import com.bushro.common.core.util.AssertUtil;
import com.bushro.common.core.util.R;
import com.bushro.system.domain.Oauth2TokenDto;
import com.bushro.system.entity.SysUser;
import com.bushro.system.form.LoginForm;
import com.bushro.system.mapper.SysUserMapper;
import com.bushro.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.LinkedHashMap;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {


    @Resource
    private RestTemplate restTemplate;

    @Override
    public R<Oauth2TokenDto> login(LoginForm loginForm) {
//        // 参数校验
//        AssertUtil.isNotEmpty(loginForm.getUsername(), "请输入登录帐号");
//        AssertUtil.isNotEmpty(loginForm.getPassword(), "请输入登录密码");
//        // 构建请求头
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        // 构建请求体（请求参数）
//        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
//        body.add("username", loginForm.getUsername());
//        body.add("password", loginForm.getPassword());
//        body.setAll(BeanUtil.beanToMap(oAuth2ClientConfig));
//        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);
//        // 设置 Authorization
//        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor(oAuth2ClientConfig.getClientId(),
//                oAuth2ClientConfig.getSecret()));
//        // 发送请求
//        ResponseEntity<R> result = restTemplate.postForEntity(oauthServerName + "oauth/token", entity, R.class);
//        // 处理返回结果
//        AssertUtil.isTrue(result.getStatusCode() != HttpStatus.OK, "登录失败");
//        R resultInfo = result.getBody();
//        if (resultInfo.getCode() != MessageEnum.SUCCESS.code()) {
//            // 登录失败
//            resultInfo.setData(resultInfo.getMessage());
//            return resultInfo;
//        }
//        // 这里的 Data 是一个 LinkedHashMap 转成了域对象 Oauth2TokenDto
//        Oauth2TokenDto tokenDto = BeanUtil.fillBeanWithMap((LinkedHashMap) resultInfo.getData(),
//                new Oauth2TokenDto(), false);
//        return R.ok(tokenDto);
        return null;
    }
}
