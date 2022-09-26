package com.bushro.message.controller;

import com.bushro.common.core.util.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.bushro.message.service.IDingTalkService;

import javax.annotation.Resource;

/**
 * 钉钉
 */
@RestController
@RequestMapping("/news")
public class DingTalkController {
    @Resource
    private IDingTalkService dingTalkService;

    @PostMapping("/dingTalkUpload")
    public R push(@RequestParam(value = "configId") String configId,
                  @RequestParam(value = "fileType") String fileType,
                  @RequestParam(value = "media") MultipartFile media) {
        return dingTalkService.upload(configId, fileType, media);
    }
}
