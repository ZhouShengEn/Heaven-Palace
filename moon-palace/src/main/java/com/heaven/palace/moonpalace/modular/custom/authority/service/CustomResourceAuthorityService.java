package com.heaven.palace.moonpalace.modular.custom.authority.service;


import com.heaven.palace.moonpalace.base.response.ObjectRestResponse;
import com.heaven.palace.moonpalace.base.response.PageResultResponse;
import com.heaven.palace.moonpalace.modular.custom.authority.entity.CustomResourceAuthority;
import com.heaven.palace.moonpalace.modular.custom.authority.vo.AuthorityPageReqVO;
import com.heaven.palace.moonpalace.modular.custom.ugroup.vo.CustomUserGroupSwitchVO;

import java.util.List;

/**
 * CustomResourceAuthorityService
 *
 * @author ZhouShengEn
 * @date 2023-02-03 17:35
 */
public interface CustomResourceAuthorityService {

    /**
     * 分页查询
     */
    PageResultResponse<CustomResourceAuthority> page(AuthorityPageReqVO pageReqVO);


    /**
     * 添加权限
     */
    ObjectRestResponse<Void> add(CustomResourceAuthority CustomResourceAuthority);


    /**
     * 修改权限
     */
    ObjectRestResponse<Void> update(CustomResourceAuthority CustomResourceAuthority);


    /**
     * 批量删除权限
     */
    ObjectRestResponse<Void> batchDelete(List<Integer> ids);

    /**
     * 删除权限
     * @param CustomResourceAuthority
     */
    void deleteByCondition(CustomResourceAuthority CustomResourceAuthority);

    List<CustomResourceAuthority> selectByCondition(CustomResourceAuthority CustomResourceAuthority);

    List<CustomUserGroupSwitchVO> queryAuthoritySwitch(Integer resourceId, String resourceType, List<String> groupTypeCodes);

    void deleteById(Integer id);

}