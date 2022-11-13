package com.bushro.message.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bushro.common.core.exception.BusinessException;
import com.bushro.common.core.util.MessagePage;
import com.bushro.message.base.BaseMessage;
import com.bushro.message.base.Config;
import com.bushro.message.entity.MessageConfig;
import com.bushro.message.entity.MessageConfigValue;
import com.bushro.message.enums.MessageErrorEnum;
import com.bushro.message.enums.MessagePlatformEnum;
import com.bushro.message.form.QueryConfigForm;
import com.bushro.message.form.UpdateConfigForm;
import com.bushro.message.mapper.MessageConfigMapper;
import com.bushro.message.mapper.MessageConfigValueMapper;
import com.bushro.message.service.IMessageConfigService;
import com.bushro.message.service.IMessageConfigValueService;
import com.bushro.message.utils.MessageHandlerUtils;
import com.bushro.message.vo.ConfigFieldVO;
import com.bushro.message.vo.ConfigPageVo;
import com.bushro.message.vo.ConfigVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

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
 * @since 2022-10-09
 */
@Transactional(rollbackFor = Throwable.class)
@Service
public class MessageConfigServiceImpl extends ServiceImpl<MessageConfigMapper, MessageConfig> implements IMessageConfigService {

    @Resource
    private IMessageConfigValueService messageConfigValueService;

    @Resource
    private MessageConfigValueMapper configValueMapper;


    /**
     * 添加或者修改配置
     */
    @Override
    public int addOrUpdateConfig(UpdateConfigForm updateConfigForm) {

        Map<String, String> config = updateConfigForm.getConfig();
        if (config == null || config.size() <= 0) {
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
            if (config != null && config.size() == 0) {
                throw new BusinessException(MessageErrorEnum.CONFIG_PARAM_NOT_NULL);
            }
            if (StringUtils.isEmpty(configName)) {
                throw new BusinessException(MessageErrorEnum.CONFIG_NAME_NOT_NULL);
            }
        }

        if (StringUtils.isNotBlank(configName)) {
            // 名称判重
            LambdaQueryWrapper<MessageConfig> queryWrapper = Wrappers.<MessageConfig>lambdaQuery()
                    .eq(MessageConfig::getPlatform, platform)
                    .eq(MessageConfig::getConfigName, configName);
            if (configId != null) {
                queryWrapper = queryWrapper.ne(MessageConfig::getConfigId, configId);
            }
            MessageConfig existsConfig = getOne(queryWrapper);
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

    @Override
    public List<ConfigFieldVO> getFields(MessagePlatformEnum platform) {
        return MessageHandlerUtils.listConfigFieldName(platform);
    }

    /**
     * @param configIds : 配置id
     * @return : key:配置id Map<String, Object>：配置的所有属性值
     * @descript :
     * @date : 2022-10-11
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
        listMap.forEach((k, v) -> {
            Map<String, Object> valueMap = new HashMap<>();
            for (MessageConfigValue messageConfigValue : v) {
                valueMap.put(messageConfigValue.getKeyName(), messageConfigValue.getValue());
            }
            configMap.put(k, valueMap);
        });
        return configMap;
    }

    /**
     * @param message    : 要发送的信息
     * @param configType : 配置类
     * @return : java.util.List<T>
     * @descript : 查询配置
     * @date : 2022-10-09
     */

    @Override
    public <T> List<T> queryConfigOrDefault(BaseMessage message, Class<T> configType) {
        return queryConfigOrDefault(message.getConfigIds(), configType);
    }

    /**
     * @param configIds  : 配置id
     * @param configType : 配置类
     * @return : java.util.List<T>
     * @descript : 查询配置
     * @date : 2022-10-15
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


    @Override
    public ConfigPageVo page(QueryConfigForm queryConfigForm) {
        Page page = new Page(queryConfigForm.getPageIndex(), queryConfigForm.getPageSize());
        LambdaQueryWrapper<MessageConfig> queryWrapper = Wrappers.<MessageConfig>lambdaQuery()
                .eq(MessageConfig::getPlatform, queryConfigForm.getPlatform());
        if (!StrUtil.isEmpty(queryConfigForm.getConfigName())) {
            queryWrapper = queryWrapper.like(MessageConfig::getConfigName, queryConfigForm.getConfigName());
        }
        Page configPage = this.page(page, queryWrapper);
        List<ConfigVo> configList = configValueMapper.pageConfig(queryConfigForm);
        Map<Long, List<ConfigVo>> listMap = configList.stream().collect(Collectors.groupingBy(ConfigVo::getConfigId));
        List<Map> configListMap = new ArrayList<>();
        listMap.forEach((k, v) -> {
            Map map = new HashMap<>();
            map.put("configId", String.valueOf(k));
            for (ConfigVo configVo : v) {
                map.put(configVo.getKey(), configVo.getValue());
                map.put("configName", configVo.getConfigName());
            }
            configListMap.add(map);
        });
        ConfigPageVo pageVo = new ConfigPageVo();
        pageVo.setColumnList(getFields(queryConfigForm.getPlatform()));
        MessagePage<Map> messagePage = new MessagePage<>();
        messagePage.setDataList(configListMap);
        messagePage.setTotal(configPage.getRecords().size());
        messagePage.setPageSize(queryConfigForm.getPageSize());
        messagePage.setPageIndex(queryConfigForm.getPageIndex());
        pageVo.setPage(messagePage);
        return pageVo;
    }

    @Override
    public List<ConfigVo> list(String platform) {
        List<MessageConfig> configList = this.list(Wrappers.<MessageConfig>lambdaQuery()
                .select(MessageConfig::getConfigId, MessageConfig::getConfigName)
                .eq(MessageConfig::getPlatform, platform)
                .eq(MessageConfig::getUseFlag, 1));
        return configList.stream().map(e -> changeVo(e)).collect(Collectors.toList());
    }

    private ConfigVo changeVo(MessageConfig config) {
        ConfigVo configVo = new ConfigVo();
        configVo.setConfigId(config.getConfigId());
        configVo.setConfigName(config.getConfigName());
        return configVo;
    }
}
