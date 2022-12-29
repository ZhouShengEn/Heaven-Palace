package com.heaven.palace.purplecloudpalace.page;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * 分页结果的封装(for Bootstrap Table)
 *
 * @author ZhouShengEn
 * @Date 2022年8月25日
 */
public class PageInfoBT<T> {

    // 结果集
    private List<T> rows;

    // 总数
    private long total;

    public PageInfoBT(Page<T> page) {
        this.rows = page.getRecords();
        this.total = page.getTotal();
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
