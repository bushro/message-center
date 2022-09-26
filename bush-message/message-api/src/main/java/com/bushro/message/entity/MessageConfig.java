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
 * 消息配置
 * </p>
 *
 * @author bushro
 * @since 2021-10-09
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@TableName("MESSAGE_CONFIG")
@ApiModel(value="MessageConfig对象", description="消息配置")
public class MessageConfig {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "CONFIG_ID", type = IdType.ASSIGN_ID)
    private Long configId;

    @ApiModelProperty(value = "配置名称")
    @TableField("CONFIG_NAME")
    private String configName;

    @ApiModelProperty(value = "平台类型")
    @TableField("PLATFORM")
    private String platform;

    @ApiModelProperty(value = "租户号")
    @TableField("TENANT_ID")
    private Long tenantId;

    @ApiModelProperty(value = "创建时间")
    @TableField("CREATE_TIME")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("UPDATE_TIME")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "是否有效 1有效，0无效")
    @TableField("USE_FLAG")
    private Integer useFlag;



    public static class Columns{
            public static final String CONFIG_ID = "CONFIG_ID";
            public static final String CONFIG_NAME = "CONFIG_NAME";
            public static final String PLATFORM = "PLATFORM";
            public static final String TENANT_ID = "TENANT_ID";
            public static final String CREATE_TIME = "CREATE_TIME";
            public static final String UPDATE_TIME = "UPDATE_TIME";
            public static final String USE_FLAG = "USE_FLAG";
    }
}
