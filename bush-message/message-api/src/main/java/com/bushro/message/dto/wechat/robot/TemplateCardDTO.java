package com.bushro.message.dto.wechat.robot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateCardDTO extends RobotCommonDTO {

    /**
     * 具体的模版卡片参数
     */
    private String templateCard;
}
