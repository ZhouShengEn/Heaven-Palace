package com.heaven.palace.moonpalace.modular.code.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.heaven.palace.moonpalace.common.persistence.dao.UserMapper;
import com.heaven.palace.moonpalace.common.persistence.model.User;
import com.heaven.palace.moonpalace.core.shiro.ShiroKit;
import com.heaven.palace.moonpalace.modular.code.dao.TemplateDao;
import com.heaven.palace.moonpalace.modular.code.dao.TemplateFileDao;
import com.heaven.palace.moonpalace.modular.code.model.TemplateFileModel;
import com.heaven.palace.moonpalace.modular.code.model.TemplateModel;
import com.heaven.palace.moonpalace.modular.code.service.ITemplateService;
import com.heaven.palace.moonpalace.modular.code.vo.GenTemplatePageReqVO;
import com.heaven.palace.moonpalace.modular.code.vo.GenTemplatePageResVO;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 模板管理Service
 *
 * @author ZhouShengEn
 * @Date 2022-8-25
 */
@Service
public class TemplateServiceImpl implements ITemplateService {

    @Resource
    private TemplateDao templateDao;
    @Resource
    private TemplateFileDao templateFileDao;
    @Resource
    private UserMapper userMapper;

    @Override
    public Integer insert(TemplateModel entity, TemplateFileModel fileModel) {
        fileModel.setFile(hanlderFileEncode(fileModel.getFile()));
        entity.setUserId(ShiroKit.getUser().getId());
        entity.setCrtUserId(ShiroKit.getUser().getId());
        entity.setCrtUserName(ShiroKit.getUser().getName());
        entity.setMdfUserId(ShiroKit.getUser().getId());
        entity.setMdfUserName(ShiroKit.getUser().getName());
        entity.setMdfTime(new Date());
        entity.setCrtTime(new Date());
        templateDao.insert(entity);
        fileModel.setTemplateId(entity.getId());
        fileModel.setCrtTime(new Date());
        fileModel.setMdfTime(new Date());
        return templateFileDao.insert(fileModel);
    }
    @Override
    public Integer insertTemplate(TemplateModel entity, TemplateFileModel fileModel) {
        // fileModel.setFile(Base64.decodeStr(fileModel.getFile()));
        return insert(entity, fileModel);
    }

    @Override
    public Integer deleteById(Integer id) {
        templateFileDao.deleteByTemplateId(id);
        return templateDao.deleteById(id);
    }

    @Override
    public Integer updateById(TemplateModel entity, TemplateFileModel fileModel) {
        fileModel.setFile(hanlderFileEncode(fileModel.getFile()));
        fileModel.setTemplateId(entity.getId());
        fileModel.setMdfTime(new Date());
        templateFileDao.updateById(fileModel);
        entity.setMdfUserName(ShiroKit.getUser().getName());
        entity.setMdfUserId(ShiroKit.getUser().getId());
        entity.setMdfTime(new Date());
        return templateDao.updateById(entity);
    }
    @Override
    public Integer updateTemplate(TemplateModel entity, TemplateFileModel fileModel) {
        // fileModel.setFile(Base64.decodeStr(fileModel.getFile()));
        return updateById(entity, fileModel);
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

    @Override
    public List<GenTemplatePageResVO> page(GenTemplatePageReqVO template) {
        List<GenTemplatePageResVO> page = templateDao.page(template);
        page.forEach(temp -> {
            User crtUser = userMapper.selectById(temp.getCrtUserId());
            User mdfUser = userMapper.selectById(temp.getMdfUserId());
            temp.setCrtUserName(crtUser.getName());
            temp.setMdfUserName(mdfUser.getName());
        });
        return page;
    }

    private String hanlderFileEncode(String file) {
        return file.replaceAll("& #40;","(")
            .replaceAll("& #41;",")")
            .replaceAll("& lt;","<")
            .replaceAll("& gt;",">")
            .replaceAll("& #39;","'");
    }

}
