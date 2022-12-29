package com.heaven.palace.purplecloudpalace.model.enmus;

/**
 * 数据库类型枚举 Created by ZhouShengEn on 2017/9/12.
 */
public enum DBType {
    MYSQL("mysql", "com.mysql.jdbc.Driver"),
    ORACLE("oracle", "oracle.jdbc.driver.OracleDriver"),
    SQLSERVER("sqlserver", "com.microsoft.sqlserver.jdbc.SQLServerDriver"),
    POSTGRESQL("postgresql", "org.postgresql.Driver");


    private String type;
    private String driver;

    private DBType(String type, String driver) {
        this.type = type;
        this.driver = driver;
    }

    public static DBType getDbTypeByType(String type) {
        DBType[] types = values();
        for (int i = 0; i < types.length; i++) {
            if (types[i].getType().equals(type)) {
                return types[i];
            }
        }
        return null;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }
}
