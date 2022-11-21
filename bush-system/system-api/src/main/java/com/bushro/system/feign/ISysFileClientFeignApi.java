package com.bushro.system.feign;

import com.bushro.common.core.util.R;
import com.bushro.system.entity.SysFile;
import com.bushro.system.feign.fallback.ISysFileClientFeignFallback;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @description:
 * @author: luoq
 * @date: 2022/11/21
 */
@FeignClient(value = "system-server",
        path = "/system-server/sysFileClient",
        contextId = "ISysFileClientFeignApi",
        fallback = ISysFileClientFeignFallback.class)
public interface ISysFileClientFeignApi {

    /**
     * 得到一个文件名
     *
     * @param fileName 文件名称
     * @return {@link SysFile}
     */
    @ApiOperation(value = "通过文件名查询")
    @GetMapping("/getOneByFileName/{fileName}")
    R<SysFile> getOneByFileName(@PathVariable(value = "fileName") String fileName);
}
