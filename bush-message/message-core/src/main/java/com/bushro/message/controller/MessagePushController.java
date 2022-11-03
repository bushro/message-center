package com.bushro.message.controller;

import com.bushro.common.core.util.R;
import com.bushro.common.idempotent.annotation.Idempotent;
import com.bushro.message.dto.MessagePushDTO;
import com.bushro.message.service.IMessagePushService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 统一发送消息接口
 */
@RestController
@RequestMapping("/msg")
public class MessagePushController {


    @Resource
    private IMessagePushService messagePushService;


    @PostMapping("/push")
    public R<String> push(@RequestBody String param) {
        return messagePushService.push(param);
    }


    @GetMapping("/get")
    public String get() {
        return "success";
    }

    @Idempotent(key = "#test.requestNo",expireTime = 1000, info = "请勿重复查询")
    @PostMapping("/test")
    public String test(@RequestBody MessagePushDTO test) {
//        CorpLinkMessageHandler bean = SpringContextHolder.getBean(CorpLinkMessageHandler.class);
//        LinkMessageDTO messageDTO = new LinkMessageDTO();
//        messageDTO.setTitle("测试");
//        bean.setBaseMessage(messageDTO);
//        ThreadPoolUtil.getThreadPool().submit(bean);
        return "success";
    }
}
