package com.bushro.message.service.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.bushro.service.OssTemplate;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.bushro.common.core.util.R;
import com.bushro.message.dto.MessagePushDTO;
import com.bushro.message.dto.TypeMessageDTO;
import com.bushro.message.enums.MessageErrorEnum;
import com.bushro.message.enums.MessageTypeEnum;
import com.bushro.message.factory.DisruptorFactory;
import com.bushro.message.handle.AbstractMessageHandler;
import com.bushro.message.handle.MessageHandlerHolder;
import com.bushro.message.service.IMessagePushService;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.concurrent.Executors;

/**
 * 消息推送
 */
@Service
@Slf4j
public class IMessagePushServiceImpl implements IMessagePushService {




    @Override
    public R<String> push(String param) {
        MessagePushDTO messagePushDTO = new MessagePushDTO();
        JSONObject json = new JSONObject(param);
        String requestNo = json.getStr("requestNo");
        if (StringUtils.isBlank(requestNo)) {
            // 如果客户端投递消息时没有传唯一请求号，这里自动生成一个,
            // 后面这个需要客户端先请求接口，一定需要携带。
            requestNo = IdWorker.getIdStr();
        }
        messagePushDTO.setRequestNo(requestNo);
        messagePushDTO.setParam(param);
        JSONObject messageParam = json.getJSONObject("messageParam");
        if (messageParam == null) {
            return R.failed(MessageErrorEnum.PUSH_PARAM_ERROR.message());
        }

        MessageTypeEnum[] values = MessageTypeEnum.values();
        for (MessageTypeEnum value : values) {
            JSONObject jsonObject = messageParam.getJSONObject(value.name());
            if (jsonObject == null) {
                continue;
            }
            JSONArray configIds = jsonObject.getJSONArray("configIds");
            TypeMessageDTO typeMessageDTO = TypeMessageDTO.builder()
                .configIds(configIds == null ? Collections.EMPTY_LIST : configIds.toList(Long.TYPE))
                .param(jsonObject.getJSONObject("param"))
                .build();
            messagePushDTO.getMessageParam().put(value, typeMessageDTO);
            messagePushDTO.setMessageTypeEnum(value);
            messagePushDTO.setMessageDTO(typeMessageDTO);
        }
        if (messagePushDTO.getMessageParam() == null || messagePushDTO.getMessageParam().size() <= 0) {
            return R.failed(MessageErrorEnum.PUSH_PARAM_ERROR.message());
        }
        DisruptorFactory<MessagePushDTO> disruptorFactory = new DisruptorFactory<>();
        disruptorFactory.push(messagePushDTO, MessagePushDTO::new,
            MessageHandlerHolder.values().toArray(new AbstractMessageHandler[0]));
        return R.ok(requestNo, MessageErrorEnum.PUSH_SUCCESS.message());
    }
}
