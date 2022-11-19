package com.bushro.auth.core.userdetails.b;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * web端 认证对象
 */
@Getter
@Setter
public class SysUserDetails implements UserDetails {

    private static final long serialVersionUID = 1658435491496270439L;

    /**
     * 用户id
     */
    private Integer sysUserId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 认证身份标识
     * {@link com.bushro.common.security.enums.AuthGrantTypeEnum}
     */
    private String authenticationIdentity;
    /**
     * 头像
     */
    private String avatarUrl;
    /**
     * 角色
     */
    private String roles;
    /**
     * 是否有效 0=无效 1=有效
     */
    private int isValid;
    /**
     * 角色集合, 不能为空
     */
    private List<GrantedAuthority> authorities;

    /**
     * 获取角色信息
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (StrUtil.isNotBlank(this.roles)) {
            // 获取数据库中的角色信息
            Lists.newArrayList();
            this.authorities = Stream.of(this.roles.split(",")).map(role -> {
                return new SimpleGrantedAuthority(role);
            }).collect(Collectors.toList());
        } else {
            // 如果角色为空则设置为 ROLE_USER
            this.authorities = AuthorityUtils
                    .commaSeparatedStringToAuthorityList("ROLE_USER");
        }
        return this.authorities;
    }

    /**
     * 判断用户是否被禁用
     */
    @Override
    public boolean isEnabled() {
        return this.isValid == 0 ? false : true;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }



}
