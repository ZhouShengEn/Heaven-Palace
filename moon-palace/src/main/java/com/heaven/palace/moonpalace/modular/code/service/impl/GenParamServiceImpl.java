package com.heaven.palace.moonpalace.modular.code.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.heaven.palace.moonpalace.modular.code.dao.GenParamDao;
import com.heaven.palace.moonpalace.modular.code.model.GenParamModel;
import com.heaven.palace.moonpalace.modular.code.service.IGenParamService;
import com.heaven.palace.moonpalace.modular.code.vo.GenParamPageReqVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 生成参数Service
 *
 * @author ZhouShengEn
 * @Date 2022-8-25
 */
@Service
public class GenParamServiceImpl implements IGenParamService {

    @Autowired
    private GenParamDao genParamDao;

    @Override
    public Integer insert(GenParamModel entity) {
        return genParamDao.insert(entity);
    }

    @Override
    public Integer deleteById(Integer id) {
        return genParamDao.deleteById(id);
    }

    @Override
    public Integer updateById(GenParamModel entity) {
        return genParamDao.updateById(entity);
    }

    @Override
    public GenParamModel selectById(Integer id) {
        return genParamDao.selectById(id);
    }

    @Override
    public GenParamModel selectOne(GenParamModel entity) {
        return genParamDao.selectOne(new QueryWrapper<GenParamModel>(entity));
    }

    @Override
    public Integer selectCount(GenParamModel model) {
        return genParamDao.selectCount(model);
    }

    @Override
    public List<GenParamModel> selectList(GenParamModel model) {
        return genParamDao.selectList(model);
    }

    @Override
    public List<GenParamModel> selectPage(Page pagination, GenParamModel model, Wrapper<GenParamModel> wrapper) {
        return genParamDao.selectPage(pagination,model,wrapper);
    }

    @Override
    public List<GenParamModel> page(GenParamPageReqVO model) {
        return genParamDao.page(model);
    }

}
