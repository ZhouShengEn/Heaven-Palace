package com.heaven.palace.purplecloudpalace.business.dao;



import com.heaven.palace.purplecloudpalace.common.IMapper;
import com.heaven.palace.purplecloudpalace.node.ZTreeNode;
import com.heaven.palace.purplecloudpalace.business.entity.BaseDictionaryType;
import com.heaven.palace.purplecloudpalace.business.vo.BaseDictionaryTypeVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * BaseDictionaryTypeDao
 *
 * @author ZhouShengEn
 * @Date 2022-09-02 17:02
 */
@Repository
public interface BaseDictionaryTypeDao extends IMapper<BaseDictionaryType> {

    /**
     * 查询所有字典类型
     * @return
     */
    List<BaseDictionaryTypeVo> queryTypes();

    List<ZTreeNode> queryTypeTree();
    
}
