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
 * 消息配置值
 * </p>
 *
 * @author bushro
 * @since 2021-10-09
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@TableName("MESSAGE_CONFIG_VALUE")
@ApiModel(value="MessageConfigValue对象", description="消息配置值")
public class MessageConfigValue {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "属性")
    @TableField("KEY_NAME")
    private String keyName;

    @ApiModelProperty(value = "值")
    @TableField("VALUE")
    private String value;

    @ApiModelProperty(value = "配置id")
    @TableField("CONFIG_ID")
    private Long configId;

    @ApiModelProperty(value = "租户号")
    @TableField("TENANT_ID")
    private Long tenantId;

    @ApiModelProperty(value = "创建时间")
    @TableField("CREATE_TIME")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("UPDATE_TIME")
    private LocalDateTime updateTime;



    public static class Columns{
            public static final String ID = "ID";
            public static final String KEY_NAME = "KEY_NAME";
            public static final String VALUE = "VALUE";
            public static final String CONFIG_ID = "CONFIG_ID";
            public static final String TENANT_ID = "TENANT_ID";
            public static final String CREATE_TIME = "CREATE_TIME";
            public static final String UPDATE_TIME = "UPDATE_TIME";
    }
}
