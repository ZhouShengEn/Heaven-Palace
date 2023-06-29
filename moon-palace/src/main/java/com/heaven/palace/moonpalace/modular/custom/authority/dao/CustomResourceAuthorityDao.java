package com.heaven.palace.moonpalace.modular.custom.authority.dao;


import com.heaven.palace.moonpalace.common.IMapper;
import com.heaven.palace.moonpalace.modular.custom.authority.entity.CustomResourceAuthority;
import com.heaven.palace.moonpalace.modular.custom.authority.vo.AuthorityPageReqVO;
import com.heaven.palace.moonpalace.modular.custom.ugroup.vo.CustomUserGroupSwitchVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * CustomResourceAuthorityMapper
 *
 * @author ZhouShengEn
 * @date 2023-02-03 17:35
 */
@Repository
public interface CustomResourceAuthorityDao extends IMapper<CustomResourceAuthority> {

    List<CustomResourceAuthority> page(AuthorityPageReqVO pageReqVO);

    List<CustomUserGroupSwitchVO> queryAuthoritySwitch(Integer resourceId, String resourceType, List<String> groupTypeCodes);

    List<CustomResourceAuthority> selectByCondition(CustomResourceAuthority CustomResourceAuthority);

}
