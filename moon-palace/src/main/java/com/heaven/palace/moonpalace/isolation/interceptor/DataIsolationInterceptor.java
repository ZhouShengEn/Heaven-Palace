package com.heaven.palace.moonpalace.isolation.interceptor;

import com.heaven.palace.jasperpalace.base.context.CurrentBaseContext;
import com.heaven.palace.moonpalace.isolation.annotation.DataIsolation;
import com.heaven.palace.moonpalace.isolation.context.DataIsolationContext;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.update.Update;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.Properties;

/**
 * @author :zhoushengen
 * @date : 2023/1/30
 */
@Component
@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})
})
@Slf4j
public class DataIsolationInterceptor implements Interceptor {
    
    private static final String USER_ID = "create_by";
    

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        try {
            DataIsolation dataIsolation = DataIsolationContext.local.get();
            if (null == dataIsolation) {
                return invocation.proceed();
            }
            String userId = String.valueOf(CurrentBaseContext.getUserId());
            if (StringUtils.isAnyEmpty(userId)) {
                return invocation.proceed();
            }
            if (invocation.getTarget() instanceof StatementHandler) {
                String condition = createCondition(dataIsolation, userId);
                if (null == condition){
                    return invocation.proceed();
                }
                StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
                MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
                String sql = statementHandler.getBoundSql().getSql();
                MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
                log.info("data permission old sql :{}", sql);
                sql = generateSql(mappedStatement, condition, sql);
                log.info("data permission new sql :{}", sql);
                metaObject.setValue("delegate.boundSql.sql", sql);
               
            }

        } catch (Exception e) {
            log.error("data permission intercept error! reason: {}", e.getMessage());
            e.printStackTrace();
        }
        return invocation.proceed();
    }

    /**
     * 构造sql
     * @param mappedStatement
     * @param condition
     * @param sql
     * @return
     * @throws JSQLParserException
     */
    private String generateSql(MappedStatement mappedStatement, String condition, String sql) throws JSQLParserException {
        Expression oldWhere;
        Expression addWhere = CCJSqlParserUtil.parseCondExpression(condition);
        switch (mappedStatement.getSqlCommandType()){
            case UPDATE:
                Update update = (Update) CCJSqlParserUtil.parse(sql);
                oldWhere = update.getWhere();
                update.setWhere(null == oldWhere ? addWhere : new AndExpression(oldWhere, addWhere));
                return update.toString();
            case SELECT:
                Select select = (Select) CCJSqlParserUtil.parse(sql);
                PlainSelect selectBody = (PlainSelect) select.getSelectBody();
                oldWhere = selectBody.getWhere();
                selectBody.setWhere(null == oldWhere ? addWhere : new AndExpression(oldWhere, addWhere));
                return selectBody.toString();
            case DELETE:
                Delete delete = (Delete) CCJSqlParserUtil.parse(sql);
                oldWhere = delete.getWhere();
                delete.setWhere(null == oldWhere ? addWhere : new AndExpression(oldWhere, addWhere));
                return delete.toString();
            default:
                return sql;
        }
    }
    

    /**
     * 构造where条件
     * @param dataIsolation
     * @param userId
     * @return
     */
    private String createCondition(DataIsolation dataIsolation, String userId) {
        switch (dataIsolation.level()){
            case USER:
            case ALL:
                return  USER_ID.concat(" = ").concat(userId);
            default:
                return null;
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
