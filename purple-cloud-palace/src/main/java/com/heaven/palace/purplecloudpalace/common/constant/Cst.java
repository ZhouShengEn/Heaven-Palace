package com.heaven.palace.purplecloudpalace.common.constant;

/**
 * 一些服务的快捷获取
 *
 * @author ZhouShengEn
 * @date 2022-8-25
 */
public class Cst {

    private Cst() {
    }

    private static Cst cst = new Cst();

    public static Cst me() {
        return cst;
    }

}
