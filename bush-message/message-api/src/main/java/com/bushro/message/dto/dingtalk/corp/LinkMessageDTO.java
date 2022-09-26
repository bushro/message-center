package com.bushro.message.dto.dingtalk.corp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import com.bushro.message.base.BaseMessage;

import java.util.List;

/**
 * 钉钉链接消息
 *
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class LinkMessageDTO extends BaseMessage {
    private static final long serialVersionUID = 6529460286674167742L;

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

    /**
     * 消息点击链接地址，当发送消息为小程序时支持小程序跳转链接。
     */
    private String messageUrl;

    /**
     * 图片地址，可以通过上传媒体文件接口获取。
     */
    private String picUrl;

    /**
     * 消息标题，建议100字符以内。
     */
    private String title;

    /**
     * 消息描述，建议500字符以内。
     */
    private String text;

}
