package com.bushro.message.controller;

import com.bushro.common.core.util.R;
import com.bushro.common.idempotent.annotation.Idempotent;
import com.bushro.message.base.IdStrAndName;
import com.bushro.message.dto.MessagePushDTO;
import com.bushro.message.enums.MessagePlatformEnum;
import com.bushro.message.enums.MessageTypeEnum;
import com.bushro.message.service.IMessagePushService;
import com.bushro.message.utils.MessageHandlerUtils;
import com.bushro.message.vo.SchemeFieldVO;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * 统一发送消息接口
 */
@RestController
@Api(tags = "消息推送中心")
@RequestMapping("/msg")
public class MessagePushController {


    @Resource
    private IMessagePushService messagePushService;


    @ApiOperation(value = "发送消息")
    @PostMapping("/push")
    public R<String> push(@RequestBody String param) {
        return messagePushService.push(param);
    }

    @ApiOperation("消息类型字段")
    @GetMapping("/field")
    public R<List<SchemeFieldVO>> field(@NotNull(message = "未知消息类型") MessageTypeEnum messageType) {
        return R.ok(MessageHandlerUtils.listSchemeField(messageType));
    }

    @ApiOperation("平台消息类型")
    @GetMapping("/type")
    public R<List<IdStrAndName>> allType(@NotNull(message = "未知平台") MessagePlatformEnum platform) {
        List<IdStrAndName> results = new ArrayList<>();
        MessageTypeEnum[] values = MessageTypeEnum.values();
        for (MessageTypeEnum value : values) {
            if (!value.getPlatform().equals(platform)) {
                continue;
            }
            results.add(new IdStrAndName(value.name(), value.getShortName()));
        }
        return R.ok(results);
    }

    @ApiOperation(value = "测试")
    @GetMapping("/get")
    public List<Long> get() {
        List<Long> list = new ArrayList<>();
        list.add(4123412341234L);
        list.add(12341234324324324L);
        list.add(2342342342423423L);
        return list;
    }

    @Idempotent(key = "#test.requestNo", expireTime = 1000, info = "请勿重复查询")
    @PostMapping("/test")
    public String test(@RequestBody MessagePushDTO test) {
        return "success";
    }
}
