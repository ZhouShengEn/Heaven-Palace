package com.heaven.palace.moonpalace.modular.code.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.heaven.palace.moonpalace.modular.code.dao.TemplateShareDao;
import com.heaven.palace.moonpalace.modular.code.model.TemplateShareModel;
import com.heaven.palace.moonpalace.modular.code.service.ITemplateShareService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 模板分享管理Service
 *
 * @author ZhouShengEn
 * @Date 2022-8-25
 */
@Service
public class TemplateShareServiceImpl implements ITemplateShareService {

    @Resource
    private TemplateShareDao templateShareDao;

    @Override
    public Integer insert(TemplateShareModel entity) {
        return templateShareDao.insert(entity);
    }

    @Override
    public Integer deleteById(Integer id) {
        return templateShareDao.deleteById(id);
    }

    @Override
    public Integer updateById(TemplateShareModel entity) {
        return templateShareDao.updateById(entity);
    }

    @Override
    public TemplateShareModel selectById(Integer id) {
        return templateShareDao.selectById(id);
    }

    @Override
    public TemplateShareModel selectOne(TemplateShareModel entity) {
        return templateShareDao.selectOne(new QueryWrapper<TemplateShareModel>(entity));
    }

    @Override
    public Integer selectCount(TemplateShareModel model) {
        return templateShareDao.selectCount(model);
    }

    @Override
    public List<TemplateShareModel> selectList(TemplateShareModel model) {
        return templateShareDao.selectList(model);
    }

    @Override
    public List<TemplateShareModel> selectPage(Page pagination, TemplateShareModel model, Wrapper<TemplateShareModel> wrapper) {
        return templateShareDao.selectPage(pagination, model, wrapper);
    }

}
