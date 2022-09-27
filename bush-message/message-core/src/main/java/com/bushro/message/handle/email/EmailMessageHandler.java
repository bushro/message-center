package com.bushro.message.handle.email;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.extra.mail.Mail;
import cn.hutool.extra.mail.MailAccount;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import com.bushro.message.dto.email.EmailMessageDTO;
import com.bushro.message.entity.MessageRequestDetail;
import com.bushro.message.enums.MessageTypeEnum;
import com.bushro.message.enums.SendStatusEnum;
import com.bushro.message.handle.AbstractMessageHandler;
import com.bushro.message.properties.EmailConfig;
import com.bushro.message.service.IMessageConfigService;
import com.bushro.message.service.IMessageRequestDetailService;

import java.util.*;

/**
 * 邮件消息处理器
 **/
@Component
public class EmailMessageHandler extends AbstractMessageHandler<EmailMessageDTO> {

    private final static Logger LOGGER = LoggerFactory.getLogger(EmailMessageHandler.class);

    @Autowired
    private IMessageConfigService messageConfigService;

    @Autowired
    private IMessageRequestDetailService messageRequestDetailService;



    @Override
    public MessageTypeEnum messageType() {
        return MessageTypeEnum.EMAIL;
    }

    @Override
    public void handle(EmailMessageDTO param) {
        List<EmailConfig> configs = messageConfigService.queryConfigOrDefault(param, EmailConfig.class);
        String content = param.getContent();
        String title = param.getTitle();
        for (EmailConfig config : configs) {
            Set<String> receiverUsers = new HashSet<>();
            if (CollUtil.isNotEmpty(param.getReceiverIds())) {
                receiverUsers.addAll(param.getReceiverIds());
            }
            if (receiverUsers.size() <= 0) {
                LOGGER.warn("请求号：{}，消息配置：{}。没有检测到接收邮箱", param.getRequestNo(), config.getConfigName());
                return;
            }
            MailAccount account = new MailAccount();
            account.setHost(config.getHost());
            account.setPort(config.getPort());
            account.setAuth(true);
            account.setFrom(config.getFrom());
            account.setUser(config.getUser());
            account.setPass(config.getPassword());
            account.setSslEnable(Optional.ofNullable(config.getSslEnable()).orElse(false));

            MessageRequestDetail requestDetail = MessageRequestDetail.builder()
                .platform(messageType().getPlatform().name())
                .messageType(messageType().name())
                .receiverId(String.join(",", receiverUsers))
                .requestNo(param.getRequestNo())
                .configId(config.getConfigId())
                .build();

            Mail mail = Mail.create(account);
            mail.setTitle(title);
            mail.setContent(content);
            mail.setHtml(true);
//            List<File> files = getFiles(param.getFileIds());
//            if (!CollectionUtils.isEmpty(files)) {
//                mail.setFiles(files.toArray(new File[0]));
//            }
            mail.setTos(param.getReceiverIds().toArray(new String[0]));
            if (!CollectionUtils.isEmpty(param.getCcs())) {
                mail.setCcs(param.getCcs().toArray(new String[0]));
            }
            try {
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

    /**
     * 获取文件列表
     */
//    public List<File> getFiles(List<String> fileIds) {
//        if (CollectionUtils.isEmpty(fileIds)) {
//            return null;
//        }
//        List<File> listFile = new ArrayList<>();
//        for (String fileId : fileIds) {
//            SysFile oneSysFile = sysFileRpcClient.getSysFileByFileId(Long.valueOf(fileId));
//            String rootPath = fileUploadClient.getFileRootPath(oneSysFile);
//            File file = new File(rootPath + oneSysFile.getFileName());
//            if (!file.exists()) {
//                LOGGER.error("文件{}不存在", rootPath + oneSysFile.getFileName());
//            }
//            listFile.add(file);
//        }
//        return listFile;
//    }
}
