package com.bushro.message.dto.wechat.robot;

import com.bushro.message.base.BaseMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
public class RobotCommonDTO extends BaseMessage {

}
