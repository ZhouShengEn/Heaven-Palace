package com.heaven.palace.moonpalace.mutidatasource;

/**
 * 复杂操作时连续指定db
 *
 * @author ZhouShengEn
 * @date 2022年8月25日
 */
public class DataSourceDetermineContextHolder {

    private static final ThreadLocal<Integer> contextHolder = new ThreadLocal<Integer>();

    /**
     * 设置数据源类型
     *
     * @param currentDataSource 数据库类型
     */
    public static void setCurrentDataSource(Integer currentDataSource) {
        contextHolder.set(currentDataSource);
    }

    /**
     * 获取数据源类型
     */
    public static Integer getCurrentDataSource() {
        return contextHolder.get();
    }

    /**
     * 清除数据源类型
     */
    public static void clearDataSource() {
        contextHolder.remove();
    }
}
