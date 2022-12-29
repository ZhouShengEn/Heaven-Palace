package com.heaven.palace.purplecloudpalace.modular.code.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.heaven.palace.purplecloudpalace.modular.code.model.TemplateShareModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TemplateShareDao
 *
 * @author ZhouShengEn
 * @Date 2022-8-25
 */
@Repository
public interface TemplateShareDao extends BaseMapper<TemplateShareModel> {

    /**
     * 统计数量
     */
    Integer selectCount(@Param("e") TemplateShareModel model);

    /**
     * 查询列表
     */
    List<TemplateShareModel> selectList(@Param("e") TemplateShareModel model);

    /**
     * 分页查询信息
     */
    List<TemplateShareModel> selectPage(@Param("p") Page pagination, @Param("e") TemplateShareModel model, @Param("w") Wrapper<TemplateShareModel> wrapper);

}
