package com.heaven.palace.moonpalace.modular.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.heaven.palace.moonpalace.base.controller.BaseController;
import com.heaven.palace.moonpalace.base.response.ObjectRestResponse;
import com.heaven.palace.moonpalace.core.shiro.ShiroKit;
import com.heaven.palace.moonpalace.modular.code.model.CustomModuleModel;
import com.heaven.palace.moonpalace.modular.code.model.DbInfoModel;
import com.heaven.palace.moonpalace.modular.custom.global.constant.BusinessCacheEnum;
import com.heaven.palace.moonpalace.modular.system.dao.CustomModuleDao;
import com.heaven.palace.moonpalace.modular.system.dao.DbInfoDao;
import com.heaven.palace.moonpalace.modular.system.vo.QueryEnvResVO;
import com.heaven.palace.moonpalace.util.MappingUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author :zhoushengen
 * @date : 2022/9/8
 */
@RequestMapping("/env")
@RestController
@Slf4j
@Api(tags = "环境管理")
public class EnvController extends BaseController {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private DbInfoDao dbInfoDao;

    @Autowired
    private CustomModuleDao customModuleDao;

    /**
     * 切换环境
     * @param env
     * @param cacheParam
     * @return
     */
    @RequestMapping(value = "change", method = RequestMethod.GET)
    @ApiOperation(value = "切换数据源")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "env", value = "数据源别名", dataTypeClass = String.class, required = true),
            @ApiImplicitParam(paramType = "query", name = "cacheParam", value = "对应模块参数",    dataTypeClass = String.class, required = true),
    })
    public ObjectRestResponse<Void> changeEnv(@RequestParam Integer env, @RequestParam String cacheParam) {
        redissonClient.getBucket(BusinessCacheEnum.getCacheKeyPrefixByParam(cacheParam).concat(ShiroKit.getUser().getAccount())).set(env);
        return new ObjectRestResponse<>().message("切换成功！");
    }

    @RequestMapping(value = "get", method = RequestMethod.GET)
    @ApiOperation(value = "获取当前环境信息")
    public ObjectRestResponse<QueryEnvResVO> getEnv(@RequestParam String cacheParam) {
        Integer dbId = (Integer) redissonClient.getBucket(
            BusinessCacheEnum.getCacheKeyPrefixByParam(cacheParam).concat(ShiroKit.getUser().getAccount())).get();
        List<DbInfoModel> dbInfoModels = dbInfoDao.selectList(new DbInfoModel());
        if (null == dbId){
            QueryWrapper<CustomModuleModel> customModuleWrapper = new QueryWrapper<>();
            customModuleWrapper.eq("module_param", cacheParam);
            CustomModuleModel customModuleModel = customModuleDao.selectOne(customModuleWrapper);
            dbId = customModuleModel.getDefaultDbId();
        }
        DbInfoModel dbInfoModel = dbInfoDao.selectById(dbId);
        QueryEnvResVO queryEnvResVO = new QueryEnvResVO();
        queryEnvResVO.setCurrentDb(MappingUtils.beanConvert(dbInfoModel, QueryEnvResVO.SimpleDbInfo.class));
        queryEnvResVO.setDbs(MappingUtils.beanListConvert(dbInfoModels, QueryEnvResVO.SimpleDbInfo.class));
        queryEnvResVO.setDbTags(dbInfoModels.stream().map(DbInfoModel::getTag).distinct().collect(Collectors.toList()));
        return new ObjectRestResponse<>().data(queryEnvResVO);
    }


}
