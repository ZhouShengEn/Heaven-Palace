package com.heaven.palace.purplecloudpalace.modular.code.service.impl;



import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.heaven.palace.purplecloudpalace.modular.code.dao.DbInfoDao;
import com.heaven.palace.purplecloudpalace.modular.code.model.DbInfoModel;
import com.heaven.palace.purplecloudpalace.modular.code.service.IDbInfoService;

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

    @Override
    public Integer insert(DbInfoModel entity) {

        return dbInfoDao.insert(entity);
    }

    @Override
    public Integer deleteById(Integer id) {
        return dbInfoDao.deleteById(id);
    }

    @Override
    public Integer updateById(DbInfoModel entity) {
        return dbInfoDao.updateById(entity);
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

}
