package com.bushro.message.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.bushro.common.core.util.R;
import com.bushro.message.service.IWechatService;

import javax.annotation.Resource;

/**
 * 微信
 */

@RestController
@RequestMapping("/news")
public class WechatController {
    @Resource
    private IWechatService wechatServer;

    @PostMapping("/wechatUpload")
    public R push(@RequestParam(value = "configId") String configId,
                             @RequestParam(value = "fileType") String fileType,
                             @RequestParam(value = "media") MultipartFile media) {
        return wechatServer.upload(configId, fileType, media);
    }

}
