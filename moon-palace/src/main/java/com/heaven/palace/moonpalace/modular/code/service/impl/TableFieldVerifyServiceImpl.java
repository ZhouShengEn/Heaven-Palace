package com.heaven.palace.moonpalace.modular.code.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.heaven.palace.moonpalace.modular.code.dao.TableFieldVerifyDao;
import com.heaven.palace.moonpalace.modular.code.model.TableFieldVerifyModel;
import com.heaven.palace.moonpalace.modular.code.service.ITableFieldVerifyService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service
 *
 * @author ZhouShengEn
 * @Date 2022-8-25
 */
@Service
public class TableFieldVerifyServiceImpl implements ITableFieldVerifyService {

    @Resource
    private TableFieldVerifyDao tableFieldVerifyDao;

    @Override
    public Integer insert(TableFieldVerifyModel entity) {
        return tableFieldVerifyDao.insert(entity);
    }

    @Override
    public Integer deleteById(Integer id) {
        return tableFieldVerifyDao.deleteById(id);
    }

    @Override
    public Integer updateById(TableFieldVerifyModel entity) {
        return tableFieldVerifyDao.updateById(entity);
    }

    @Override
    public TableFieldVerifyModel selectById(Integer id) {
        return tableFieldVerifyDao.selectById(id);
    }

    @Override
    public TableFieldVerifyModel selectOne(TableFieldVerifyModel entity) {
        return tableFieldVerifyDao.selectOne(new QueryWrapper<TableFieldVerifyModel>(entity));
    }

    @Override
    public Integer selectCount(TableFieldVerifyModel model) {
        return tableFieldVerifyDao.selectCount(model);
    }

    @Override
    public List<TableFieldVerifyModel> selectList(TableFieldVerifyModel model) {
        return tableFieldVerifyDao.selectList(model);
    }

    @Override
    public List<TableFieldVerifyModel> selectPage(Page pagination, TableFieldVerifyModel model, Wrapper<TableFieldVerifyModel> wrapper) {
        return tableFieldVerifyDao.selectPage(pagination,model,wrapper);
    }

    @Override
    public Integer deleteByFieldIds(List<Integer> fieldIds) {
        return tableFieldVerifyDao.deleteByFieldIds(fieldIds);
    }

    @Override
    public Integer batchInsert(List<TableFieldVerifyModel> verifyModelList) {
        return tableFieldVerifyDao.batchInsert(verifyModelList);
    }

}
