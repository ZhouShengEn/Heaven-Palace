package com.heaven.palace.purplecloudpalace.modular.code.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heaven.palace.purplecloudpalace.modular.code.model.TableFieldVerifyModel;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TableFieldVerifyDao
 *
 * @author ZhouShengEn
 * @Date 2022-8-25
 */
@Repository
public interface TableFieldVerifyDao extends BaseMapper<TableFieldVerifyModel>{

    /**
     * 统计数量
     * @param model
     * @return
     */
    Integer selectCount(@Param("e")TableFieldVerifyModel model);

    /**
     * 查询列表
     * @param model
     * @return
     */
    List<TableFieldVerifyModel> selectList(@Param("e")TableFieldVerifyModel model);

    /**
     * 分页查询信息
     * @param pagination
     * @param model
     * @param wrapper
     * @return
     */
    List<TableFieldVerifyModel> selectPage(@Param("p") Page pagination, @Param("e") TableFieldVerifyModel model, @Param("w") Wrapper<TableFieldVerifyModel> wrapper);

    /**
     * 批量删除数据
     * @param fieldIds
     * @return
     */
    Integer deleteByFieldIds(@Param("fieldIds")List<Integer> fieldIds);

    /**
     * 批量插入
     * @param verifyModelList
     * @return
     */
    Integer batchInsert(@Param("list") List<TableFieldVerifyModel> verifyModelList);
}
