package com.heaven.palace.moonpalace.modular.code.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.heaven.palace.moonpalace.modular.code.model.TemplateModel;
import com.heaven.palace.moonpalace.modular.code.vo.GenTemplatePageReqVO;
import com.heaven.palace.moonpalace.modular.code.vo.GenTemplatePageResVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TemplateDao
 *
 * @author ZhouShengEn
 * @Date 2022-8-25
 */
@Repository
public interface TemplateDao extends BaseMapper<TemplateModel> {

    /**
     * 统计数量
     */
    Integer selectCount(@Param("e") TemplateModel model);

    /**
     * 查询列表
     */
    List<TemplateModel> selectList(@Param("e") TemplateModel model);

    /**
     * 分页查询信息
     */
    List<TemplateModel> selectPage(@Param("p") Page pagination, @Param("e") TemplateModel model, @Param("w") Wrapper<TemplateModel> wrapper);

    List<TemplateModel> getTemplateByIds(@Param("ids") String[] templates);

    List<TemplateModel> getAllTemplateByGroupId(String groupId);

    List<GenTemplatePageResVO> page(@Param("e") GenTemplatePageReqVO template);
}
