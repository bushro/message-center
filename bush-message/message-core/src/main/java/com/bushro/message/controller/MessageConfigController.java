package com.bushro.message.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.bushro.common.core.util.R;
import com.bushro.message.form.UpdateConfigForm;
import com.bushro.message.service.IMessageConfigService;

import javax.annotation.Resource;

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

    @PostMapping("/addOrUpdated")
    public R addOrUpdated(@RequestBody UpdateConfigForm updateConfigForm) {
        messageConfigService.addOrUpdateConfig(updateConfigForm);
        return R.ok("保存成功");
    }

}
