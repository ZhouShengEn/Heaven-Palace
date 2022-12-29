package com.heaven.palace.purplecloudpalace.base.tips;

/**
 * 返回给前台的错误提示
 *
 * @author ZhouShengEn
 * @date 2022年8月25日
 */
public class ErrorTip extends Tip {

    public ErrorTip(int code, String message) {
        super();
        this.code = code;
        this.message = message;
    }
}
