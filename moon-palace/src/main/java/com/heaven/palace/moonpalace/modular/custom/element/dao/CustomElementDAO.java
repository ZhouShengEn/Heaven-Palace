package com.heaven.palace.moonpalace.modular.custom.element.dao;


import com.heaven.palace.moonpalace.common.IMapper;
import com.heaven.palace.moonpalace.modular.custom.element.entity.CustomElement;
import com.heaven.palace.moonpalace.modular.custom.element.vo.CustomElementPageReqVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhoushengen
 * @version 1.0
 * @date 2023/2/27 10:25
 */
@Repository
public interface CustomElementDAO extends IMapper<CustomElement> {

    List<CustomElement> page(CustomElementPageReqVO pageReqVO);
}
