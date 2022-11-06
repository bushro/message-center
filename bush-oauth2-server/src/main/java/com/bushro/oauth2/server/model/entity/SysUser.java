package com.bushro.oauth2.server.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统用户
 *
 * @author luo.qiang
 * @date 2022/11/06
 */
@Data
public class SysUser implements Serializable {

    private static final long serialVersionUID = -5584424620273067069L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    // 用户名

    @TableField("USERNAME")
    private String username;
    // 昵称
    @TableField("NICKNAME")
    private String nickname;
    // 密码
    @TableField("PASSWORD")
    private String password;
    // 手机号
    @TableField("PHONE")
    private String phone;
    // 邮箱
    @TableField("EMAIL")
    private String email;
    // 头像
    @TableField("AVATAR_URL")
    private String avatarUrl;
    // 角色
    @TableField("ROLES")
    private String roles;
    // 是否有效 0=无效 1=有效
    @TableField("IS_VALID")
    private int isValid;

    @TableField("CREATE_DATE")
    private LocalDateTime createDate;

    @TableField("UPDATE_DATE")
    private LocalDateTime updateDate;
}
