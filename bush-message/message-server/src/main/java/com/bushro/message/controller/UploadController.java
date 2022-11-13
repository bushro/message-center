package com.bushro.message.controller;

import com.bushro.common.core.util.R;
import com.bushro.message.service.IDingTalkService;
import com.bushro.message.service.IWechatService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;


/**
 * 钉钉微信上传文件
 *
 * @author luo.qiang
 * @date 2022/10/24
 */
@RestController
@RequestMapping("/upload")
public class UploadController {
    @Resource
    private IWechatService wechatServer;

    @Resource
    private IDingTalkService dingTalkService;


    /**
     * 钉钉上传文件
     *
     * @param configId 配置id
     * @param fileType 文件类型
     * @param media    媒体
     * @return {@link R}
     */
    @PostMapping("/dingTalkUpload")
    public R dingTalkUpload(@RequestParam(value = "configId") String configId,
                            @RequestParam(value = "fileType") String fileType,
                            @RequestParam(value = "media") MultipartFile media) {
        return dingTalkService.upload(configId, fileType, media);
    }

    /**
     * 微信上传文件
     *
     * @param configId 配置id
     * @param fileType 文件类型
     * @param media    媒体
     * @return {@link R}
     */
    @PostMapping("/wechatUpload")
    public R wechatUpload(@RequestParam(value = "configId") String configId,
                          @RequestParam(value = "fileType") String fileType,
                          @RequestParam(value = "media") MultipartFile media) {
        return wechatServer.upload(configId, fileType, media);
    }
}
