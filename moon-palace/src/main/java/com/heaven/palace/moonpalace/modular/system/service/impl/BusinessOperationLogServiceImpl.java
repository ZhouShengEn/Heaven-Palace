package com.heaven.palace.moonpalace.modular.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.heaven.palace.moonpalace.base.response.ObjectRestResponse;
import com.heaven.palace.moonpalace.base.response.PageResultResponse;
import com.heaven.palace.moonpalace.common.persistence.model.BusinessOperationLog;
import com.heaven.palace.moonpalace.constant.BusinessLogConst;
import com.heaven.palace.moonpalace.core.businesslog.operation.BusinessLogOperationFactory;
import com.heaven.palace.moonpalace.core.shiro.ShiroKit;
import com.heaven.palace.moonpalace.exception.BusinessException;
import com.heaven.palace.moonpalace.exception.BusinessExceptionEnum;
import com.heaven.palace.moonpalace.modular.system.dao.BusinessOperationLogDao;
import com.heaven.palace.moonpalace.modular.system.service.BusinessOperationLogService;
import com.heaven.palace.moonpalace.modular.system.vo.BusinessLogPageReqVO;
import com.heaven.palace.moonpalace.modular.system.vo.BusinessLogPageResVO;
import com.heaven.palace.moonpalace.util.MappingUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author :zhoushengen
 * @date : 2022/9/6
 */
@Service
@Transactional
@Slf4j
public class BusinessOperationLogServiceImpl implements BusinessOperationLogService {

    @Resource
    private BusinessOperationLogDao operationLogDao;

    @Override
    public PageResultResponse<BusinessLogPageResVO> page(BusinessLogPageReqVO logPageReqVO) {
        Page<Object> result = PageHelper.startPage(logPageReqVO.getPage(), logPageReqVO.getLimit(), "id desc");
        // 查询同步日志时非admin用户只允许查看自己
        if (null != logPageReqVO.getSyncStatus()
            && BusinessLogConst.SyncStatusEnum.Need_Synchronize.getStatus().equals(logPageReqVO.getSyncStatus())
            && !"admin".equals(ShiroKit.getUser().getAccount())) {
            logPageReqVO.setUserId(ShiroKit.getUser().getId());
        }
        List<BusinessLogPageResVO> businessOperationLogs = operationLogDao.page(logPageReqVO);
        businessOperationLogs.forEach(log -> {
            BusinessLogPageReqVO businessLogPageReqVO = new BusinessLogPageReqVO();
            businessLogPageReqVO.setParentId(log.getId());
            log.setChildLogs(operationLogDao.page(businessLogPageReqVO));
        });
        return new PageResultResponse<>(result.getTotal(), businessOperationLogs);
    }

    @Override
    public ObjectRestResponse synchronize(Integer logId, Integer targetDbId) {
        BusinessOperationLog businessOperationLog = operationLogDao.selectById(logId);
        if (null == businessOperationLog || !BusinessLogConst.SyncStatusEnum.Need_Synchronize.getStatus()
            .equals(businessOperationLog.getSyncStatus()) || null == businessOperationLog.getSqlText()) {
            throw new BusinessException(BusinessExceptionEnum.BUSINESS_LOG_SYNCHRONIZE_QUERY_ERROR);
        }
        BusinessOperationLog updateLog = MappingUtils.beanConvert(businessOperationLog, BusinessOperationLog.class);
        // 初始化已同步日志数据
        initSynchronizedBusinessOperationLog(targetDbId, businessOperationLog);

        try {
            BusinessLogOperationFactory
                .route(BusinessLogConst.SqlTypeEnum.getOperationBeanNameByOperate(businessOperationLog.getTitle()))
                .executeSql(businessOperationLog);
            updateLog.setUpdateTime(new Date());
            updateLog.setUpdateBy(ShiroKit.getUser().getAccount());
            updateLog.setSyncStatus(BusinessLogConst.SyncStatusEnum.Normal.getStatus());
            operationLogDao.updateById(updateLog);
        } catch (Exception e) {
            log.error("synchronize sql error logId:{}, targetDbId:{}, exception:{}", logId, targetDbId, e);
            throw new BusinessException(BusinessExceptionEnum.BUSINESS_LOG_SYNCHRONIZE_RUN_ERROR.getCode(),
                String.format(BusinessExceptionEnum.BUSINESS_LOG_SYNCHRONIZE_RUN_ERROR.getMessage(), e.getMessage()));
        }

        // 同步子集日志
        LambdaQueryWrapper<BusinessOperationLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BusinessOperationLog::getParentId, logId);
        List<BusinessOperationLog> businessOperationLogs = operationLogDao.selectList(wrapper);
        if (CollectionUtils.isNotEmpty(businessOperationLogs)) {
            businessOperationLogs.forEach(childLog -> synchronizeChild(childLog, targetDbId));
        }

        return new ObjectRestResponse<>().message("同步成功！");
    }

    /**
     * 同步二级日志
     * 
     * @param childLog
     * @param targetDbId
     */
    public void synchronizeChild(BusinessOperationLog childLog, Integer targetDbId) {
        // 初始化已同步日志数据
        initSynchronizedBusinessOperationLog(targetDbId, childLog);
        try {
            BusinessLogOperationFactory
                .route(BusinessLogConst.SqlTypeEnum.getOperationBeanNameByOperate(childLog.getTitle()))
                .executeSql(childLog);
        } catch (Exception e) {
            log.error("synchronize sql error logId:{}, targetDbId:{}, exception:{}", childLog.getId(), targetDbId, e);
            throw new BusinessException(BusinessExceptionEnum.BUSINESS_LOG_SYNCHRONIZE_RUN_ERROR.getCode(),
                String.format(BusinessExceptionEnum.BUSINESS_LOG_SYNCHRONIZE_RUN_ERROR.getMessage(), e.getMessage()));
        }
    }

    @Override
    public ObjectRestResponse<Void> synchronizeDelete(Integer logId) {
        BusinessOperationLog businessOperationLog = operationLogDao.selectById(logId);
        if (null == businessOperationLog || !BusinessLogConst.SyncStatusEnum.Need_Synchronize.getStatus()
            .equals(businessOperationLog.getSyncStatus())) {
            throw new BusinessException(BusinessExceptionEnum.BUSINESS_LOG_SYNCHRONIZE_QUERY_ERROR);
        }
        businessOperationLog.setSyncStatus(BusinessLogConst.SyncStatusEnum.Normal.getStatus());
        businessOperationLog.setUpdateTime(new Date());
        businessOperationLog.setUpdateBy(ShiroKit.getUser().getName());
        operationLogDao.updateById(businessOperationLog);
        return new ObjectRestResponse<>().message("删除成功！");
    }

    /**
     * 初始化已同步日志数据
     * 
     * @param targetDbId
     * @param businessOperationLog
     * @return
     */
    private void initSynchronizedBusinessOperationLog(Integer targetDbId, BusinessOperationLog businessOperationLog) {
        businessOperationLog.setSyncStatus(BusinessLogConst.SyncStatusEnum.Synchronized.getStatus());
        businessOperationLog.setDbId(targetDbId);
        businessOperationLog.setId(null);
    }

}
