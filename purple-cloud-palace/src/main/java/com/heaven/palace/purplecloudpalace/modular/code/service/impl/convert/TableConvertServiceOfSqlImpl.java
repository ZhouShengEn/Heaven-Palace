package com.heaven.palace.purplecloudpalace.modular.code.service.impl.convert;

import com.heaven.palace.purplecloudpalace.db.convert.sql.SqlConvertFactory;
import com.heaven.palace.purplecloudpalace.model.GenBeanEntity;
import com.alibaba.fastjson.JSON;

import org.springframework.stereotype.Service;

import java.util.Map;

import javax.annotation.Resource;

import com.heaven.palace.purplecloudpalace.modular.code.service.ITableConvertServer;

/**
 * 导入的sql实现
 *
 * @author ZhouShengEn on 2017/10/25.
 */
@Service("sqlTableConvertServer")
public class TableConvertServiceOfSqlImpl implements ITableConvertServer {

    @Resource(name = "dbTableConvertServer")
    private ITableConvertServer tableConvertServer;

    @Override
    public void importBean(String json, int userId) {
        Map<String, String> map = JSON.parseObject(json, Map.class);
        String dbType = map.get("dbType");
        String sql = map.get("sql");
        GenBeanEntity beanEntity = SqlConvertFactory.getReadTable(dbType).parseSql(sql);
        tableConvertServer.importBean(JSON.toJSONString(beanEntity), userId);

    }
}
