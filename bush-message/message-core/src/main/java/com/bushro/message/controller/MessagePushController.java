package com.bushro.message.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.bushro.common.core.util.R;
import com.bushro.message.service.IMessagePushService;

import javax.annotation.Resource;

/**
 * 统一发送消息接口
 */
@RestController
@RequestMapping("/news")
public class MessagePushController {


    @Resource
    private IMessagePushService messagePushService;


    @PostMapping("/_push")
    public R<String> push(@RequestBody String param) {
        return messagePushService.push(param);
    }

    @GetMapping("/get")
    public String push() {
        return "success";
    }

//    @PostMapping("/_upload")
//    public R<SysFile> upload(@RequestParam("media") MultipartFile file) {
//        SysFile upload = messagePushService.upload(file);
//        return R.ok(upload);
//    }
}
