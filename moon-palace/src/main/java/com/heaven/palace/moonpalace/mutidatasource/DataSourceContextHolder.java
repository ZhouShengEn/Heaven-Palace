package com.heaven.palace.moonpalace.mutidatasource;

/**
 * datasource的上下文
 *
 * @author ZhouShengEn
 * @date 2022年8月25日
 */
public class DataSourceContextHolder {

    private static final ThreadLocal<Integer> contextHolder = new ThreadLocal<Integer>();

    /**
     * 设置数据源类型
     *
     * @param dataSourceType 数据库类型
     */
    public static void setDataSourceType(Integer dataSourceType) {
        contextHolder.set(dataSourceType);
    }

    /**
     * 获取数据源类型
     */
    public static Integer getDataSourceType() {
        return contextHolder.get();
    }

    /**
     * 清除数据源类型
     */
    public static void clearDataSourceType() {
        contextHolder.remove();
    }
}
