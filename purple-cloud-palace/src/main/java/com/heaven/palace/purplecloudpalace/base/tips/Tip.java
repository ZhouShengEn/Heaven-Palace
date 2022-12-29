package com.heaven.palace.purplecloudpalace.base.tips;

/**
 * 返回给前台的提示（最终转化为json形式）
 *
 * @author ZhouShengEn
 * @Date 2022年8月25日
 */
public abstract class Tip {

    protected int code;
    protected String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
