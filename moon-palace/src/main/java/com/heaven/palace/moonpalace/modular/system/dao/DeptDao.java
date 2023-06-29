package com.heaven.palace.moonpalace.modular.system.dao;

import com.heaven.palace.moonpalace.node.ZTreeNode;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * 部门dao
 *
 * @author ZhouShengEn
 * @date 2022年8月25日
 */
public interface DeptDao {

    /**
     * 获取ztree的节点列表
     *
     * @date 2022年8月25日
     */
    List<ZTreeNode> tree();

    List<Map<String, Object>> list(@Param("condition") String condition);
}
