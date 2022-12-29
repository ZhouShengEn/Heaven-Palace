package com.heaven.palace.purplecloudpalace.modular.code.service;

import com.heaven.palace.purplecloudpalace.modular.code.model.GenParamModel;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;


/**
 * 生成参数Service
 *
 * @author ZhouShengEn
 * @Date 2022-8-25
 */
public interface IGenParamService {

     /**
     * <p>
     * 插入一条记录
     * </p>
     *
     * @param entity 实体对象
     * @return int
     */
    Integer insert(GenParamModel entity);

    /**
     * <p>
     * 根据 ID 删除
     * </p>
     *
     * @param id 主键ID
     * @return int
     */
    Integer deleteById(Integer id);

    /**
     * <p>
     * 根据 ID 修改
     * </p>
     *
     * @param entity 实体对象
     * @return int
     */
    Integer updateById(GenParamModel entity);

    /**
     * <p>
     * 根据 ID 查询
     * </p>
     *
     * @param id 主键ID
     * @return GenParamModel
     */
    GenParamModel selectById(Integer id);

    /**
     * <p>
     * 根据 entity 条件，查询一条记录
     * </p>
     *
     * @param entity 实体对象
     * @return GenParamModel
     */
    GenParamModel selectOne(GenParamModel entity);

    /**
     * <p>
     * 根据 model 条件，查询总记录数
     * </p>
     *
     * @param model 实体对象
     * @return int
     */
    Integer selectCount(GenParamModel model);

    /**
     * <p>
     * 根据 entity 条件，查询全部记录
     * </p>
     *
     * @param model 实体对象封装操作类（可以为 null）
     * @return List<GenParamModel>
     */
    List<GenParamModel> selectList(GenParamModel model);


    /**
     * <p>
     * 根据 entity 条件，查询全部记录（并翻页）
     * </p>
     *
     * @param pagination 分页查询条件
     * @param model   实体对象封装操作��以为 null）
     * @param wrapper   SQL包装
     * @return List<GenParamModel>
     */
    List<GenParamModel> selectPage(Page pagination, GenParamModel model, Wrapper<GenParamModel> wrapper);

}
