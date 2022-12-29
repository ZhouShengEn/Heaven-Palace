package com.heaven.palace.purplecloudpalace.business.service;


import com.heaven.palace.purplecloudpalace.node.ZTreeNode;
import com.heaven.palace.purplecloudpalace.business.entity.BaseDictionaryItem;
import com.heaven.palace.purplecloudpalace.business.entity.BaseDictionaryType;
import com.heaven.palace.purplecloudpalace.business.vo.BaseDictionaryItemVo;
import com.heaven.palace.purplecloudpalace.business.vo.BaseDictionaryTypeVo;

import java.util.List;

/**
 * 字典表
 *
 * @author zjc
 * @version 2021-04-14 20:03:59
 * @email 471511042@qq.com
 */
public interface BaseDictionaryService {

    List<BaseDictionaryItemVo> queryItems(BaseDictionaryItem baseDictionaryItem);

    void insertDictItem(BaseDictionaryItemVo baseDictionaryItemVo);

    List<BaseDictionaryTypeVo> queryTypes(BaseDictionaryType baseDictionaryType);

    void deleteDictType(Integer dictTypeId);

    void deleteItemDict(Integer dictId);

    void insertDictType(BaseDictionaryType baseDictionaryType);

    BaseDictionaryType selectDictTypeById(Integer dictTypeId);

    BaseDictionaryItem selectDictItemById(Integer dictId);

    void dictTypeUpdateBySelective(BaseDictionaryType dictTypeId);

    void dictItemUpdateBySelective(BaseDictionaryItem baseDictionaryItem);

    List<ZTreeNode> queryTypeTree();
}