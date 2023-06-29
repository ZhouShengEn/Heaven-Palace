package com.heaven.palace.moonpalace.model.enmus;

/**
 * 主键类型
 *
 * @author ZhouShengEn
 * @date 2022年8月25日
 */
public enum GenerationType {

    TABLE("table"), SEQUENCE("sequence"), IDENTITY("identity"), AUTO("auto");

    private String value;

    private GenerationType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
