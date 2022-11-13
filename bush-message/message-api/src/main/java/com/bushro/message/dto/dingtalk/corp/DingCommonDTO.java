package com.bushro.message.dto.dingtalk.corp;

import com.bushro.message.annotation.SchemeValue;
import com.bushro.message.annotation.SchemeValueOption;
import com.bushro.message.base.BaseMessage;
import com.bushro.message.base.BaseParam;
import com.bushro.message.enums.SchemeValueType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class DingCommonDTO extends BaseMessage {
    private static final long serialVersionUID = -8709896456192017842L;

    /**
     * 接收人分组列表
     */
    private List<Long> receiverGroupIds;

    /**
     * 是否发送给企业全部用户，注意钉钉限制只能发3次全员消息
     * 当设置为false时必须指定userid_list或dept_id_list其中一个参数的值。
     *
     */
    @SchemeValue(type = SchemeValueType.SELECT, value = "是否发送给企业全部用户，注意钉钉限制只能发3次全员消息", description = "是否发送给企业全部用户，注意钉钉限制只能发3次全员消息", options = {
            @SchemeValueOption(key = "true", label = "true"),
            @SchemeValueOption(key = "false", label = "false")
    }, order = 1)
    private boolean toAllUser;

    /**
     * 接收者的用户userid列表, user123,user456 接收者的userid列表，最大用户列表长度100。
     */
    @SchemeValue(value = "接收者的用户userid列表, user123,user456 接收者的userid列表，最大用户列表长度100。", order = 2)
    private String useridList;

    /**
     * 接收者的部门id列表，最大列表长度20。多个用,隔开
     * 接收者是部门ID时，包括子部门下的所有用户。
     *
     */
    @SchemeValue(value = "接收者的部门id列表，最大列表长度20。多个用,隔开 接收者是部门ID时，包括子部门下的所有用户。", order = 3)
    private String deptIdList;
}
