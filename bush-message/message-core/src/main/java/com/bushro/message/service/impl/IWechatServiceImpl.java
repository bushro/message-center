package com.bushro.message.service.impl;

import com.bushro.common.core.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.cp.api.impl.WxCpServiceImpl;
import me.chanjar.weixin.cp.config.impl.WxCpDefaultConfigImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.bushro.common.core.util.R;
import com.bushro.message.enums.MessageErrorEnum;
import com.bushro.message.properties.WechatWorkAgentConfig;
import com.bushro.message.service.IMessageConfigService;
import com.bushro.message.service.IWechatService;
import com.bushro.message.utils.SingletonUtil;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * 微信
 */
@Service
@Slf4j
public class IWechatServiceImpl implements IWechatService {

    @Resource
    private IMessageConfigService messageConfigService;

    @Override
    public R upload(String configId, String fileType, MultipartFile media) {
        if (StringUtils.isEmpty(configId)) {
            throw new BusinessException(MessageErrorEnum.CONFIG_NOT_NULL);
        }
        List<WechatWorkAgentConfig> workAgentConfigList = messageConfigService.queryConfigOrDefault(
            Arrays.asList(Long.valueOf(configId)), WechatWorkAgentConfig.class);
        WechatWorkAgentConfig config = workAgentConfigList.get(0);
        WxCpServiceImpl wxCpService = SingletonUtil.get(config.getCorpId() + config.getSecret() + config.getAgentId(), () -> {
            WxCpDefaultConfigImpl cpConfig = new WxCpDefaultConfigImpl();
            cpConfig.setCorpId(config.getCorpId());
            cpConfig.setCorpSecret(config.getSecret());
            cpConfig.setAgentId(config.getAgentId());
            WxCpServiceImpl wxCpService1 = new WxCpServiceImpl();
            wxCpService1.setWxCpConfigStorage(cpConfig);
            return wxCpService1;
        });

        try {
            /**
             * 这个接口的mediaType和fileType一致
             */
            WxMediaUploadResult result = wxCpService.getMediaService().upload(fileType, fileType, media.getInputStream());
            return R.ok(result);
        } catch (Exception e) {
            log.error("上传企业微信文件失败", e);
        }

        return R.failed("上传企业微信文件失败");
    }
}
