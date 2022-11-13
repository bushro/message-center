package com.bushro.message.vo;

import com.bushro.message.base.IdStrAndName;
import com.bushro.message.enums.SchemeValueType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * 方案字段
 *
 * @author luo.qiang
 * @date 2022/11/11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchemeFieldVO implements Serializable {
    private static final long serialVersionUID = -4546007323930142286L;

    /**
     * 字段名称
     */
    private String name;
    /**
     * 字段key
     */
    private String key;

    /**
     * 字段描述
     */
    private String description;
    /**
     * 字段类型
     */
    private SchemeValueType type;
    /**
     * 顺序
     */
    private int order;
    /**
     * 选项（如果是选择型字段）
     */
    private List<IdStrAndName> options = new ArrayList<>();
    /**
     * 多对象字段
     */
    private List<MultiObjFieldVO> multiObjFields = new ArrayList<>();

}
