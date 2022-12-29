package com.heaven.palace.purplecloudpalace.modular.code.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.heaven.palace.purplecloudpalace.modular.code.dao.TemplateFileDao;
import com.heaven.palace.purplecloudpalace.modular.code.model.TemplateFileModel;
import com.heaven.palace.purplecloudpalace.modular.code.service.ITemplateFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 模板内容Service
 *
 * @author ZhouShengEn
 * @Date 2022-8-25
 */
@Service
public class TemplateFileServiceImpl implements ITemplateFileService {

    @Autowired
    private TemplateFileDao templateFileDao;

    @Override
    public Integer insert(TemplateFileModel entity) {
        return templateFileDao.insert(entity);
    }

    @Override
    public Integer deleteById(Integer id) {
        return templateFileDao.deleteById(id);
    }

    @Override
    public Integer updateById(TemplateFileModel entity) {
        return templateFileDao.updateById(entity);
    }

    @Override
    public TemplateFileModel selectById(Integer id) {
        return templateFileDao.selectById(id);
    }

    @Override
    public TemplateFileModel selectOne(TemplateFileModel entity) {
        return templateFileDao.selectOne(new QueryWrapper<TemplateFileModel>(entity));
    }

    @Override
    public Integer selectCount(TemplateFileModel model) {
        return templateFileDao.selectCount(model);
    }

    @Override
    public List<TemplateFileModel> selectList(TemplateFileModel model) {
        return templateFileDao.selectList(model);
    }

    @Override
    public List<TemplateFileModel> selectPage(Page pagination, TemplateFileModel model, Wrapper<TemplateFileModel> wrapper) {
        return templateFileDao.selectPage(pagination,model,wrapper);
    }

}
