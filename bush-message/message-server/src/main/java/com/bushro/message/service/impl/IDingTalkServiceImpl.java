package com.bushro.message.service.impl;

import com.bushro.common.core.exception.BusinessException;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiMediaUploadRequest;
import com.dingtalk.api.response.OapiMediaUploadResponse;
import com.taobao.api.FileItem;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.bushro.common.core.util.R;
import com.bushro.message.enums.MessageErrorEnum;
import com.bushro.message.properties.DingTalkCorpConfig;
import com.bushro.message.service.IDingTalkService;
import com.bushro.message.service.IMessageConfigService;
import com.bushro.message.utils.AccessTokenUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
/**
 * 钉钉
 */
@Service
public class IDingTalkServiceImpl implements IDingTalkService {

    private final static Logger LOGGER = LoggerFactory.getLogger(IDingTalkServiceImpl.class);

    @Resource
    private IMessageConfigService messageConfigService;

    @Override
    public R upload(String configId, String fileType, MultipartFile media) {

        if (StringUtils.isEmpty(configId)) {
            throw new BusinessException(MessageErrorEnum.CONFIG_NOT_NULL);
        }
        List<DingTalkCorpConfig> talkCorpConfigs = messageConfigService.queryConfigOrDefault(
            Arrays.asList(Long.valueOf(configId)), DingTalkCorpConfig.class);
        DingTalkCorpConfig config = talkCorpConfigs.get(0);
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/media/upload");
        OapiMediaUploadRequest req = new OapiMediaUploadRequest();
        req.setType(fileType);
        // 要上传的媒体文件
        try {
            FileItem item = new FileItem(media.getOriginalFilename(), media.getInputStream());
            req.setMedia(item);
            OapiMediaUploadResponse rsp = client.execute(req, AccessTokenUtils.getAccessToken(config.getAppKey(), config.getAppSecret()));
            if (!rsp.isSuccess()) {
                LOGGER.error("上传钉钉文件失败:{}", rsp.getErrmsg());
            }
            return R.ok(rsp);
        } catch (Exception e) {
            LOGGER.error("上传钉钉文件失败", e);
        }
        return R.failed("上传钉钉文件失败");
    }
}
