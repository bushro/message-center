package com.bushro.message.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bushro.message.base.BaseMessage;
import com.bushro.message.entity.MessageConfig;
import com.bushro.message.enums.MessagePlatformEnum;
import com.bushro.message.form.QueryConfigForm;
import com.bushro.message.form.UpdateConfigForm;
import com.bushro.message.vo.ConfigFieldVO;
import com.bushro.message.vo.ConfigPageVo;
import com.bushro.message.vo.ConfigVo;

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
     * 添加或更新配置
     *
     * @param updateConfigForm 更新配置形式
     * @return int
     */
    int addOrUpdateConfig(UpdateConfigForm updateConfigForm);


    /**
     * 删除配置通过id
     *
     * @param Id id
     */
    void deleteConfigById(Long configId);

    /**
     * 获取字段
     *
     * @param platform 平台
     * @return {@link List}<{@link ConfigFieldVO}>
     */
    List<ConfigFieldVO> getFields(MessagePlatformEnum platform);

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
     * @param message    具体的消息
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


    /**
     * 分页
     *
     * @param queryConfigForm 查询配置形式
     * @return {@link IPage}<>
     */
    ConfigPageVo page(QueryConfigForm queryConfigForm);


    /**
     * 列表
     *
     * @param platform 平台
     * @return {@link ConfigVo}
     */
    List<ConfigVo> list(String platform);

}
