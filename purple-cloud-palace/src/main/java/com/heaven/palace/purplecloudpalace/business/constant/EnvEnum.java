package com.heaven.palace.purplecloudpalace.business.constant;


import com.heaven.palace.purplecloudpalace.common.constant.DSEnum;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author :zhoushengen
 * @date : 2022/9/8
 * 环境枚举
 */
public enum EnvEnum {
    DEV(1, "开发环境", DSEnum.DATA_SOURCE_PRODUCT_DEV),
    TEST(2, "测试环境", DSEnum.DATA_SOURCE_PRODUCT_TEST);
//    PRO(3, "生产环境", DSEnum.DATA_SOURCE_PRODUCT_PRO);
    
    private Integer id;
    private String name;
    private String DBSource;
    
    public static EnvEnum getEnvById(Integer id){
        if (null == id){
            return null;
        }
        return Objects.requireNonNull(Arrays.stream(EnvEnum.values()).filter(envEnum -> envEnum.id.equals(id)).findFirst()).get();
    }

    EnvEnum(Integer id, String name, String DBSource) {
        this.id = id;
        this.name = name;
        this.DBSource = DBSource;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDBSource() {
        return DBSource;
    }
}
