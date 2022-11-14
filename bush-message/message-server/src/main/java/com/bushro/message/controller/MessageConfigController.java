package com.bushro.message.controller;


import com.bushro.common.core.util.R;
import com.bushro.message.enums.MessagePlatformEnum;
import com.bushro.message.form.QueryConfigForm;
import com.bushro.message.form.UpdateConfigForm;
import com.bushro.message.service.IMessageConfigService;
import com.bushro.message.vo.ConfigFieldVO;
import com.bushro.message.vo.ConfigPageVo;
import com.bushro.message.vo.ConfigVo;
import com.bushro.message.vo.PlatformVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 消息配置 前端控制器
 * </p>
 *
 * @author bushro
 * @since 2022-10-09
 */
@Api(tags = "平台配置")
@RestController
@RequestMapping("/config")
public class MessageConfigController {

    @Resource
    private IMessageConfigService messageConfigService;


    @ApiOperation(value = "分页查询")
    @PostMapping("/page")
    public R<ConfigPageVo> page(@Valid @RequestBody QueryConfigForm queryConfigForm) {
        return R.ok(messageConfigService.page(queryConfigForm));
    }

    @ApiOperation(value = "查询平台配置")
    @GetMapping("/list")
    public R<List<ConfigVo>> list(@NotNull String platform) {
        return R.ok(messageConfigService.list(platform));
    }

    @ApiOperation(value = "添加/修改配置")
    @PostMapping("/addOrUpdated")
    public R addOrUpdated(@RequestBody UpdateConfigForm updateConfigForm) {
        messageConfigService.addOrUpdateConfig(updateConfigForm);
        return R.ok("保存成功");
    }

    @ApiOperation(value = "获取表头")
    @GetMapping("/{platform}/getFields")
    public R<List<ConfigFieldVO>> getFields(@PathVariable("platform") MessagePlatformEnum platform) {
        return R.ok(messageConfigService.getFields(platform));
    }

    /**
     * 后期可以配置到数据库种
     */
    @ApiOperation(value = "获取所有平台")
    @GetMapping("/platform")
    public R<List<PlatformVo>> platforms() {
        List<PlatformVo> platformList = new ArrayList<>();
        for (MessagePlatformEnum platformEnum : MessagePlatformEnum.values()) {
            platformList.add(PlatformVo.builder()
                    .id(platformEnum.name())
                    .name(platformEnum.getName())
                    .description("")
                    .validateReg("")
                    .enable(true)
                    .build());
        }
        return R.ok(platformList);
    }

}
