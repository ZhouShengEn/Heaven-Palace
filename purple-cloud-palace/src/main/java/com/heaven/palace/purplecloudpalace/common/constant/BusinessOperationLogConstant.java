package com.heaven.palace.purplecloudpalace.common.constant;

/**
 * @author :zhoushengen
 * @date : 2022/9/6
 */
public interface BusinessOperationLogConstant {
    enum BUSINESS_TYPE{
        // 字典业务
        BUSINESS_DICT_ITEM,
        BUSINESS_DICT_TYPE
    }
    
    enum TITLE{
        INSERT("新增"),
        DELETE("删除"),
        UPDATE("修改");
        
        private String name;

        TITLE(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
