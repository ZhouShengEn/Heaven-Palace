package com.heaven.palace.purplecloudpalace.modular.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.heaven.palace.purplecloudpalace.common.constant.factory.MutiStrFactory;
import com.heaven.palace.purplecloudpalace.common.exception.BizExceptionEnum;
import com.heaven.palace.purplecloudpalace.common.exception.BussinessException;
import com.heaven.palace.purplecloudpalace.common.persistence.dao.DictMapper;
import com.heaven.palace.purplecloudpalace.common.persistence.model.Dict;
import com.heaven.palace.purplecloudpalace.modular.system.dao.DictDao;
import com.heaven.palace.purplecloudpalace.modular.system.service.IDictService;

@Service
@Transactional
public class DictServiceImpl implements IDictService {

    @Resource
    DictDao dictDao;

    @Resource
    DictMapper dictMapper;

    @Override
    public void addDict(String dictName, String dictValues) {
        //判断有没有该字典
        List<Dict> dicts = dictMapper.selectList(new QueryWrapper<Dict>().eq("name", dictName).eq("pid", 0));
        if (dicts != null && dicts.size() > 0) {
            throw new BussinessException(BizExceptionEnum.DICT_EXISTED);
        }

        //解析dictValues
        List<Map<String, String>> items = MutiStrFactory.parseKeyValue(dictValues);

        //添加字典
        Dict dict = new Dict();
        dict.setName(dictName);
        dict.setNum(0);
        dict.setPid(0);
        this.dictMapper.insert(dict);

        //添加字典条目
        for (Map<String, String> item : items) {
            String num = item.get(MutiStrFactory.MUTI_STR_KEY);
            String name = item.get(MutiStrFactory.MUTI_STR_VALUE);
            Dict itemDict = new Dict();
            itemDict.setPid(dict.getId());
            itemDict.setName(name);
            try {
                itemDict.setNum(Integer.valueOf(num));
            } catch (NumberFormatException e) {
                throw new BussinessException(BizExceptionEnum.DICT_MUST_BE_NUMBER);
            }
            this.dictMapper.insert(itemDict);
        }
    }

    @Override
    public void editDict(Integer dictId, String dictName, String dicts) {
        //删除之前的字典
        this.delteDict(dictId);

        //重新添加新的字典
        this.addDict(dictName, dicts);
    }

    @Override
    public void delteDict(Integer dictId) {
        //删除这个字典的子词典
        QueryWrapper<Dict> dictQueryWrapper = new QueryWrapper<Dict>();
        dictQueryWrapper = dictQueryWrapper.eq("pid", dictId);
        dictMapper.delete(dictQueryWrapper);

        //删除这个词典
        dictMapper.deleteById(dictId);
    }
}
