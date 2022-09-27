package com.bushro.message.service;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.bushro.common.core.util.R;

/**
 * 微信接口
 */
public interface IWechatService {


    /**
     * @descript : 调用官方上传临时素材接口，获取返回信息
     * @author : luoq
     * @date : 2021-10-15
     * @param configId : 配置id
     * @param fileType : 文件类型，请看{@link me.chanjar.weixin.common.api.WxConsts.MediaFileType}
     * @param media :
     * @return : com.bushro.common.entity.R
     */

    R upload(String configId, String fileType, MultipartFile media);
}
