package com.heaven.palace.purplecloudpalace.modular.code.service.impl;



import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.heaven.palace.purplecloudpalace.modular.code.dao.TemplateDao;
import com.heaven.palace.purplecloudpalace.modular.code.dao.TemplateFileDao;
import com.heaven.palace.purplecloudpalace.modular.code.model.TemplateFileModel;
import com.heaven.palace.purplecloudpalace.modular.code.model.TemplateModel;
import com.heaven.palace.purplecloudpalace.modular.code.service.ITemplateService;

/**
 * 模板管理Service
 *
 * @author ZhouShengEn
 * @Date 2022-8-25
 */
@Service
public class TemplateServiceImpl implements ITemplateService {

    @Autowired
    private TemplateDao templateDao;
    @Autowired
    private TemplateFileDao templateFileDao;

    @Override
    public Integer insert(TemplateModel entity, TemplateFileModel fileModel) {
        templateDao.insert(entity);
        fileModel.setTemplateId(entity.getId());
        return templateFileDao.insert(fileModel);
    }

    @Override
    public Integer deleteById(Integer id) {
        templateFileDao.deleteByTemplateId(id);
        return templateDao.deleteById(id);
    }

    @Override
    public Integer updateById(TemplateModel entity, TemplateFileModel fileModel) {
        fileModel.setTemplateId(entity.getId());
        int nums = templateFileDao.updateTemplateId(fileModel);
        if (nums == 0) {
            templateFileDao.insert(fileModel);
        }
        return templateDao.updateById(entity);
    }

    @Override
    public TemplateModel selectById(Integer id) {
        return templateDao.selectById(id);
    }

    @Override
    public TemplateModel selectOne(TemplateModel entity) {
        return templateDao.selectOne(new QueryWrapper<TemplateModel>(entity));
    }

    @Override
    public Integer selectCount(TemplateModel model) {
        return templateDao.selectCount(model);
    }

    @Override
    public List<TemplateModel> selectList(TemplateModel model) {
        return templateDao.selectList(model);
    }

    @Override
    public List<TemplateModel> selectPage(Page pagination, TemplateModel model, Wrapper<TemplateModel> wrapper) {
        return templateDao.selectPage(pagination, model, wrapper);
    }

    @Override
    public List<TemplateModel> getTemplateByIds(String[] templates) {
        return templateDao.getTemplateByIds(templates);
    }

    @Override
    public List<TemplateModel> getAllTemplateByGroupId(String groupId) {
        List<TemplateModel> list = templateDao.getAllTemplateByGroupId(groupId);
        for (int i = 0; i < (list == null ? 0 : list.size()); i++) {
            TemplateFileModel fileModel = new TemplateFileModel();
            fileModel.setTemplateId(list.get(i).getId());
            list.get(i).setFileModel(templateFileDao.selectOne(new QueryWrapper<TemplateFileModel>(fileModel)));
        }
        return list;
    }

}
