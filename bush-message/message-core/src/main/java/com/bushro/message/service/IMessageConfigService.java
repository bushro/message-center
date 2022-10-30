package com.bushro.message.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bushro.message.base.BaseMessage;
import com.bushro.message.entity.MessageConfig;
import com.bushro.message.form.UpdateConfigForm;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 消息配置 服务类
 * </p>
 *
 * @author bushro
 * @since 2022-10-09
 */
public interface IMessageConfigService extends IService<MessageConfig> {

    /**
     * 添加或者修改配置
     */
    int addOrUpdateConfig(UpdateConfigForm updateConfigForm);

    /**
     * 批量查询配置
     *
     * @param configIds 配置id列表，传空会返回空map
     * @return 键为配置id，值为：具体的配置键值
     */
    Map<Long, Map<String, Object>> queryConfig(List<Long> configIds);

    /**
     * 批量查询配置
     *
     * @param message  具体的消息
     * @param configType 配置DTO类型
     */
    <T> List<T> queryConfigOrDefault(BaseMessage message, Class<T> configType);

    /**
     * 批量查询配置
     *
     * @param configIds  配置id列表
     * @param configType 配置DTO类型
     */
    <T> List<T> queryConfigOrDefault(List<Long> configIds, Class<T> configType);

}
