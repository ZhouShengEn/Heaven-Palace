package com.heaven.palace.moonpalace.modular.code.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.heaven.palace.moonpalace.modular.code.dao.TemplateGroupDao;
import com.heaven.palace.moonpalace.modular.code.model.TemplateGroupModel;
import com.heaven.palace.moonpalace.modular.code.service.ITemplateGroupService;
import com.heaven.palace.moonpalace.modular.code.vo.GenTemplateGroupPageReqVO;
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
public class TemplateGroupServiceImpl implements ITemplateGroupService {

    @Autowired
    private TemplateGroupDao templateGroupDao;

    @Override
    public Integer insert(TemplateGroupModel entity) {
        return templateGroupDao.insert(entity);
    }

    @Override
    public Integer deleteById(Integer id) {
        return templateGroupDao.deleteById(id);
    }

    @Override
    public Integer updateById(TemplateGroupModel entity) {
        return templateGroupDao.updateById(entity);
    }

    @Override
    public TemplateGroupModel selectById(Integer id) {
        return templateGroupDao.selectById(id);
    }

    @Override
    public TemplateGroupModel selectOne(TemplateGroupModel entity) {
        return templateGroupDao.selectOne(new QueryWrapper<TemplateGroupModel>(entity));
    }

    @Override
    public Integer selectCount(TemplateGroupModel model) {
        return templateGroupDao.selectCount(model);
    }

    @Override
    public List<TemplateGroupModel> selectList(TemplateGroupModel model) {
        return templateGroupDao.selectList(model);
    }

    @Override
    public List<TemplateGroupModel> selectPage(Page pagination, TemplateGroupModel model, Wrapper<TemplateGroupModel> wrapper) {
        return templateGroupDao.selectPage(pagination,model,wrapper);
    }

    @Override
    public List<TemplateGroupModel> page(GenTemplateGroupPageReqVO reqVO) {
        return templateGroupDao.page(reqVO);
    }

}
