package com.bushro.message.dto.wechat.robot;

import com.bushro.message.annotation.SchemeValue;
import com.bushro.message.enums.SchemeValueType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * 企业微信文本消息发送DTO
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TextMessageDTO extends RobotCommonDTO {
    private static final long serialVersionUID = -3289428483627765265L;

    /**
     * userid的列表，提醒群中的指定成员(@某个成员)，@all表示提醒所有人，
     * 如果开发者获取不到userid，可以使用mentioned_mobile_list
     */
    @SchemeValue(value = "userid的列表，提醒群中的指定成员(@某个成员)，@all表示提醒所有人", type = SchemeValueType.RECEIVER, order = 1)
    private List<String> mentionedList;

    /**
     * 手机号列表，提醒手机号对应的群成员(@某个成员)，@all表示提醒所有人
     */
    @SchemeValue(value = "手机号列表，提醒手机号对应的群成员(@某个成员)，@all表示提醒所有人", type = SchemeValueType.RECEIVER, order = 2)
    private List<String> mentionedMobileList;

    /**
     * 请输入内容...
     */
    @SchemeValue(type = SchemeValueType.TEXTAREA, description = "请输入内容...", order = 3)
    private String content;


}
