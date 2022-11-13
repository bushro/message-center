package com.bushro.message.dto.dingtalk.robot;

import com.bushro.message.annotation.SchemeValue;
import com.bushro.message.annotation.SchemeValueOption;
import com.bushro.message.base.BaseMessage;
import com.bushro.message.enums.SchemeValueType;
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
    @SchemeValue(value = "被@人的手机号", order = 1)
    private List<String> atMobiles;
    /**
     * 被@人的用户userid。
     */
    @SchemeValue(value = "被@人的用户userid", order = 2)
    private List<String> atUserIds;
    /**
     * 是否@所有人。
     */
    @SchemeValue(type = SchemeValueType.SELECT, value = "是否@所有人", description = "是否@所有人", options = {
            @SchemeValueOption(key = "true", label = "true"),
            @SchemeValueOption(key = "false", label = "false")
    }, order = 3)
    private Boolean isAtAll;

}
