package com.bushro.message.dto.wechat.robot;

import com.bushro.message.base.BaseMessage;
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
 * @date 2022/10/26
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class NewsMessageDTO extends RobotCommonDTO {

    /**
     * 图文消息，一个图文消息支持1到8条图文
     */
    private List<ArticleDTO> articles;
}
