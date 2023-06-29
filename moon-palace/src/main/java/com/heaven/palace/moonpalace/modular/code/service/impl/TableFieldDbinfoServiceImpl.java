package com.heaven.palace.moonpalace.modular.code.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.heaven.palace.moonpalace.modular.code.dao.TableFieldDbinfoDao;
import com.heaven.palace.moonpalace.modular.code.model.TableFieldDbinfoModel;
import com.heaven.palace.moonpalace.modular.code.service.ITableFieldDbinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service
 *
 * @author ZhouShengEn
 * @Date 2022-8-25
 */
@Service
public class TableFieldDbinfoServiceImpl implements ITableFieldDbinfoService {

    @Autowired
    private TableFieldDbinfoDao tableFieldDbinfoDao;

    @Override
    public Integer insert(TableFieldDbinfoModel entity) {
        return tableFieldDbinfoDao.insert(entity);
    }

    @Override
    public Integer deleteById(Integer id) {
        return tableFieldDbinfoDao.deleteById(id);
    }

    @Override
    public Integer updateById(TableFieldDbinfoModel entity) {
        return tableFieldDbinfoDao.updateById(entity);
    }

    @Override
    public TableFieldDbinfoModel selectById(Integer id) {
        return tableFieldDbinfoDao.selectById(id);
    }

    @Override
    public TableFieldDbinfoModel selectOne(TableFieldDbinfoModel entity) {
        return tableFieldDbinfoDao.selectOne(new QueryWrapper<TableFieldDbinfoModel>(entity));
    }

    @Override
    public Integer selectCount(TableFieldDbinfoModel model) {
        return tableFieldDbinfoDao.selectCount(model);
    }

    @Override
    public List<TableFieldDbinfoModel> selectList(TableFieldDbinfoModel model) {
        return tableFieldDbinfoDao.selectList(model);
    }

    @Override
    public List<TableFieldDbinfoModel> selectPage(Page pagination, TableFieldDbinfoModel model, Wrapper<TableFieldDbinfoModel> wrapper) {
        return tableFieldDbinfoDao.selectPage(pagination,model,wrapper);
    }
    @Override
    public Integer deleteByFieldIds(List<Integer> fieldIds) {
        return tableFieldDbinfoDao.deleteByFieldIds(fieldIds);
    }

    @Override
    public Integer batchInsert(List<TableFieldDbinfoModel> dbInfoModelList) {
        return tableFieldDbinfoDao.batchInsert(dbInfoModelList);
    }
}
