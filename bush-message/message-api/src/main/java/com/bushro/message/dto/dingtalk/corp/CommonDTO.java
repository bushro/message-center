package com.bushro.message.dto.dingtalk.corp;

import com.bushro.message.base.BaseMessage;
import com.bushro.message.base.BaseParam;
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
public class CommonDTO extends BaseMessage {
    private static final long serialVersionUID = -8709896456192017842L;

    /**
     * 接收人分组列表
     */
    private List<Long> receiverGroupIds;

    /**
     * 是否发送给企业全部用户，注意钉钉限制只能发3次全员消息
     */
    private boolean toAllUser;

    /**
     * 接收人列表
     */
    private List<String> receiverIds;

    /**
     * 接收人的部门id列表，接收者的部门id列表，多个用,隔开
     */
    private String deptIdList;
}
