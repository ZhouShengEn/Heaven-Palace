package com.heaven.palace.moonpalace.modular.system.service.impl;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.heaven.palace.moonpalace.base.response.ObjectRestResponse;
import com.heaven.palace.moonpalace.constant.RedisCacheKeyConst;
import com.heaven.palace.moonpalace.core.shiro.ShiroKit;
import com.heaven.palace.moonpalace.exception.BusinessException;
import com.heaven.palace.moonpalace.exception.BusinessExceptionEnum;
import com.heaven.palace.moonpalace.modular.code.model.DbInfoModel;
import com.heaven.palace.moonpalace.modular.code.model.CustomModuleModel;
import com.heaven.palace.moonpalace.modular.system.dao.DbInfoDao;
import com.heaven.palace.moonpalace.modular.system.dao.CustomModuleDao;
import com.heaven.palace.moonpalace.modular.system.enums.DbCategoryEnum;
import com.heaven.palace.moonpalace.modular.system.service.IDbInfoService;
import com.heaven.palace.moonpalace.modular.system.vo.DbInfoPageReqVO;
import com.heaven.palace.moonpalace.modular.system.vo.DbinfoPageResVO;
import com.heaven.palace.moonpalace.modular.system.warpper.BeanKeyConvert;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.subject.Subject;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 数据库管理Service
 *
 * @author ZhouShengEn
 * @Date 2022-8-25
 */
@Service
public class DbInfoServiceImpl implements IDbInfoService {

    @Autowired
    private DbInfoDao dbInfoDao;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private CustomModuleDao customModuleDao;

    private final ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 10, 20
        , TimeUnit.SECONDS, new LinkedBlockingQueue<>(1), Executors.defaultThreadFactory(),
        new ThreadPoolExecutor.AbortPolicy());

    @Override
    public void insert(DbInfoModel entity) {
        DbInfoModel dbInfoModel = new DbInfoModel();
        dbInfoModel.setAlias(entity.getAlias());
        List<DbInfoModel> dbInfoModels = selectList(dbInfoModel);
        if (!CollectionUtils.isEmpty(dbInfoModels) && dbInfoModels.get(0).getCategory().equals(entity.getCategory())) {
            throw new BusinessException(BusinessExceptionEnum.DB_INFO_ALIAS_REPEAT_ERROR);
        }
        dbInfoDao.insert(entity);
        updateDynamicDataSourcesCache(entity);
    }

    @Override
    public void deleteById(Integer id) {
        DbInfoModel model = dbInfoDao.selectById(id);
        if (null != model) {
            dbInfoDao.deleteById(id);
            if (DbCategoryEnum.BUSINESS_DBS.ordinal() == model.getCategory()) {
                String datasourceKey = RedisCacheKeyConst.GEN_DYNAMIC_DATASOURCE_KEY_PREFIX.concat(
                    String.valueOf(model.getId()));
                redissonClient.getBucket(datasourceKey).deleteAsync();
            }

        }

    }

    @Override
    public void updateById(DbInfoModel entity) {
        DbInfoModel dbSelect = dbInfoDao.selectById(entity.getId());
        if (StringUtils.isEmpty(entity.getAlias())) {
            throw new BusinessException(BusinessExceptionEnum.DB_INFO_ALIAS_NULL_ERROR);
        }
        if (null != dbSelect && !Objects.equals(dbSelect.getAlias(), entity.getAlias())) {
            DbInfoModel dbInfoModel = new DbInfoModel();
            dbInfoModel.setAlias(entity.getAlias());
            List<DbInfoModel> dbInfoModels = selectList(dbInfoModel);
            if (!CollectionUtils.isEmpty(dbInfoModels) && dbInfoModels.get(0).getCategory().equals(entity.getCategory())) {
                throw new BusinessException(BusinessExceptionEnum.DB_INFO_ALIAS_REPEAT_ERROR);
            }
        }
        dbInfoDao.updateById(entity);
        updateDynamicDataSourcesCache(entity);
    }

    @Override
    public DbInfoModel selectById(Integer id) {
        return dbInfoDao.selectById(id);
    }

    @Override
    public DbInfoModel selectOne(DbInfoModel entity) {
        return dbInfoDao.selectOne(new QueryWrapper<DbInfoModel>(entity));
    }

    @Override
    public Integer selectCount(DbInfoModel model) {
        return dbInfoDao.selectCount(model);
    }

    @Override
    public List<DbInfoModel> selectList(DbInfoModel model) {
        return dbInfoDao.selectList(model);
    }

    @Override
    public List<DbInfoModel> selectPage(Page pagination, DbInfoModel model, Wrapper<DbInfoModel> wrapper) {
        return dbInfoDao.selectPage(pagination, model, wrapper);
    }

    @SneakyThrows
    @Override
    public ObjectRestResponse<Void> delete(List<Integer> ids) {
        Subject subject = ShiroKit.getSubject();
        CompletableFuture<Void> completableFuture = CompletableFuture.allOf(
            ids.stream().map(id -> CompletableFuture.runAsync(() -> {
                ShiroKit.setUser(subject);
                deleteById(id);
            }, executor)).toArray(CompletableFuture[]::new));

        completableFuture.get(10, TimeUnit.SECONDS);

        return new ObjectRestResponse<>().message("删除成功！");
    }

    @Override
    public List<DbinfoPageResVO> page(DbInfoPageReqVO pageReqVO) {
        List<DbinfoPageResVO> dbinfoPageResVOS = dbInfoDao.page(pageReqVO);
        QueryWrapper<CustomModuleModel> queryWrapper = new QueryWrapper<CustomModuleModel>();
        List<CustomModuleModel> CustomModuleModels = customModuleDao.selectList(queryWrapper);
        if (!CollectionUtils.isEmpty(CustomModuleModels) && !CollectionUtils.isEmpty(dbinfoPageResVOS)) {
            Map<Integer, List<CustomModuleModel>> listMap = CustomModuleModels.stream()
                .collect(Collectors.groupingBy(CustomModuleModel::getDefaultDbId));
            dbinfoPageResVOS.forEach(
                db -> db.setIsDefault(null != listMap.get(db.getId()) ? Boolean.TRUE : Boolean.FALSE));
        }
        BeanKeyConvert.systemUserNameConvert(dbinfoPageResVOS);
        return dbinfoPageResVOS;
    }

    public void updateDynamicDataSourcesCache(DbInfoModel model) {
        String datasourceKey = RedisCacheKeyConst.GEN_DYNAMIC_DATASOURCE_KEY_PREFIX.concat(
            String.valueOf(model.getId()));
        redissonClient.getBucket(datasourceKey).setAsync(JSON.toJSONString(model));

    }

}
