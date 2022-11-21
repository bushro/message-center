package com.bushro.system.feign;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bushro.common.core.util.R;
import com.bushro.system.entity.SysFile;
import com.bushro.system.service.ISysFileService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

/**
 * @description:
 * @author: luoq
 * @date: 2022/11/21
 */
@ApiIgnore
@Api(tags = "RPC远程服务-文件")
@RestController
@RequestMapping("/system-server/sysFileClient")
public class SysFileClientFeignService implements ISysFileClientFeignApi {

    @Resource
    private ISysFileService sysFileService;


    @Override
    public R<SysFile> getOneByFileName(String fileName) {
        SysFile sysFile = sysFileService.getOne(Wrappers.<SysFile>lambdaQuery().eq(SysFile::getFileName, fileName));
        return R.ok(sysFile);
    }
}
