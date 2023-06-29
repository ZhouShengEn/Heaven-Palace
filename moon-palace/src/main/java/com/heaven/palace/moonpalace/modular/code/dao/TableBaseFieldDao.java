package com.heaven.palace.moonpalace.modular.code.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.heaven.palace.moonpalace.modular.code.model.TableBaseFieldModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TableBaseFieldDao
 *
 * @author ZhouShengEn
 * @Date 2022-8-25
 */
@Repository
public interface TableBaseFieldDao extends BaseMapper<TableBaseFieldModel>{

    /**
     * 统计数量
     * @param model
     * @return
     */
    Integer selectCount(@Param("e")TableBaseFieldModel model);

    /**
     * 查询列表
     * @param model
     * @return
     */
    List<TableBaseFieldModel> selectList(@Param("e")TableBaseFieldModel model);

    /**
     * 分页查询信息
     * @param pagination
     * @param model
     * @param wrapper
     * @return
     */
    List<TableBaseFieldModel> selectPage(@Param("p") Page pagination, @Param("e") TableBaseFieldModel model, @Param("w") Wrapper<TableBaseFieldModel> wrapper);

}
