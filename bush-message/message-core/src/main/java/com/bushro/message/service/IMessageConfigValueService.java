package com.bushro.message.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bushro.message.entity.MessageConfigValue;
import com.bushro.message.form.QueryConfigForm;
import com.bushro.message.vo.ConfigVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 消息配置值 服务类
 * </p>
 *
 * @author bushro
 * @since 2022-10-09
 */
public interface IMessageConfigValueService extends IService<MessageConfigValue> {

    /**
     * 查询配置文件
     */
    List<MessageConfigValue> listByConfidIds(List<Long> configIds);



}
