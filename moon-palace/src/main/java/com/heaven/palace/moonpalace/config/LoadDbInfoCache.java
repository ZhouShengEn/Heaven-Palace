package com.heaven.palace.moonpalace.config;

import com.alibaba.fastjson.JSON;
import com.heaven.palace.moonpalace.constant.RedisCacheKeyConst;
import com.heaven.palace.moonpalace.modular.code.model.DbInfoModel;
import com.heaven.palace.moonpalace.modular.system.dao.DbInfoDao;
import com.heaven.palace.moonpalace.modular.system.enums.DbCategoryEnum;
import org.redisson.api.RedissonClient;
import javax.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author :zhoushengen
 * @date : 2023/2/2
 */
@Configuration
public class LoadDbInfoCache implements CommandLineRunner {
    
    @Resource
    private DbInfoDao dbInfoDao;
    
    @Resource
    private RedissonClient redissonClient;
    
    @Override
    public void run(String... args) throws Exception {
        DbInfoModel dbInfoModel = new DbInfoModel();
        dbInfoModel.setCategory(DbCategoryEnum.BUSINESS_DBS.ordinal());
        List<DbInfoModel> dbInfoModels = dbInfoDao.selectList(dbInfoModel);
        if (!CollectionUtils.isEmpty(dbInfoModels)){
            // 重新加载数据库缓存
            redissonClient.getKeys().deleteByPattern(RedisCacheKeyConst.GEN_DYNAMIC_DATASOURCE_KEY_PREFIX.concat("*"));
            dbInfoModels.forEach(db -> {
                String datasourceKey = RedisCacheKeyConst.GEN_DYNAMIC_DATASOURCE_KEY_PREFIX.concat(String.valueOf(db.getId()));
                redissonClient.getBucket(datasourceKey).setAsync(JSON.toJSONString(db));
            });
        }
        
    }
}
