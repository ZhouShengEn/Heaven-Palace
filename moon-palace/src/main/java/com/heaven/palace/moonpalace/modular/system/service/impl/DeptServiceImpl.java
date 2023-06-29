package com.heaven.palace.moonpalace.modular.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.heaven.palace.moonpalace.common.persistence.dao.DeptMapper;
import com.heaven.palace.moonpalace.common.persistence.model.Dept;
import com.heaven.palace.moonpalace.modular.system.service.IDeptService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class DeptServiceImpl implements IDeptService {

    @Resource
    DeptMapper deptMapper;

    @Override
    public void deleteDept(Integer deptId) {

        Dept dept = deptMapper.selectById(deptId);
        QueryWrapper<Dept> wrapper = new QueryWrapper<Dept>();
        wrapper = wrapper.like("pids", "%[" + dept.getId() + "]%");
        List<Dept> subDepts = deptMapper.selectList(wrapper);
        for (Dept temp : subDepts) {
            temp.deleteById();
        }

        dept.deleteById();
    }
}
