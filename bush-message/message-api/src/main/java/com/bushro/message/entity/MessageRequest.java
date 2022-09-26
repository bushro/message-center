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
 * 消息发送请求
 * </p>
 *
 * @author bushro
 * @since 2021-10-09
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@TableName("MESSAGE_REQUEST")
@ApiModel(value="MessageRequest对象", description="消息发送请求")
public class MessageRequest {

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

    @ApiModelProperty(value = "json参数")
    @TableField("PARAM")
    private String param;

    @ApiModelProperty(value = "创建时间")
    @TableField("CREATE_TIME")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("UPDATE_TIME")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "请求号，唯一")
    @TableField("REQUEST_NO")
    private String requestNo;



    public static class Columns{
            public static final String ID = "ID";
            public static final String PARAM = "PARAM";
            public static final String CREATE_TIME = "CREATE_TIME";
            public static final String UPDATE_TIME = "UPDATE_TIME";
            public static final String REQUEST_NO = "REQUEST_NO";
    }
}
