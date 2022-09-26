package com.bushro.message.dto.email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import com.bushro.message.base.BaseMessage;

import java.util.List;

/**
 * 邮件消息
 *
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class EmailMessageDTO extends BaseMessage {
    private static final long serialVersionUID = 2692273549631779696L;

    /**
     * 接收人分组列表
     */
    private List<Long> receiverGroupIds;
    /**
     * 接收人列表
     */
    private List<String> receiverIds;
    /**
     * 文件id列表
     */
    private List<String> fileIds;
    /**
     * 抄送人列表
     */
    private List<String> ccs;
    /**
     * 请输入邮箱标题
     */
    private String title;
    /**
     * 请输入邮箱内容
     */
    private String content;

}
