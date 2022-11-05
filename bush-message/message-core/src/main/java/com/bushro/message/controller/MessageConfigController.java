package com.bushro.message.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bushro.message.enums.MessagePlatformEnum;
import com.bushro.message.form.QueryConfigForm;
import com.bushro.message.service.IMessageConfigValueService;
import com.bushro.message.vo.ConfigFieldVO;
import com.bushro.message.vo.ConfigPageVo;
import org.springframework.web.bind.annotation.*;

import com.bushro.common.core.util.R;
import com.bushro.message.form.UpdateConfigForm;
import com.bushro.message.service.IMessageConfigService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 消息配置 前端控制器
 * </p>
 *
 * @author bushro
 * @since 2022-10-09
 */
@RestController
@RequestMapping("/config")
public class MessageConfigController {

    @Resource
    private IMessageConfigService messageConfigService;



    @PostMapping("/page")
    public R<ConfigPageVo> page(@Valid @RequestBody QueryConfigForm queryConfigForm) {
        return R.ok(messageConfigService.page(queryConfigForm));
    }

    @PostMapping("/addOrUpdated")
    public R addOrUpdated(@RequestBody UpdateConfigForm updateConfigForm) {
        messageConfigService.addOrUpdateConfig(updateConfigForm);
        return R.ok("保存成功");
    }

    @GetMapping("/{platform}/getFields")
    public R<List<ConfigFieldVO>> getFields(@PathVariable("platform") MessagePlatformEnum platform) {
        return R.ok(messageConfigService.getFields(platform));
    }

}
