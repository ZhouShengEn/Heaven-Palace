package com.heaven.palace.purplecloudpalace.modular.code.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.heaven.palace.purplecloudpalace.modular.code.model.TableFieldDbinfoModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TableFieldDbinfoDao
 *
 * @author ZhouShengEn
 * @Date 2022-8-25
 */
@Repository
public interface TableFieldDbinfoDao extends BaseMapper<TableFieldDbinfoModel>{

    /**
     * 统计数量
     * @param model
     * @return
     */
    Integer selectCount(@Param("e")TableFieldDbinfoModel model);

    /**
     * 查询列表
     * @param model
     * @return
     */
    List<TableFieldDbinfoModel> selectList(@Param("e")TableFieldDbinfoModel model);

    /**
     * 分页查询信息
     * @param pagination
     * @param model
     * @param wrapper
     * @return
     */
    List<TableFieldDbinfoModel> selectPage(@Param("p") Page pagination, @Param("e") TableFieldDbinfoModel model, @Param("w") Wrapper<TableFieldDbinfoModel> wrapper);

    /**
     * 批量删除数据
     * @param fieldIds
     * @return
     */
    Integer deleteByFieldIds(@Param("fieldIds")List<Integer> fieldIds);

    /**
     * 批量插入
     * @param dbInfoModelList
     * @return
     */
    Integer batchInsert(@Param("list")List<TableFieldDbinfoModel> dbInfoModelList);
}
