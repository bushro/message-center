package com.bushro.system.feign;


import com.bushro.common.core.util.R;
import com.bushro.system.service.ISysOauthClientService;
import com.bushro.system.vo.SysOauthClientVO;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

/**
 * <p> oauth客户端 </p>
 *
 * @author luo.qiang
 * @description
 * @date 2021/8/19 11:25
 */
@ApiIgnore
@Api(tags = "RPC远程服务-oauth客户端")
@RestController
@RequestMapping("/system-server/oauthClient")
public class SysOauthClientFeignService implements ISysOauthClientFeignApi {

    @Resource
    private ISysOauthClientService sysOauthClientService;

    @Override
    public R<SysOauthClientVO> getClient(String clientId) {
//        int aa = 1 / 0;
        return R.ok(this.sysOauthClientService.detail(clientId));
    }

}
