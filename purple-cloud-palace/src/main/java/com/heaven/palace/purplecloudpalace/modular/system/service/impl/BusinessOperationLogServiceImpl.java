package com.heaven.palace.purplecloudpalace.modular.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.heaven.palace.purplecloudpalace.common.constant.BusinessOperationLogConstant;
import com.heaven.palace.purplecloudpalace.common.persistence.model.BusinessOperationLog;
import com.heaven.palace.purplecloudpalace.common.persistence.vo.BusinessOperationLogVo;
import com.heaven.palace.purplecloudpalace.constant.RedisCacheKeyConst;
import com.heaven.palace.purplecloudpalace.core.shiro.ShiroKit;
import com.heaven.palace.purplecloudpalace.modular.system.dao.BusinessOperationLogDao;
import com.heaven.palace.purplecloudpalace.modular.system.service.BusinessOperationLogService;
import com.heaven.palace.purplecloudpalace.mutidatasource.config.OProductDevDataSourceProperties;
import com.heaven.palace.purplecloudpalace.business.constant.EnvEnum;
import com.heaven.palace.purplecloudpalace.util.MappingUtils;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author :zhoushengen
 * @date : 2022/9/6
 */
@Service
@Transactional
public class BusinessOperationLogServiceImpl implements BusinessOperationLogService {

    @Resource
    private BusinessOperationLogDao operationLogDao;
    
    @Autowired
    private RedissonClient redissonClient;
    
    @Override
    public List<BusinessOperationLogVo> queryListByType(String type, String descField, Integer env) {
        QueryWrapper<BusinessOperationLog> wrapper = new QueryWrapper<>();
        wrapper.eq("type", type);
        wrapper.eq("env", null != env ? (Integer) env:EnvEnum.DEV.getId());
        wrapper.orderByDesc(descField);
        return MappingUtils.beanListConvert(operationLogDao.selectList(wrapper), BusinessOperationLogVo.class);
    }

    @Override
    public void insert(BusinessOperationLog businessOperationLog, Integer env) {
        businessOperationLog.setEnv(null != env ? (Integer) env :EnvEnum.DEV.getId());
        operationLogDao.insert(businessOperationLog);
    }
}
