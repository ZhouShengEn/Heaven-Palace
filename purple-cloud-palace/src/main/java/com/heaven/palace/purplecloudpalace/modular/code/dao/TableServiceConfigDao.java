package com.heaven.palace.purplecloudpalace.modular.code.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.heaven.palace.purplecloudpalace.modular.code.model.TableServiceConfigModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TableServiceConfigDao
 *
 * @author ZhouShengEn
 * @Date 2022-8-25
 */
@Repository
public interface TableServiceConfigDao extends BaseMapper<TableServiceConfigModel>{

    /**
     * 统计数量
     * @param model
     * @return
     */
    Integer selectCount(@Param("e")TableServiceConfigModel model);

    /**
     * 查询列表
     * @param model
     * @return
     */
    List<TableServiceConfigModel> selectList(@Param("e")TableServiceConfigModel model);

    /**
     * 分页查询信息
     * @param pagination
     * @param model
     * @param wrapper
     * @return
     */
    List<TableServiceConfigModel> selectPage(@Param("p") Page pagination, @Param("e") TableServiceConfigModel model, @Param("w") Wrapper<TableServiceConfigModel> wrapper);

    /**
     * 批量插入
     * @param serviceConfig
     */
    void batchInsert(List<TableServiceConfigModel> serviceConfig);

}
