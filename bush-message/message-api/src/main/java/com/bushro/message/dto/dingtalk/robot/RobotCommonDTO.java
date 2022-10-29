package com.bushro.message.dto.dingtalk.robot;

import com.bushro.message.base.BaseMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
public class RobotCommonDTO extends BaseMessage {

    /**
     * 被@人的手机号。
     * <p>
     * 注意 在text内容里要有@人的手机号，只有在群内的成员才可被@，非群内成员手机号会被脱敏。
     */
    private List<String> atMobiles;
    /**
     * 被@人的用户userid。
     */
    private List<String> atUserIds;
    /**
     * 是否@所有人。
     */
    private Boolean isAtAll;

}
