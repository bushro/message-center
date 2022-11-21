package com.bushro.system.feign;


import com.bushro.common.core.util.R;
import com.bushro.system.feign.fallback.ISysOauthClientFeignFallback;
import com.bushro.system.vo.SysOauthClientVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * <p>
 * oauth客户端
 * </p>
 *
 * @author luo.qiang
 * @description
 * @date 2021/8/19 11:22
 */
@FeignClient(value = "system-server",
        path = "/system-server/oauthClient",
        contextId = "ISysOauthClientFeignApi",
        fallback = ISysOauthClientFeignFallback.class)
public interface ISysOauthClientFeignApi {

    /**
     * 详情
     *
     * @param clientId 客户端ID
     * @return 详情
     * @author luo.qiang
     * @date 2022/11/16 22:35
     */
    @GetMapping("/getClient/{clientId}")
    R<SysOauthClientVO> getClient(@PathVariable(value = "clientId") String clientId);

}
