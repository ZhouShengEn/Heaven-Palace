package com.heaven.palace.purplecloudpalace.modular.code.service.impl;


import com.heaven.palace.purplecloudpalace.modular.code.dao.TableBaseFieldDao;
import com.heaven.palace.purplecloudpalace.modular.code.model.TableBaseFieldModel;
import com.heaven.palace.purplecloudpalace.modular.code.service.ITableBaseFieldService;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
public class TableBaseFieldServiceImpl implements ITableBaseFieldService {

    @Autowired
    private TableBaseFieldDao tableBaseFieldDao;

    @Override
    public Integer insert(TableBaseFieldModel entity) {
        return tableBaseFieldDao.insert(entity);
    }

    @Override
    public Integer deleteById(Integer id) {
        return tableBaseFieldDao.deleteById(id);
    }

    @Override
    public Integer updateById(TableBaseFieldModel entity) {
        return tableBaseFieldDao.updateById(entity);
    }

    @Override
    public TableBaseFieldModel selectById(Integer id) {
        return tableBaseFieldDao.selectById(id);
    }

    @Override
    public TableBaseFieldModel selectOne(TableBaseFieldModel entity) {
        return tableBaseFieldDao.selectOne(new QueryWrapper<TableBaseFieldModel>(entity));
    }

    @Override
    public Integer selectCount(TableBaseFieldModel model) {
        return tableBaseFieldDao.selectCount(model);
    }

    @Override
    public List<TableBaseFieldModel> selectList(TableBaseFieldModel model) {
        return tableBaseFieldDao.selectList(model);
    }

    @Override
    public List<TableBaseFieldModel> selectPage(Page pagination, TableBaseFieldModel model, Wrapper<TableBaseFieldModel> wrapper) {
        return tableBaseFieldDao.selectPage(pagination,model,wrapper);
    }

}
