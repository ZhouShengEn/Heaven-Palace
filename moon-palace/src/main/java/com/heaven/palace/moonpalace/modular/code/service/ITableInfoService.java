package com.heaven.palace.moonpalace.modular.code.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.heaven.palace.moonpalace.model.GenBeanEntity;
import com.heaven.palace.moonpalace.modular.code.model.TableInfoModel;

import java.util.List;


/**
 * Service
 *
 * @author ZhouShengEn
 * @Date 2022-8-25
 */
public interface ITableInfoService {

     /**
     * <p>
     * 插入一条记录
     * </p>
     *
     * @param entity 实体对象
     * @return int
     */
    Integer insert(TableInfoModel entity);

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
    Integer updateById(TableInfoModel entity);

    /**
     * <p>
     * 根据 ID 查询
     * </p>
     *
     * @param id 主键ID
     * @return TableInfoModel
     */
    TableInfoModel selectById(Integer id);

    /**
     * <p>
     * 根据 entity 条件，查询一条记录
     * </p>
     *
     * @param entity 实体对象
     * @return TableInfoModel
     */
    TableInfoModel selectOne(TableInfoModel entity);

    /**
     * <p>
     * 根据 model 条件，查询总记录数
     * </p>
     *
     * @param model 实体对象
     * @return int
     */
    Integer selectCount(TableInfoModel model);

    /**
     * <p>
     * 根据 entity 条件，查询全部记录
     * </p>
     *
     * @param model 实体对象封装操作类（可以为 null）
     * @return List<TableInfoModel>
     */
    List<TableInfoModel> selectList(TableInfoModel model);


    /**
     * <p>
     * 根据 entity 条件，查询全部记录（并翻页）
     * </p>
     *
     * @param pagination 分页查询条件
     * @param model   实体对象封装操作可以为 null）
     * @param wrapper   SQL包装
     * @return List<TableInfoModel>
     */
    List<TableInfoModel> selectPage(Page pagination, TableInfoModel model, Wrapper<TableInfoModel> wrapper);

    /**
     * 返回生成代码的对象
     * @param tableId
     * @return
     */
    GenBeanEntity getGenBean(Integer tableId);
}
