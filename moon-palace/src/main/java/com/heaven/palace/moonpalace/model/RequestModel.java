package com.heaven.palace.moonpalace.model;

import com.heaven.palace.moonpalace.page.Pager;

/**
 * 基础请求类
 * @author ZhouShengEn
 * @date 2022年8月25日
 */
public class RequestModel extends Pager {

    private static final long serialVersionUID = -6618310402644509781L;

    public static final String SUCCESS = "success";

    public static final String MESSAGE = "msg";

    public static final String LIST = "list";

    public static final String MODEL = "model";

    private String id;

    private String name;

    private String startTime;

    private String endTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

}
