package com.heaven.palace.moonpalace.component.cache;

/**
 * @author :zhoushengen
 * @date : 2022/8/19
 */
public interface ICache<K, V> {

    /**
     * 获取缓存
     * @param cacheParam
     * @return
     */
    public V get(K cacheParam);

    /**
     *设置缓存
     * @param cacheParam
     * @param value
     */
    public void set(K cacheParam, V value);

    /**
     * 删除缓存
     * @param cacheParam
     */
    public void remove(K cacheParam);

}
