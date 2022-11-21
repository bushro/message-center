package com.bushro.message.handle.email;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.mail.Mail;
import cn.hutool.extra.mail.MailAccount;
import com.amazonaws.services.s3.model.S3Object;
import com.bushro.common.core.enums.MessageEnum;
import com.bushro.common.core.exception.BusinessException;
import com.bushro.common.core.util.R;
import com.bushro.common.oss.service.OssTemplate;
import com.bushro.message.dto.email.EmailMessageDTO;
import com.bushro.message.entity.MessageRequestDetail;
import com.bushro.message.enums.MessageTypeEnum;
import com.bushro.message.enums.SendStatusEnum;
import com.bushro.message.properties.EmailConfig;
import com.bushro.message.service.IMessageConfigService;
import com.bushro.message.service.IMessageRequestDetailService;
import com.bushro.system.entity.SysFile;
import com.bushro.system.feign.ISysFileClientFeignApi;
import com.bushro.system.vo.RemoteFileVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 邮件消息处理器
 *
 * @author luo.qiang
 * @date 2022/11/21
 */
@Component
@Slf4j
public class EmailMessageHandler extends AbstractEmailHandler<EmailMessageDTO> implements Runnable {

    private final static Logger LOGGER = LoggerFactory.getLogger(EmailMessageHandler.class);

    private EmailMessageDTO param;

    @Resource
    private IMessageConfigService messageConfigService;

    @Resource
    private IMessageRequestDetailService messageRequestDetailService;

    @Resource
    private ISysFileClientFeignApi sysFileService;

    @Resource
    private OssTemplate ossTemplate;


    @Override
    public MessageTypeEnum messageType() {
        this.messageTypeEnum = MessageTypeEnum.EMAIL;
        return MessageTypeEnum.EMAIL;
    }


    @Override
    public void run() {
        List<EmailConfig> configs = messageConfigService.queryConfigOrDefault(param, EmailConfig.class);
        String content = param.getContent();
        String title = param.getTitle();
        for (EmailConfig config : configs) {
            this.config = config;
            this.setReceiverUsers(param);
            MailAccount account = this.getClient(config);
            MessageRequestDetail requestDetail = MessageRequestDetail.builder()
                    .platform(messageType().getPlatform().name())
                    .messageType(messageType().name())
                    .receiverId(param.getToUser())
                    .requestNo(param.getRequestNo())
                    .configId(config.getConfigId())
                    .build();
            try {
                Mail mail = Mail.create(account);
                mail.setTitle(title);
                mail.setContent(content);
                mail.setHtml(true);
                mail.setTos(param.getToUser().split(",|，"));
                if (!StrUtil.isEmpty(param.getCcs())) {
                    mail.setCcs(param.getCcs().split(",|，"));
                }
                List<File> files = new ArrayList<>();
                //文件处理
                if (!CollectionUtil.isEmpty(param.getFileNames())) {
                    for (String fileName : param.getFileNames()) {
                        R<SysFile> rpcFile = sysFileService.getOneByFileName(fileName);
                        if (MessageEnum.ERROR.code().equals(rpcFile.getCode())) {
                            log.error("找不到文件-{}", fileName);
                            throw new BusinessException("文件不存在！");
                        }
                        SysFile sysFile = rpcFile.getData();
                        RemoteFileVo fileVo = new RemoteFileVo();
                        fileVo.setFileName(sysFile.getFileName());
                        fileVo.setBucket(sysFile.getBucketName());
                        S3Object s3Object = ossTemplate.getObject(sysFile.getBucketName(), sysFile.getFileName());
                        File tempFile = null;
                        try {
                            tempFile = File.createTempFile(String.valueOf(sysFile.getId()), sysFile.getOriginal());
                            FileOutputStream stream = new FileOutputStream(tempFile);
                            IoUtil.copy(s3Object.getObjectContent(), stream);
                            files.add(tempFile);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            if (null != tempFile) {
                                tempFile.delete();
                            }
                        }
                    }
                }
                mail.setFiles(files.toArray(new File[0]));
                requestDetail.setSendStatus(SendStatusEnum.SEND_STATUS_SUCCESS.getCode());
                requestDetail.setMsgTest(SendStatusEnum.SEND_STATUS_SUCCESS.getDescription());
                String send = mail.send();
                LOGGER.info("发送邮件响应情况:{}", send);
            } catch (Exception e) {
                LOGGER.error("发送邮件失败", e);
                String eMessage = ExceptionUtil.getMessage(e);
                eMessage = StringUtils.isBlank(eMessage) ? "未知错误" : eMessage;
                requestDetail.setSendStatus(SendStatusEnum.SEND_STATUS_FAIL.getCode());
                requestDetail.setMsgTest(eMessage);
            }
            messageRequestDetailService.logDetail(requestDetail);
        }
    }


    @Override
    public void setBaseMessage(Object object) {
        this.param = (EmailMessageDTO) object;
    }

    @Override
    public Runnable getRunnable() {
        return this;
    }
}
