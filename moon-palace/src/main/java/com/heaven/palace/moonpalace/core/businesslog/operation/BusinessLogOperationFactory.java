package com.heaven.palace.moonpalace.core.businesslog.operation;

import com.heaven.palace.moonpalace.common.persistence.model.BusinessOperationLog;
import com.heaven.palace.moonpalace.component.distributedlock.DistributedLockManage;
import com.heaven.palace.moonpalace.constant.BusinessLogConst;
import com.heaven.palace.moonpalace.core.businesslog.context.BusinessLogTempObject;
import com.heaven.palace.moonpalace.core.businesslog.context.LogCurrentDateHolder;
import com.heaven.palace.moonpalace.core.shiro.ShiroKit;
import com.heaven.palace.moonpalace.core.shiro.ShiroUser;
import com.heaven.palace.moonpalace.modular.system.dao.BusinessOperationLogDao;
import com.heaven.palace.moonpalace.mutidatasource.DynamicDataSource;
import com.heaven.palace.moonpalace.support.DateTimeKit;
import com.heaven.palace.moonpalace.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SqlRunner;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.joda.time.DateTime;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.javers.core.diff.ListCompareAlgorithm.LEVENSHTEIN_DISTANCE;

@Component
public abstract class BusinessLogOperationFactory {

    @Resource
    private Map<String, BusinessLogOperationFactory> logFactoryMap;

    @Resource
    private BusinessOperationLogDao operationLogDao;

    @Resource
    public DynamicDataSource dynamicDateSource;

    @Resource
    private DistributedLockManage distributedLockManage;

    private static final Map<String, BusinessLogOperationFactory> staticLogFactoryMap = new HashMap<>(1);

    public static final ThreadLocal<BusinessLogTempObject> BUSINESS_LOG_TEMP_OBJECT = new ThreadLocal<>();

    public final Javers javers = JaversBuilder.javers().withListCompareAlgorithm(LEVENSHTEIN_DISTANCE).build();

    /**
     * 在子线程中修改主线程的临时线程变量，这里需要手动设置线程安全，对象弱引用时自动释放
     */
    public static final WeakHashMap<String, Integer> TEMP_LOG_RELATE_MAP = new WeakHashMap<>(1);

    public final static String TIME_PATTERN = "'\\d{4}-\\d{2}-\\d{2}.*?'";

    public final static String UNDO_LOG = "无修改;";

    @PostConstruct
    public void init() {
        logFactoryMap.forEach(staticLogFactoryMap::put);
    }

    /**
     * 记录日志
     */
    public abstract void writeBusinessLog();

    /**
     * sql执行
     *
     * @return
     */
    public void executeSql(BusinessOperationLog businessOperationLog) throws SQLException, ParseException {
        String sqlText = businessOperationLog.getSqlText();
        // 无修改不进行sql操作
        if (StringUtils.isNotEmpty(sqlText) && !UNDO_LOG.equals(sqlText)) {
            Pattern compile = Pattern.compile(TIME_PATTERN);
            Matcher matcher = compile.matcher(sqlText);
            while (matcher.find()) {
                String group = matcher.group().replaceAll("'", "");
                sqlText = sqlText.replace(group, DateUtil.format2String(group, DateTimeKit.NORM_DATETIME_PATTERN));
            }
            try (Connection connection = dynamicDateSource.getDataSource(businessOperationLog.getDbId()).getConnection();){
                new SqlRunner(connection).run(sqlText);
            }
           
        }
        ShiroUser user = ShiroKit.getUser();
        execute(() -> assembleBaseUserInfo(businessOperationLog, user));

    }

    public static BusinessLogOperationFactory route(String logOperateBeanName) {
        return null != staticLogFactoryMap.get(logOperateBeanName) ? staticLogFactoryMap.get(logOperateBeanName)
                : staticLogFactoryMap.get("businessLogNoneOperation");
    }

    public void execute(Callable<BusinessOperationLog> task) {
        ShiroUser user = ShiroKit.getUser();
        Thread thread = Thread.currentThread();
        String logKey;
        if (null == LogCurrentDateHolder.get()) {
            LogCurrentDateHolder.set(logKey = new String(String.valueOf(thread.getId()).concat("-").concat(DateTime.now().toString())));
        } else {
            logKey = new String(LogCurrentDateHolder.get());
        }
        asyncExecute(task, user, logKey);
        // 在gc后释放weakHashmap里对应的数据
        logKey = null;
    }

    private void asyncExecute(Callable<BusinessOperationLog> task, ShiroUser user, String logKey) {
        // 日志的操作异步执行，不影响主业务的行为
        // 考虑到线程安全，复合操作日志记录的子线程需要等待最初的子线程返回id作为parentId
        ForkJoinPool.commonPool().execute(() -> distributedLockManage.lock(logKey, 10L, TimeUnit.SECONDS, Boolean.TRUE, () -> {
            try {
                BusinessOperationLog businessOperationLog = task.call();
                if (null != businessOperationLog) {
                    Integer parentId = TEMP_LOG_RELATE_MAP.get(logKey);
                    // 这里的设计同一线程只允许出现二级日志，暂不考虑多级日志
                    businessOperationLog.setParentId(parentId);
                    if (null != parentId && BusinessLogConst.SyncStatusEnum.Need_Synchronize.getStatus().equals(businessOperationLog.getSyncStatus())) {
                        // 关联子操作状态不看作需要同步
                        businessOperationLog.setSyncStatus(BusinessLogConst.SyncStatusEnum.Normal.getStatus());
                    }
                    operationLogDao.insert(assembleBaseUserInfo(businessOperationLog, user));
                    if (null == parentId) {
                        TEMP_LOG_RELATE_MAP.put(logKey, businessOperationLog.getId());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
    }

    private BusinessOperationLog assembleBaseUserInfo(BusinessOperationLog businessOperationLog, ShiroUser user) {
        Date date = new Date();
        if (null == user) {
            businessOperationLog.setCreateBy("未登录");
            businessOperationLog.setUpdateBy("未登录");
            businessOperationLog.setUserId(0);
        } else {
            businessOperationLog.setCreateBy(user.getName());
            businessOperationLog.setUpdateBy(user.getName());
            businessOperationLog.setUserId(user.getId());

        }
        businessOperationLog.setCreateTime(date);
        businessOperationLog.setUpdateTime(date);
        return businessOperationLog;

    }
}
