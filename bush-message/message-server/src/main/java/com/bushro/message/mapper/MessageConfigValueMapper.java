package com.bushro.message.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bushro.message.form.QueryConfigForm;
import com.bushro.message.vo.ConfigVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.bushro.message.entity.MessageConfigValue;

import java.util.List;

/**
 * <p>
 * 消息配置值 Mapper 接口
 * </p>
 *
 * @author bushro
 * @since 2022-10-09
 */
@Mapper
public interface MessageConfigValueMapper extends BaseMapper<MessageConfigValue> {

    /**
     * 通过ids查询
     *
     * @param configIds 配置ids
     * @return {@link List}<{@link MessageConfigValue}>
     */
    List<MessageConfigValue> listByConfidIds(@Param("configIds") List<Long> configIds);

    /**
     * 查询配置
     *
     * @param query 查询
     * @return {@link IPage}<{@link ConfigVo}>
     */
    List<ConfigVo> pageConfig(@Param("query") QueryConfigForm query);

}
