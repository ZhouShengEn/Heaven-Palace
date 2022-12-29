package com.heaven.palace.purplecloudpalace.mutidatasource;

/**
 * datasource的上下文
 *
 * @author ZhouShengEn
 * @date 2022年8月25日
 */
public class DataSourceContextHolder {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    /**
     * 设置数据源类型
     *
     * @param dataSourceType 数据库类型
     */
    public static void setDataSourceType(String dataSourceType) {
        contextHolder.set(dataSourceType);
    }

    /**
     * 获取数据源类型
     */
    public static String getDataSourceType() {
        return contextHolder.get();
    }

    /**
     * 清除数据源类型
     */
    public static void clearDataSourceType() {
        contextHolder.remove();
    }
}
