package com.bushro.message.utils;

import cn.hutool.core.util.StrUtil;
import com.bushro.common.redis.util.RedisUtil;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 钉钉token获取工具类
 *
 * @author luo.qiang
 * @date 2022/11/20
 */

@Slf4j
public class AccessTokenUtils {
    public static final String REDIS_DINGTALK_TOKEN_PREFIX = "DINGTALK_TOKEN:";

    /**
     * 获取访问令牌
     *
     * 在使用access_token时，请注意：
     * access_token的有效期为7200秒（2小时），有效期内重复获取会返回相同结果并自动续期，过期后获取会返回新的access_token。
     * 开发者需要缓存access_token，用于后续接口的调用。因为每个应用的access_token是彼此独立的，所以进行缓存时需要区分应用来进行存储。
     * 不能频繁调用gettoken接口，否则会受到频率拦截。
     *
     * @param appKey    应用关键
     * @param appSecret 应用程序秘密
     * @return {@link String}
     * @throws ApiException api例外
     */
    public static String getAccessToken(String appKey, String appSecret) throws ApiException {
        String accessToken = RedisUtil.get(REDIS_DINGTALK_TOKEN_PREFIX + appKey);
        if (StrUtil.isEmpty(accessToken)) {
            DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
            OapiGettokenRequest request = new OapiGettokenRequest();
            request.setAppkey(appKey);
            request.setAppsecret(appSecret);
            request.setHttpMethod("GET");
            OapiGettokenResponse response;
            response = client.execute(request);
            log.info("获取到的钉钉token信息：{}", response.getBody());
            RedisUtil.setEx(REDIS_DINGTALK_TOKEN_PREFIX + appKey, response.getAccessToken(), 7200, TimeUnit.SECONDS);
            accessToken = response.getAccessToken();
        }
        return accessToken;
    }

}
