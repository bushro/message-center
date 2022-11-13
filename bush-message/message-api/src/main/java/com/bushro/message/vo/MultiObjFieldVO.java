package com.bushro.message.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 多对象输入
 *
 * @author luo.qiang
 * @date 2022/11/11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MultiObjFieldVO {

    private String key;
    private String label;
    private String description;

}
