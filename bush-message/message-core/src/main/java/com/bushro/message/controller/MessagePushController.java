package com.bushro.message.controller;

import com.bushro.common.core.util.SpringContextHolder;
import com.bushro.message.dto.dingtalk.corp.LinkMessageDTODing;
import com.bushro.message.handle.dingtalk.corp.CorpLinkMessageHandler;
import com.bushro.message.utils.ThreadPoolUtil;
import org.springframework.web.bind.annotation.*;
import com.bushro.common.core.util.R;
import com.bushro.message.service.IMessagePushService;

import javax.annotation.Resource;

/**
 * 统一发送消息接口
 */
@RestController
@RequestMapping("/message")
public class MessagePushController {


    @Resource
    private IMessagePushService messagePushService;


    @PostMapping("/_push")
    public R<String> push(@RequestBody String param) {
        return messagePushService.push(param);
    }

    @GetMapping("/get")
    public String push() {
        CorpLinkMessageHandler bean = SpringContextHolder.getBean(CorpLinkMessageHandler.class);
        LinkMessageDTODing messageDTO = new LinkMessageDTODing();
        messageDTO.setTitle("测试");
        bean.setBaseMessage(messageDTO);
        ThreadPoolUtil.getThreadPool().submit(bean);
        return "success";
    }
}
