package com.heaven.palace.moonpalace.modular.custom.ugroup.service.impl;

import com.heaven.palace.moonpalace.modular.custom.ugroup.dao.CustomUserGroupDao;
import com.heaven.palace.moonpalace.modular.custom.ugroup.service.CustomUserGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhoushengen
 * @version 1.0
 * @date 2023/2/22 17:20
 */
@Service
@Slf4j
public class CustomUserGroupServiceImpl implements CustomUserGroupService {

    @Autowired
    private CustomUserGroupDao customUserGroupDao;

}
