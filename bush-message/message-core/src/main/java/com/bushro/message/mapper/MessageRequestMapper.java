package com.bushro.message.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bushro.message.entity.MessageRequest;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 消息发送请求 Mapper 接口
 * </p>
 *
 * @author bushro
 * @since 2021-10-09
 */
@Mapper
public interface MessageRequestMapper extends BaseMapper<MessageRequest> {

}
