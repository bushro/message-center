package com.bushro.message.service;

import org.springframework.web.multipart.MultipartFile;
import com.bushro.common.core.util.R;

/**
 * 钉钉
 */
public interface IDingTalkService {

    /**
     * @descript : 调用官方上传临时素材接口，获取返回信息
     * @author : luoq
     * @date : 2022-10-16
     * @param configId : 配置id
     * @param fileType : 文件类型，媒体文件类型：image：图片、voice：语音、file：普通文件、video：视频
     * @param media :
     * @return : com.bushro.common.entity.R
     */

    R upload(String configId, String fileType, MultipartFile media);
}
