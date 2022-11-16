package com.bushro.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bushro.system.entity.SysOauthClient;
import com.bushro.system.vo.SysOauthClientVO;

/**
 * <p>  oauth客户端 服务类 </p>
 *
 * @author luo.qiang
 * @description
 * @date 2022/11/16 22:35
 */
public interface ISysOauthClientService extends IService<SysOauthClient> {

    /**
     * 详情
     *
     * @param clientId 客户端ID
     * @return 详情
     * @author luo.qiang
     * @date 2022/11/16 22:35
     */
    SysOauthClientVO detail(String clientId);


}
