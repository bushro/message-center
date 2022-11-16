package com.bushro.system.feign.fallback;


import com.bushro.common.core.util.R;
import com.bushro.system.feign.ISysOauthClientFeignApi;
import com.bushro.system.vo.SysOauthClientVO;
import org.springframework.stereotype.Component;

/**
 * <p>
 * oauth客户端
 * </p>
 *
 * @author luo.qiang
 * @description
 * @date 2021/8/19 11:22
 */
@Component
public class ISysOauthClientFeignFallback implements ISysOauthClientFeignApi {

    @Override
    public R<SysOauthClientVO> getClient(String clientId) {
        return R.busy();
    }
}
