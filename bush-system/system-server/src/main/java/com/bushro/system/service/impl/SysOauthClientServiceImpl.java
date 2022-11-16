package com.bushro.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bushro.system.entity.SysOauthClient;
import com.bushro.system.mapper.SysOauthClientMapper;
import com.bushro.system.service.ISysOauthClientService;
import com.bushro.system.vo.SysOauthClientVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p> oauth客户端 服务实现类 </p>
 *
 * @author luo.qiang
 * @description
 * @date 2022/11/16 22:35
 */
@Slf4j
@Service
public class SysOauthClientServiceImpl extends ServiceImpl<SysOauthClientMapper, SysOauthClient> implements ISysOauthClientService {

    @Resource
    private SysOauthClientMapper sysOauthClientMapper;

    @Override
    public SysOauthClientVO detail(String clientId) {
        SysOauthClient oauthClient = this.getOne(Wrappers.<SysOauthClient>lambdaQuery().eq(SysOauthClient::getClientId, clientId));
        SysOauthClientVO sysOauthClientVO = new SysOauthClientVO();
        Assert.notNull(oauthClient, "该数据不存在！");
        BeanUtil.copyProperties(oauthClient, sysOauthClientVO);
        return sysOauthClientVO;
    }

}
