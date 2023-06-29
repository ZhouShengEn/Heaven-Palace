package com.heaven.palace.moonpalace.modular.system.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.heaven.palace.moonpalace.modular.code.model.DbInfoModel;
import com.heaven.palace.moonpalace.modular.system.vo.DbInfoPageReqVO;
import com.heaven.palace.moonpalace.modular.system.vo.DbinfoPageResVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DbInfoDao
 *
 * @author ZhouShengEn
 * @Date 2022-8-25
 */
@Repository
public interface DbInfoDao extends BaseMapper<DbInfoModel> {

    /**
     * 统计数量
     */
    Integer selectCount(@Param("e") DbInfoModel model);

    /**
     * 查询列表
     */
    List<DbInfoModel> selectList(@Param("e") DbInfoModel model);

    /**
     * 分页查询信息
     */
    List<DbInfoModel> selectPage(@Param("p") Page pagination, @Param("e") DbInfoModel model, @Param("w") Wrapper<DbInfoModel> wrapper);

    List<DbinfoPageResVO> page(DbInfoPageReqVO dbInfoPageReqVO);

}
