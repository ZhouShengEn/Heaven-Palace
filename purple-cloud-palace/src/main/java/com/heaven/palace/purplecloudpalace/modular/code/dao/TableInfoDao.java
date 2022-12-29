package com.heaven.palace.purplecloudpalace.modular.code.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.heaven.palace.purplecloudpalace.modular.code.model.TableInfoModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TableInfoDao
 *
 * @author ZhouShengEn
 * @Date 2022-8-25
 */
@Repository
public interface TableInfoDao extends BaseMapper<TableInfoModel>{

    /**
     * 统计数量
     * @param model
     * @return
     */
    Integer selectCount(@Param("e")TableInfoModel model);

    /**
     * 查询列表
     * @param model
     * @return
     */
    List<TableInfoModel> selectList(@Param("e")TableInfoModel model);

    /**
     * 分页查询信息
     * @param pagination
     * @param model
     * @param wrapper
     * @return
     */
    List<TableInfoModel> selectPage(@Param("p") Page pagination, @Param("e") TableInfoModel model, @Param("w") Wrapper<TableInfoModel> wrapper);

}
