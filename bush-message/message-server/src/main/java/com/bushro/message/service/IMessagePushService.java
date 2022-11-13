package com.bushro.message.service;


import org.springframework.web.multipart.MultipartFile;
import com.bushro.common.core.util.R;

public interface IMessagePushService {

    /**
     * 消息推送
     */
    R<String> push (String param);

}
