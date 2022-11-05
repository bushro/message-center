package com.bushro.common.core.util;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 自定义分页
 *
 * @author luo.qiang
 * @date 2022/11/05
 */
@Data
public class MessagePage<E> implements Serializable {
    private static final long serialVersionUID = -3141529084123771881L;

    private static final int PAGE_ITEM_COUNT = 10; // 显示几个页码

    private List<E> dataList;
    private int total;
    private int pageSize = 20;
    private int pageIndex = 1; // 当前页码
    private Integer totalPage;

    public MessagePage() {
    }

    public MessagePage(int pageNum, int pageSize) {
        this.pageIndex = pageNum;
        this.pageSize = pageSize;
    }

    public MessagePage(int pageNum, int pageSize, int total, List<E> dataList) {
        this.pageIndex = pageNum;
        this.pageSize = pageSize;
        this.total = total;
        this.dataList = dataList;
    }

    public List<E> getDataList() {
        return dataList;
    }

    public void setDataList(List<E> dataList) {
        this.dataList = dataList;
    }
}
