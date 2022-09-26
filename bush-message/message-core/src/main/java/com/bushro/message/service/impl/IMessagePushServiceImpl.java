package com.bushro.message.service.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.bushro.common.core.util.R;
import com.bushro.message.dto.MessagePushDTO;
import com.bushro.message.dto.TypeMessageDTO;
import com.bushro.message.enums.MessageErrorEnum;
import com.bushro.message.enums.MessageTypeEnum;
import com.bushro.message.factory.DisruptorFactory;
import com.bushro.message.handle.MessageHandler;
import com.bushro.message.handle.MessageHandlerHolder;
import com.bushro.message.service.IMessagePushService;
import java.util.Collections;

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
        }
        if (messagePushDTO.getMessageParam() == null || messagePushDTO.getMessageParam().size() <= 0) {
            return R.failed(MessageErrorEnum.PUSH_PARAM_ERROR.message());
        }
        DisruptorFactory<MessagePushDTO> disruptorFactory = new DisruptorFactory<>();
        disruptorFactory.push(messagePushDTO, MessagePushDTO::new,
            MessageHandlerHolder.values().toArray(new MessageHandler[0]));
        return R.ok(requestNo, MessageErrorEnum.PUSH_SUCCESS.message());
    }

//    @Override
//    public SysFile upload(MultipartFile file) {
//        SysFile sysFile = null;
//        try {
//            sysFile = fileUploadClient.upload(file, "emailFile");
//        } catch (IOException e) {
//            log.info("上传文件失败", e);
//        }
//        return sysFile;
//    }
}
