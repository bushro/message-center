package com.bushro.message.dto.email;

import com.bushro.message.annotation.SchemeValue;
import com.bushro.message.base.BaseMessage;
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
public class EmailCommonDTO extends BaseMessage {
    private static final long serialVersionUID = -8709896456192017842L;

    /**
     * 接收人分组列表
     */
    private List<Long> receiverGroupIds;


    @SchemeValue(value = "接收人邮箱，多个接收人用,分割", order = 1)
    private String toUser;

}
