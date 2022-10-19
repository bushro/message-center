package com.bushro.message.dto.email;

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

    /**
     * 接收人列表
     */
    private List<String> receiverIds;

}
