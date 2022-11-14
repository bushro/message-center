package com.bushro.message.dto.email;

import com.bushro.message.annotation.SchemeValue;
import com.bushro.message.enums.SchemeValueType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

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
public class EmailMessageDTO extends EmailCommonDTO {

    private static final long serialVersionUID = 8283191910294934673L;
    /**
     * 文件列表
     */
    @SchemeValue(value = "文件列表", type = SchemeValueType.EMAIL_FILE,  order = 5)
    private List<String> fileNames;
    /**
     * 抄送人列表
     */
    @SchemeValue(value = "抄送人列表,多个人用,分隔", order = 2)
    private String ccs;
    /**
     * 请输入邮箱标题
     */
    @SchemeValue(value = "请输入邮箱标题", order = 3)
    private String title;
    /**
     * 请输入邮箱内容
     */
    @SchemeValue(value = "请输入邮箱内容", order = 4, type = SchemeValueType.TEXTAREA)
    private String content;

}
