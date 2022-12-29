package com.heaven.palace.purplecloudpalace.business.dao;


import com.heaven.palace.purplecloudpalace.common.IMapper;
import com.heaven.palace.purplecloudpalace.business.entity.BaseDictionaryItem;
import com.heaven.palace.purplecloudpalace.business.vo.BaseDictionaryItemVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseDictionaryItemDao extends IMapper<BaseDictionaryItem> {


    /**
     * 查询所有字典
     * @return
     */
    List<BaseDictionaryItemVo> queryItems(BaseDictionaryItem baseDictionaryItem);


}
