package com.heaven.palace.jasperpalace.base.tree;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: zhoushengen
 * @Description: 树结构工具
 * @DateTime: 2024/1/31 9:53
 **/
public class TreeUtil {

    /**
     * 递归处理
     *
     * @param treeNodes  数据集合
     * @param parentNode 当前节点
     * @param deep       已递归深度
     * @param maxDeep    最大递归深度 可能为null即不限制
     */
    private static <T> void innerBuild(List<SerialTreeNode<T>> treeNodes, SerialTreeNode<T> parentNode, int deep, Integer maxDeep) {

        if (CollUtil.isEmpty(treeNodes)) {
            return;
        }
        //maxDeep 可能为空
        if (maxDeep != null && deep >= maxDeep) {
            return;
        }

        // 每层排序 TreeNodeMap 实现了Comparable接口
        treeNodes = treeNodes.stream().sorted().collect(Collectors.toList());
        for (SerialTreeNode<T> childNode : treeNodes) {
            if (parentNode.getId().equals(childNode.getParentId())) {
                List<SerialTreeNode<T>> children = parentNode.getChildren();
                if (children == null) {
                    children = CollUtil.newArrayList();
                    parentNode.setChildren(children);
                }
                children.add(childNode);
                //				childNode.setParentId(parentNode.getId());
                childNode.setParent(parentNode);
                innerBuild(treeNodes, childNode, deep + 1, maxDeep);
            }
        }
    }


    /**
     * 获取所有父节点名称列表
     *
     * <p>
     * 比如有个人在研发1部，他上面有研发部，接着上面有技术中心<br>
     * 返回结果就是：[研发一部, 研发中心, 技术中心]
     *
     * @param <T> 节点ID类型
     * @param node 节点
     * @param includeCurrentNode 是否包含当前节点的名称
     * @return 所有父节点名称列表，node为null返回空List
     * @since 5.2.4
     */
    public static <T> List<String> getParentsName(SerialTreeNode<T> node, boolean includeCurrentNode) {
        final List<String> result = new ArrayList<>();
        if(null == node){
            return result;
        }

        if (includeCurrentNode) {
            result.add(node.getName().toString());
        }

        SerialTreeNode<T> parent = node.getParent();
        while (null != parent) {
            result.add(parent.getName().toString());
            parent = parent.getParent();
        }
        return result;
    }

    /**
     * 获取ID对应的节点，如果有多个ID相同的节点，只返回第一个。<br>
     * 此方法只查找此节点及子节点，采用广度优先遍历。
     *
     * @param <T> ID类型
     * @param node 节点
     * @param id ID
     * @return 节点
     * @since 5.2.4
     */
    public static <T> SerialTreeNode<T> getNode(SerialTreeNode<T> node, T id) {
        if (ObjectUtil.equal(id, node.getId())) {
            return node;
        }

        // 查找子节点
        SerialTreeNode<T> childNode;
        for (SerialTreeNode<T> child : node.getChildren()) {
            childNode = child.getNode(id);
            if (null != childNode) {
                return childNode;
            }
        }

        // 未找到节点
        return null;
    }
}
