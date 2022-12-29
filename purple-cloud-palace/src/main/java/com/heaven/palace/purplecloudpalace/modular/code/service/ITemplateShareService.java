package com.heaven.palace.purplecloudpalace.modular.code.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.heaven.palace.purplecloudpalace.modular.code.model.TemplateShareModel;

import java.util.List;


/**
 * 模板分享管理Service
 *
 * @author ZhouShengEn
 * @Date 2022-8-25
 */
public interface ITemplateShareService {

    /**
     * <p> 插入一条记录 </p>
     *
     * @param entity 实体对象
     * @return int
     */
    Integer insert(TemplateShareModel entity);

    /**
     * <p> 根据 ID 删除 </p>
     *
     * @param id 主键ID
     * @return int
     */
    Integer deleteById(Integer id);

    /**
     * <p> 根据 ID 修改 </p>
     *
     * @param entity 实体对象
     * @return int
     */
    Integer updateById(TemplateShareModel entity);

    /**
     * <p> 根据 ID 查询 </p>
     *
     * @param id 主键ID
     * @return TemplateShareModel
     */
    TemplateShareModel selectById(Integer id);

    /**
     * <p> 根据 entity 条件，查询一条记录 </p>
     *
     * @param entity 实体对象
     * @return TemplateShareModel
     */
    TemplateShareModel selectOne(TemplateShareModel entity);

    /**
     * <p> 根据 model 条件，查询总记录数 </p>
     *
     * @param model 实体对象
     * @return int
     */
    Integer selectCount(TemplateShareModel model);

    /**
     * <p> 根据 entity 条件，查询全部记录 </p>
     *
     * @param model 实体对象封装操作类（可以为 null）
     * @return List<TemplateShareModel>
     */
    List<TemplateShareModel> selectList(TemplateShareModel model);


    /**
     * <p> 根据 entity 条件，查询全部记录（并翻页） </p>
     *
     * @param pagination 分页查询条件
     * @param model      实体对象封装操作��以为 null）
     * @param wrapper    SQL包装
     * @return List<TemplateShareModel>
     */
    List<TemplateShareModel> selectPage(Page pagination, TemplateShareModel model, Wrapper<TemplateShareModel> wrapper);

}
