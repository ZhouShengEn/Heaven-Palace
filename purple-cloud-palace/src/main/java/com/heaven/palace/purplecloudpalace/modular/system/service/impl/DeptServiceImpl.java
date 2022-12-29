package com.heaven.palace.purplecloudpalace.modular.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.annotation.Resource;

import com.heaven.palace.purplecloudpalace.common.persistence.dao.DeptMapper;
import com.heaven.palace.purplecloudpalace.common.persistence.model.Dept;
import com.heaven.palace.purplecloudpalace.modular.system.service.IDeptService;

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
