package com.bushro.message.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.bushro.message.entity.MessageConfigValue;

import java.util.List;

/**
 * <p>
 * 消息配置值 Mapper 接口
 * </p>
 *
 * @author bushro
 * @since 2021-10-09
 */
public interface MessageConfigValueMapper extends BaseMapper<MessageConfigValue> {

    List<MessageConfigValue> listByConfidIds(@Param("configIds") List<Long> configIds);

}
