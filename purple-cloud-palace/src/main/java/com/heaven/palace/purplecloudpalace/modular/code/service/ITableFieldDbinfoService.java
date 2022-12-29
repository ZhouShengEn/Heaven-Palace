package com.heaven.palace.purplecloudpalace.modular.code.service;

import com.heaven.palace.purplecloudpalace.modular.code.model.TableFieldDbinfoModel;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;


/**
 * Service
 *
 * @author ZhouShengEn
 * @Date 2022-8-25
 */
public interface ITableFieldDbinfoService {

     /**
     * <p>
     * 插入一条记录
     * </p>
     *
     * @param entity 实体对象
     * @return int
     */
    Integer insert(TableFieldDbinfoModel entity);

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
    Integer updateById(TableFieldDbinfoModel entity);

    /**
     * <p>
     * 根据 ID 查询
     * </p>
     *
     * @param id 主键ID
     * @return TableFieldDbinfoModel
     */
    TableFieldDbinfoModel selectById(Integer id);

    /**
     * <p>
     * 根据 entity 条件，查询一条记录
     * </p>
     *
     * @param entity 实体对象
     * @return TableFieldDbinfoModel
     */
    TableFieldDbinfoModel selectOne(TableFieldDbinfoModel entity);

    /**
     * <p>
     * 根据 model 条件，查询总记录数
     * </p>
     *
     * @param model 实体对象
     * @return int
     */
    Integer selectCount(TableFieldDbinfoModel model);

    /**
     * <p>
     * 根据 entity 条件，查询全部记录
     * </p>
     *
     * @param model 实体对象封装操作类（可以为 null）
     * @return List<TableFieldDbinfoModel>
     */
    List<TableFieldDbinfoModel> selectList(TableFieldDbinfoModel model);


    /**
     * <p>
     * 根据 entity 条件，查询全部记录（并翻页）
     * </p>
     *
     * @param pagination 分页查询条件
     * @param model   实体对象封装操作可以为 null）
     * @param wrapper   SQL包装
     * @return List<TableFieldDbinfoModel>
     */
    List<TableFieldDbinfoModel> selectPage(Page pagination, TableFieldDbinfoModel model, Wrapper<TableFieldDbinfoModel> wrapper);

    /**
     * 批量删除,根据字段的数据
     * @param fieldIds
     */
    Integer deleteByFieldIds(List<Integer> fieldIds);

    /**
     * 批量插入
     * @param dbInfoModelList
     * @return
     */
    Integer batchInsert(List<TableFieldDbinfoModel> dbInfoModelList);
}
