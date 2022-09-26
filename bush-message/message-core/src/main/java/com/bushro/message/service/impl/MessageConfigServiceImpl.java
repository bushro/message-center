package com.bushro.message.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bushro.common.core.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import com.bushro.message.base.BaseMessage;
import com.bushro.message.base.Config;
import com.bushro.message.entity.MessageConfig;
import com.bushro.message.entity.MessageConfigValue;
import com.bushro.message.enums.MessageErrorEnum;
import com.bushro.message.enums.MessagePlatformEnum;
import com.bushro.message.form.UpdateConfigForm;
import com.bushro.message.mapper.MessageConfigMapper;
import com.bushro.message.service.IMessageConfigService;
import com.bushro.message.service.IMessageConfigValueService;
import com.bushro.message.utils.MessageHandlerUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 消息配置 服务实现类
 * </p>
 *
 * @author bushro
 * @since 2021-10-09
 */
@Service
public class MessageConfigServiceImpl extends ServiceImpl<MessageConfigMapper, MessageConfig> implements IMessageConfigService {

    @Resource
    private IMessageConfigValueService messageConfigValueService;


    /**
     * 添加或者修改配置
     */
    @Override
    public int addOrUpdateConfig(UpdateConfigForm updateConfigForm) {

        Map<String, String> config = updateConfigForm.getConfig();
        if (config == null || config.size() <= 0 ) {
            throw new BusinessException(MessageErrorEnum.CONFIG_NOT_NULL);
        }
        if (config != null) {
            config.remove("configId");
            config.remove("configName");
            config.remove("useFlag");
        }
        Long configId = updateConfigForm.getConfigId();
        configId = configId != null && configId <= 0L ? null : configId;
        MessagePlatformEnum platform = updateConfigForm.getPlatform();
        String configName = updateConfigForm.getConfigName();

        // 有传配置id认为是更新，没有传id认为是新增
        boolean isUpdate = configId != null;
        boolean isAdd = configId == null;

        if (isAdd) {
            if(config != null && config.size() == 0){
                throw new BusinessException(MessageErrorEnum.CONFIG_PARAM_NOT_NULL);
            }
            if (StringUtils.isEmpty(configName)) {
                throw new BusinessException(MessageErrorEnum.CONFIG_NAME_NOT_NULL);
            }
        }

        if (StringUtils.isNotBlank(configName)) {
            // 名称判重
            QueryWrapper<MessageConfig> configNameQw = new QueryWrapper<>();
            configNameQw.eq("platform", platform);
            configNameQw.eq("config_name", configName);
            if (configId != null) {
                configNameQw.ne("id", configId);
            }
            MessageConfig existsConfig = getOne(configNameQw);
            if (!ObjectUtils.isEmpty(existsConfig)) {
                throw new BusinessException(MessageErrorEnum.CONFIG_NAME_REPEAT);
            }
        }
        MessageConfig messageConfig;
        // 主表入库
        if (isUpdate) {
            messageConfig = getById(configId);
            messageConfig.setConfigName(configName);
            messageConfig.setUpdateTime(LocalDateTime.now());
            this.updateById(messageConfig);
        } else {
            messageConfig = new MessageConfig();
            messageConfig.setConfigId(IdWorker.getId());
            messageConfig.setConfigName(configName);
            messageConfig.setPlatform(platform.name());
            messageConfig.setCreateTime(LocalDateTime.now());
            this.save(messageConfig);
        }
        // 重新拿id
        configId = messageConfig.getConfigId();
        QueryWrapper<MessageConfigValue> wrapper = new QueryWrapper<>();
        wrapper.eq(MessageConfigValue.Columns.CONFIG_ID, configId);
        // 先把子表所有值删掉，然后重建
        messageConfigValueService.remove(wrapper);
        List<MessageConfigValue> configValues = new ArrayList<>();
        for (Map.Entry<String, String> entry : config.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            MessageConfigValue configValue = new MessageConfigValue();
            configValue.setConfigId(configId);
            configValue.setCreateTime(LocalDateTime.now());
            configValue.setValue(value);
            configValue.setKeyName(key);
            configValues.add(configValue);
        }
        messageConfigValueService.saveBatch(configValues);
        return 1;
    }

    /**
     * @descript :
     * @date : 2021-10-11
     * @param configIds : 配置id
     * @return : key:配置id Map<String, Object>：配置的所有属性值
     */
    @Override
    public Map<Long, Map<String, Object>> queryConfig(List<Long> configIds) {
        if (configIds == null || configIds.size() <= 0) {
            return new HashMap<>();
        }
        //查找所有配置
        List<MessageConfigValue> valueList = messageConfigValueService.listByConfidIds(configIds);
        Map<Long, List<MessageConfigValue>> listMap = valueList.stream()
            .collect(Collectors.groupingBy(MessageConfigValue::getConfigId));
        Map<Long, Map<String, Object>> configMap = new HashMap<>();
        listMap.forEach((k,v) -> {
            Map<String, Object> valueMap = new HashMap<>();
            for (MessageConfigValue messageConfigValue : v) {
                valueMap.put(messageConfigValue.getKeyName(),messageConfigValue.getValue());
            }
            configMap.put(k,valueMap);
        });
        return configMap;
    }

    /**
     * @descript : 查询配置
     * @date : 2021-10-09
     * @param message : 要发送的信息
     * @param configType : 配置类
     * @return : java.util.List<T>
     */

    @Override
    public <T> List<T> queryConfigOrDefault(BaseMessage message, Class<T> configType) {
        return queryConfigOrDefault(message.getConfigIds(), configType);
    }
    /**
     * @descript : 查询配置
     * @date : 2021-10-15
     * @param configIds : 配置id
     * @param configType : 配置类
     * @return : java.util.List<T>
     */
    @Override
    public <T> List<T> queryConfigOrDefault(List<Long> configIds, Class<T> configType) {
        if (CollectionUtil.isEmpty(configIds)) {
            return new ArrayList<>();
        }
        // 键为配置id，值为：具体的配置键值
        Map<Long, Map<String, Object>> configMap = queryConfig(configIds);
        // 转成具体的配置实体类
        List<Config> configs = MessageHandlerUtils.convertConfig(configType, configMap);
        return (List<T>) configs;
    }
}
