package com.bushro.common.core.util;

import lombok.Data;

import java.io.Serializable;

@Data
public class CommonPage implements Serializable {

    private static final long serialVersionUID = -2987707291928050280L;

    private Integer pageIndex;

    private Integer pageSize;

}
