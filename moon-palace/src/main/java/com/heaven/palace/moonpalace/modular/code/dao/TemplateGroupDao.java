package com.heaven.palace.moonpalace.modular.code.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.heaven.palace.moonpalace.modular.code.model.TemplateGroupModel;
import com.heaven.palace.moonpalace.modular.code.vo.GenTemplateGroupPageReqVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TemplateGroupDao
 *
 * @author ZhouShengEn
 * @Date 2022-8-25
 */
@Repository
public interface TemplateGroupDao extends BaseMapper<TemplateGroupModel>{

    /**
     * 统计数量
     * @param model
     * @return
     */
    Integer selectCount(@Param("e") TemplateGroupModel model);

    /**
     * 查询列表
     * @param model
     * @return
     */
    List<TemplateGroupModel> selectList(@Param("e") TemplateGroupModel model);

    /**
     * 分页查询信息
     * @param pagination
     * @param model
     * @param wrapper
     * @return
     */
    List<TemplateGroupModel> selectPage(@Param("p") Page pagination, @Param("e") TemplateGroupModel model, @Param("w") Wrapper<TemplateGroupModel> wrapper);

    List<TemplateGroupModel> page(GenTemplateGroupPageReqVO reqVO);
}
