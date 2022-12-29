package com.heaven.palace.purplecloudpalace.modular.code.service;



import java.util.List;

import com.heaven.palace.purplecloudpalace.modular.code.model.DbInfoModel;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 数据库管理Service
 *
 * @author ZhouShengEn
 * @Date 2022-8-25
 */
public interface IDbInfoService {

    /**
     * <p> 插入一条记录 </p>
     *
     * @param entity 实体对象
     * @return int
     */
    Integer insert(DbInfoModel entity);

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
    Integer updateById(DbInfoModel entity);

    /**
     * <p> 根据 ID 查询 </p>
     *
     * @param id 主键ID
     * @return DbInfoModel
     */
    DbInfoModel selectById(Integer id);

    /**
     * <p> 根据 entity 条件，查询一条记录 </p>
     *
     * @param entity 实体对象
     * @return DbInfoModel
     */
    DbInfoModel selectOne(DbInfoModel entity);

    /**
     * <p> 根据 model 条件，查询总记录数 </p>
     *
     * @param model 实体对象
     * @return int
     */
    Integer selectCount(DbInfoModel model);

    /**
     * <p> 根据 entity 条件，查询全部记录 </p>
     *
     * @param model 实体对象封装操作类（可以为 null）
     * @return List<DbInfoModel>
     */
    List<DbInfoModel> selectList(DbInfoModel model);


    /**
     * <p> 根据 entity 条件，查询全部记录（并翻页） </p>
     *
     * @param pagination 分页查询条件
     * @param model      实体对象封装操作��以为 null）
     * @param wrapper    SQL包装
     * @return List<DbInfoModel>
     */
    List<DbInfoModel> selectPage(Page pagination, DbInfoModel model, Wrapper<DbInfoModel> wrapper);

}
