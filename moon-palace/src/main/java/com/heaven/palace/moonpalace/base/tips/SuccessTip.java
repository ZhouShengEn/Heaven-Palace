package com.heaven.palace.moonpalace.base.tips;

/**
 * 返回给前台的成功提示
 *
 * @author ZhouShengEn
 * @date 2022年8月25日
 */
public class SuccessTip extends Tip {

    public SuccessTip() {
        super.code = 200;
        super.message = "操作成功";
    }
    public SuccessTip(String message) {
        super.code = 200;
        super.message = message;
    }
}
