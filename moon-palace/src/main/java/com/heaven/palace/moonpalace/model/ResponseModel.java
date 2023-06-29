package com.heaven.palace.moonpalace.model;

import java.io.Serializable;

/**
 * 基础返回类
 * 
 * @author ZhouShengEn
 * @date 2022年8月25日
 */
public class ResponseModel implements Serializable {

    private static final long  serialVersionUID = 5831231522873089422L;

    public static final String SUCCESS          = "操作成功";

    public static final String FAIL             = "操作失败";

    public static final String SYSTEM_ERROR     = "系统异常";

    public static ResponseModel ins() {
        return new ResponseModel();
    }

    public static ResponseModel ins(Object result) {
        return new ResponseModel("0000", null, result);
    }

    /**
     * 是否成功
     */
    private String key;
    /**
     * 消息
     */
    private String msg;
    /**
     * 返回对象
     */
    private Object result;

    public ResponseModel() {
        key = "0000";
    }

    public ResponseModel(String key) {
        this.key = key;
    }

    public ResponseModel(String key, String msg) {
        this.key = key;
        this.msg = msg;
    }

    public ResponseModel(String key, String msg, Object result) {
        this.key = key;
        this.msg = msg;
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public Object getResult() {
        return result;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
