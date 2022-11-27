package com.bushro.system.feign.fallback;

import com.bushro.common.core.util.R;
import com.bushro.system.entity.SysFile;
import com.bushro.system.feign.ISysFileClientFeignApi;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: luoq
 * @date: 2022/11/21
 */
@Component
public class ISysFileClientFeignFallback implements ISysFileClientFeignApi {

    @Override
    public R<SysFile> getOneByFileName(String fileName) {
        return R.failed("文件不存在！");
    }

}
