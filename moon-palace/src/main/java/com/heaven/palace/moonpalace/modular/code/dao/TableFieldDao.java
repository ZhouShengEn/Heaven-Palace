package com.heaven.palace.moonpalace.modular.code.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.heaven.palace.moonpalace.modular.code.model.TableFieldModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TableFieldDao
 *
 * @author ZhouShengEn
 * @Date 2022-8-25
 */
@Repository
public interface TableFieldDao extends BaseMapper<TableFieldModel>{

    /**
     * 统计数量
     * @param model
     * @return
     */
    Integer selectCount(@Param("e")TableFieldModel model);

    /**
     * 查询列表
     * @param model
     * @return
     */
    List<TableFieldModel> selectList(@Param("e")TableFieldModel model);

    /**
     * 分页查询信息
     * @param pagination
     * @param model
     * @param wrapper
     * @return
     */
    List<TableFieldModel> selectPage(@Param("p") Page pagination, @Param("e") TableFieldModel model, @Param("w") Wrapper<TableFieldModel> wrapper);

    /**
     * 批量插入列信息
     * @param tableFields
     * @return
     */
    Integer batchInsert(List<TableFieldModel> tableFields);
}
