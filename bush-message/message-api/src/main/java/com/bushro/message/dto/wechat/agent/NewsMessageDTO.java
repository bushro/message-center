package com.bushro.message.dto.wechat.agent;
import com.bushro.message.annotation.SchemeValue;
import com.bushro.message.enums.SchemeValueType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;


/**
 * 企业微信图文消息发送DTO
 *
 * @author luo.qiang
 * @date 2022/11/13
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class NewsMessageDTO extends AgentCommonDTO {
    private static final long serialVersionUID = 7034106110120563906L;

    @SchemeValue(type = SchemeValueType.MULTI_OBJ_INPUT, value = "图文消息，一个图文消息支持1到8条图文")
    private List<ArticleDTO> articles;

}
