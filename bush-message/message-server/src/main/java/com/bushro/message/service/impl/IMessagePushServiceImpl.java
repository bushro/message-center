package com.bushro.message.service.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.bushro.message.consumer.DisruptorConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.bushro.common.core.util.R;
import com.bushro.message.dto.MessagePushDTO;
import com.bushro.message.dto.TypeMessageDTO;
import com.bushro.message.enums.MessageErrorEnum;
import com.bushro.message.enums.MessageTypeEnum;
import com.bushro.message.factory.DisruptorFactory;
import com.bushro.message.service.IMessagePushService;

import javax.annotation.Resource;
import java.util.Collections;

/**
 * 消息推送
 */
@Service
@Slf4j
public class IMessagePushServiceImpl implements IMessagePushService {


    @Resource
    private DisruptorConsumerService consumerService;

    @Override
    public R<String> push(String param) {
        param = param.replaceAll("[\b\r\n\t]*", "");
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
            messagePushDTO.setMessageTypeEnum(value);
            messagePushDTO.setMessageDTO(typeMessageDTO);
        }
        DisruptorFactory<MessagePushDTO> disruptorFactory = new DisruptorFactory<>();
        disruptorFactory.push(messagePushDTO, MessagePushDTO::new,
                consumerService);
        return R.ok(requestNo, MessageErrorEnum.PUSH_SUCCESS.message());
    }
}
