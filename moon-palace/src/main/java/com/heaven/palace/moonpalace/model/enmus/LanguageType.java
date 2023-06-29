package com.heaven.palace.moonpalace.model.enmus;

/**
 * 支持的语言类型
 * Created by ZhouShengEn on 2017/9/21.
 */
public enum LanguageType {
    JAVA("java");

    private String lan;

    LanguageType(String lan) {
        this.lan = lan;
    }

    public String getLan() {
        return lan;
    }

    public static LanguageType getLanguageType(String lan) {
        LanguageType[] types = values();
        for (int i = 0; i < types.length; i++) {
            if (types[i].getLan().equalsIgnoreCase(lan)) {
                return types[i];
            }
        }
        return null;
    }
}
