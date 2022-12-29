package com.heaven.palace.purplecloudpalace.business.service.impl;


import com.heaven.palace.purplecloudpalace.constant.RedisCacheKeyConst;
import com.heaven.palace.purplecloudpalace.core.shiro.ShiroKit;
import com.heaven.palace.purplecloudpalace.core.shiro.ShiroUser;
import com.heaven.palace.purplecloudpalace.mutidatasource.annotion.DataSource;
import com.heaven.palace.purplecloudpalace.node.ZTreeNode;
import com.heaven.palace.purplecloudpalace.business.dao.BaseDictionaryItemDao;
import com.heaven.palace.purplecloudpalace.business.dao.BaseDictionaryTypeDao;
import com.heaven.palace.purplecloudpalace.business.entity.BaseDictionaryItem;
import com.heaven.palace.purplecloudpalace.business.entity.BaseDictionaryType;
import com.heaven.palace.purplecloudpalace.business.service.BaseDictionaryService;
import com.heaven.palace.purplecloudpalace.business.vo.BaseDictionaryItemVo;
import com.heaven.palace.purplecloudpalace.business.vo.BaseDictionaryTypeVo;
import com.heaven.palace.purplecloudpalace.util.MappingUtils;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 字典表
 *
 * @author zjc
 * @version 2021-04-14 20:03:59
 * @email 471511042@qq.com
 */
@Service
public class BaseDictionaryServiceImpl implements BaseDictionaryService {


    @Resource
    private BaseDictionaryItemDao baseDictionaryItemDao;
    @Resource
    private BaseDictionaryTypeDao baseDictionaryTypeDao;
    @Autowired
    private RedissonClient redissonClient;


    @Override
    @DataSource(businessCacheKey = RedisCacheKeyConst.BUSINESS_DICT_ITEM_ENV_CACHE_KEY_PREFIX)
    public List<BaseDictionaryItemVo> queryItems(BaseDictionaryItem baseDictionaryItem) {
        return baseDictionaryItemDao.queryItems(baseDictionaryItem);
    }

    @Override
    @DataSource(businessCacheKey = RedisCacheKeyConst.BUSINESS_DICT_ITEM_ENV_CACHE_KEY_PREFIX)
    public void insertDictItem(BaseDictionaryItemVo baseDictionaryItemVo) {
        BaseDictionaryItem baseDictionaryItem = MappingUtils.beanConvert(baseDictionaryItemVo, BaseDictionaryItem.class);
        assembleDictItemBaseUser(baseDictionaryItem);
        baseDictionaryItemDao.insert(baseDictionaryItem);
        baseDictionaryItemVo.setId(baseDictionaryItem.getId());
    }

    @Override
    @DataSource(businessCacheKey = RedisCacheKeyConst.BUSINESS_DICT_TYPE_ENV_CACHE_KEY_PREFIX)
    public List<BaseDictionaryTypeVo> queryTypes(BaseDictionaryType baseDictionaryType) {

        return MappingUtils.beanListConvert(baseDictionaryTypeDao.queryWithEqualConditions(baseDictionaryType), BaseDictionaryTypeVo.class);
    }

    @Override
    @DataSource(businessCacheKey = RedisCacheKeyConst.BUSINESS_DICT_TYPE_ENV_CACHE_KEY_PREFIX)
    public void deleteDictType(Integer dictTypeId) {
        baseDictionaryTypeDao.deleteById(dictTypeId);
    }

    @Override
    @DataSource(businessCacheKey = RedisCacheKeyConst.BUSINESS_DICT_ITEM_ENV_CACHE_KEY_PREFIX)
    public void deleteItemDict(Integer dictId) {
        baseDictionaryItemDao.deleteById(dictId);
    }

    @Override
    @DataSource(businessCacheKey = RedisCacheKeyConst.BUSINESS_DICT_TYPE_ENV_CACHE_KEY_PREFIX)
    public void insertDictType(BaseDictionaryType baseDictionaryType) {
        assembleDictTypeBaseUser(baseDictionaryType);
        baseDictionaryTypeDao.insert(baseDictionaryType);
    }

    @Override
    @DataSource(businessCacheKey = RedisCacheKeyConst.BUSINESS_DICT_TYPE_ENV_CACHE_KEY_PREFIX)
    public BaseDictionaryType selectDictTypeById(Integer dictTypeId) {
        return baseDictionaryTypeDao.selectById(dictTypeId);
    }

    @Override
    @DataSource(businessCacheKey = RedisCacheKeyConst.BUSINESS_DICT_ITEM_ENV_CACHE_KEY_PREFIX)
    public BaseDictionaryItem selectDictItemById(Integer dictId) {
        return baseDictionaryItemDao.selectById(dictId);
    }

    @Override
    @DataSource(businessCacheKey = RedisCacheKeyConst.BUSINESS_DICT_TYPE_ENV_CACHE_KEY_PREFIX)
    public void dictTypeUpdateBySelective(BaseDictionaryType baseDictionaryType) {
        assembleDictTypeBaseUser(baseDictionaryType);

        baseDictionaryTypeDao.updateById(baseDictionaryType);
    }

    private void assembleDictTypeBaseUser(BaseDictionaryType baseDictionaryType) {
        ShiroUser user = ShiroKit.getUser();
        if (null != user){
            baseDictionaryType.setUpdateBy(user.getAccount());
        }
        baseDictionaryType.setUpdateDate(new Date());
    }
    
    private void assembleDictItemBaseUser(BaseDictionaryItem baseDictionaryItem) {
        ShiroUser user = ShiroKit.getUser();
        if (null != user){
            baseDictionaryItem.setCreateBy(user.getAccount());
            baseDictionaryItem.setUpdateBy(user.getAccount());
        }
        baseDictionaryItem.setUpdateDate(new Date());
        baseDictionaryItem.setCreateTime(new Date());
    }

    @Override
    @DataSource(businessCacheKey = RedisCacheKeyConst.BUSINESS_DICT_ITEM_ENV_CACHE_KEY_PREFIX)
    public void dictItemUpdateBySelective(BaseDictionaryItem baseDictionaryItem) {
        assembleDictItemBaseUser(baseDictionaryItem);
        baseDictionaryItemDao.updateById(baseDictionaryItem);
    }

    @Override
    @DataSource(businessCacheKey = RedisCacheKeyConst.BUSINESS_DICT_TYPE_ENV_CACHE_KEY_PREFIX)
    public List<ZTreeNode> queryTypeTree() {
        return baseDictionaryTypeDao.queryTypeTree();
    }
}