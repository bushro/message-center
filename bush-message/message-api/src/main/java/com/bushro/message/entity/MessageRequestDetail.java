package com.bushro.message.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * <p>
 * 消息发送结果详细
 * </p>
 *
 * @author bushro
 * @since 2022-10-09
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@TableName("MESSAGE_REQUEST_DETAIL")
@ApiModel(value="MessageRequestDetail对象", description="消息发送结果详细")
public class MessageRequestDetail {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "消息类型，如企业微信文本、图片等")
    @TableField("MESSAGE_TYPE")
    private String messageType;

    @ApiModelProperty(value = "平台类型，如邮件，企业微信等")
    @TableField("PLATFORM")
    private String platform;

    @ApiModelProperty(value = "创建时间")
    @TableField("CREATE_TIME")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("UPDATE_TIME")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "接收人")
    @TableField("RECEIVER_ID")
    private String receiverId;

    @ApiModelProperty(value = "配置id")
    @TableField("CONFIG_ID")
    private Long configId;

    @ApiModelProperty(value = "发送状态，0 未开始；1 发送成功；2 发送失败")
    @TableField("SEND_STATUS")
    private Integer sendStatus;

    @ApiModelProperty(value = "反馈信息")
    @TableField("MSG_TEST")
    private String msgTest;

    @ApiModelProperty(value = "所属请求号，对应表MESSAGE_REQUEST的字段request_no")
    @TableField("REQUEST_NO")
    private String requestNo;



    public static class Columns{
            public static final String ID = "ID";
            public static final String MESSAGE_TYPE = "MESSAGE_TYPE";
            public static final String PLATFORM = "PLATFORM";
            public static final String CREATE_TIME = "CREATE_TIME";
            public static final String UPDATE_TIME = "UPDATE_TIME";
            public static final String RECEIVER_ID = "RECEIVER_ID";
            public static final String CONFIG_ID = "CONFIG_ID";
            public static final String SEND_STATUS = "SEND_STATUS";
            public static final String MSG_TEST = "MSG_TEST";
            public static final String REQUEST_NO = "REQUEST_NO";
    }
}
