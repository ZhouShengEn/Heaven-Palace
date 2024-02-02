package com.heaven.palace.jasperpalace.base.tree;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.tree.Node;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Author: zhoushengen
 * @Description: 可序列化树形结构
 * @DateTime: 2024/1/31 9:36
 **/
public class SerialTreeNode<T> extends LinkedHashMap<String, Object> implements Node<T>, Serializable {
    private static final long serialVersionUID = 4741282576739570243L;

    private static final String NAME_KEY = "name";
    private static final String ID_KEY = "id";
    private static final String PARENT_ID_KEY = "parentId";
    private static final String WEIGHT_KEY = "weight";
    private static final String CHILDREN_KEY = "children";

    private SerialTreeNode<T> parent;



    public SerialTreeNode<T> getParent() {
        return parent;
    }

    /**
     * 获取所有父节点名称列表
     *
     * <p>
     * 比如有个人在研发1部，他上面有研发部，接着上面有技术中心<br>
     * 返回结果就是：[研发一部, 研发中心, 技术中心]
     *
     * @param id                 节点ID
     * @param includeCurrentNode 是否包含当前节点的名称
     * @return 所有父节点名称列表
     * @since 5.2.4
     */
    public List<String> getParentsName(T id, boolean includeCurrentNode) {
        return TreeUtil.getParentsName(getNode(id), includeCurrentNode);
    }

    /**
     * 获取所有父节点名称列表
     *
     * <p>
     * 比如有个人在研发1部，他上面有研发部，接着上面有技术中心<br>
     * 返回结果就是：[研发一部, 研发中心, 技术中心]
     *
     * @param includeCurrentNode 是否包含当前节点的名称
     * @return 所有父节点名称列表
     * @since 5.2.4
     */
    public List<String> getParentsName(boolean includeCurrentNode) {
        return TreeUtil.getParentsName(this, includeCurrentNode);
    }

    /**
     * 设置父节点
     *
     * @param parent 父节点
     * @return this
     * @since 5.2.4
     */
    public SerialTreeNode<T> setParent(SerialTreeNode<T> parent) {
        this.parent = parent;
        if (null != parent) {
            this.setParentId(parent.getId());
        }
        return this;
    }

    @Override
    public T getId() {
        return (T) this.get(ID_KEY);
    }

    @Override
    public Node<T> setId(T id) {
        this.put(ID_KEY, id);
        return this;
    }

    @Override
    public T getParentId() {
        return (T) this.get(PARENT_ID_KEY);
    }

    @Override
    public Node<T> setParentId(T parentId) {
        this.put(PARENT_ID_KEY, parentId);
        return this;
    }

    @Override
    public CharSequence getName() {
        return (CharSequence) this.get(NAME_KEY);
    }

    @Override
    public Node<T> setName(CharSequence name) {
        this.put(NAME_KEY, name);
        return this;
    }

    @Override
    public Comparable<?> getWeight() {
        return (Comparable<?>) this.get(WEIGHT_KEY);
    }

    @Override
    public Node<T> setWeight(Comparable<?> weight) {
        this.put(WEIGHT_KEY, weight);
        return this;
    }

    public List<SerialTreeNode<T>> getChildren() {
        return (List<SerialTreeNode<T>>) this.get(CHILDREN_KEY);
    }

    public void setChildren(List<SerialTreeNode<T>> children) {
        this.put(CHILDREN_KEY, children);
    }

    /**
     * 扩展属性
     *
     * @param key   键
     * @param value 扩展值
     */
    public void putExtra(String key, Object value) {
        Assert.notEmpty(key, "Key must be not empty !");
        this.put(key, value);
    }

    /**
     * 获取ID对应的节点，如果有多个ID相同的节点，只返回第一个。<br>
     * 此方法只查找此节点及子节点，采用广度优先遍历。
     *
     * @param id ID
     * @return 节点
     * @since 5.2.4
     */
    public SerialTreeNode<T> getNode(T id) {
        return TreeUtil.getNode(this, id);
    }
}
