package com.heaven.palace.jasperpalace.base.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author :zhoushengen
 * @date : 2023/2/2
 */
@ApiModel(description = "泛型列表响应对象")
public class PageResultResponse<T> extends BaseResponse {
    @ApiModelProperty(notes = "列表响应数据，泛型")
    PageData<T> data;

    public PageResultResponse(long total, List<T> rows) {
        this.data = new PageData<T>(total, rows);
    }

    public PageResultResponse() {
        this.data = new PageData<T>();
    }

    PageResultResponse<T> total(int total) {
        this.data.setTotal(total);
        return this;
    }

    PageResultResponse<T> total(List<T> rows) {
        this.data.setRows(rows);
        return this;
    }

    public PageData<T> getData() {
        return data;
    }

    public void setData(PageData<T> data) {
        this.data = data;
    }

    @ApiModel(description =  "列表响应数据")
    public static class PageData<T> {
        @ApiModelProperty(notes = "条目总数")
        long total;
        @ApiModelProperty(notes = "当前页条目，泛型")
        List<T> rows;

        public PageData(long total, List<T> rows) {
            this.total = total;
            this.rows = rows;
        }

        public PageData() {
        }

        public long getTotal() {
            return total;
        }

        public void setTotal(long total) {
            this.total = total;
        }

        public List<T> getRows() {
            return rows;
        }

        public void setRows(List<T> rows) {
            this.rows = rows;
        }
    }
}
