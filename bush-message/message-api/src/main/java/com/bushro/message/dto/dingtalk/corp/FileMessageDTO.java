package com.bushro.message.dto.dingtalk.corp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import com.bushro.message.base.BaseMessage;

import java.util.List;

/**
 * 钉钉文件消息
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FileMessageDTO extends BaseMessage {


    private static final long serialVersionUID = -8709896456192012662L;
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
     * 接收者的部门id列表，最大列表长度20。接收者是部门ID时，包括子部门下的所有用户。多个用,隔开
     *
     */
    private String deptIdList;

    /**
     * 媒体文件mediaid。 可以通过上传媒体文件接口获取。建议宽600像素 x 400像素，宽高比3 : 2。
     */
    private String mediaId;
}
