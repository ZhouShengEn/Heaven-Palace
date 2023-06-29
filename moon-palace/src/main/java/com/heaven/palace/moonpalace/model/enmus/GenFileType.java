package com.heaven.palace.moonpalace.model.enmus;

/**
 * 不同语言,不同类型的字段
 * Created by ZhouShengEn on 2017/9/21.
 */
public enum GenFileType {

    STRING("String"),
    CHAR("char"),
    BOOLEAN("Boolean"),
    SHORT("Short"),
    LONG("Long"),
    BYTE_ARR("byteArr"),
    DATE("Date"),
    BIG_DECIMAL("BigDecimal"),
    DOUBLE("Double"),
    FLOAT("Float"),
    INTEGER("Integer");


    private String type;

    GenFileType(String type) {
        this.type = type;
    }

}
