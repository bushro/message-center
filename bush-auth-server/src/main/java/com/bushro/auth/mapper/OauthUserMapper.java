package com.bushro.auth.mapper;

import com.bushro.auth.core.userdetails.b.SysUserDetails;
import com.bushro.auth.model.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OauthUserMapper {
    /**
     * 获取用户通过名字
     *
     * @param username 用户名
     * @return {@link UserVo}
     */
    SysUserDetails getUserByName(@Param("username") String username);
}
