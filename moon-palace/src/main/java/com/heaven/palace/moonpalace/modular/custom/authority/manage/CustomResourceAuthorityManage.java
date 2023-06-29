package com.heaven.palace.moonpalace.modular.custom.authority.manage;

import com.heaven.palace.moonpalace.base.model.BaseModel;
import com.heaven.palace.moonpalace.base.response.ObjectRestResponse;
import com.heaven.palace.moonpalace.base.service.IService;
import com.heaven.palace.moonpalace.core.businesslog.context.LogCurrentDateHolder;
import com.heaven.palace.moonpalace.core.shiro.ShiroKit;
import com.heaven.palace.moonpalace.modular.custom.authority.entity.CustomResourceAuthority;
import com.heaven.palace.moonpalace.modular.custom.authority.service.CustomResourceAuthorityService;
import com.heaven.palace.moonpalace.modular.custom.authority.vo.DataPreResVO;
import com.heaven.palace.moonpalace.modular.custom.authority.vo.ResourceAddReqVO;
import com.heaven.palace.moonpalace.modular.custom.authority.vo.ResourceDataPreVO;
import com.heaven.palace.moonpalace.modular.custom.authority.vo.ResourceUpdateReqVO;
import com.heaven.palace.moonpalace.modular.custom.global.constant.AuthorityConstant;
import com.heaven.palace.moonpalace.modular.custom.global.constant.BusinessCacheEnum;
import com.heaven.palace.moonpalace.modular.custom.ugroup.vo.CustomUserGroupSwitchVO;
import com.heaven.palace.moonpalace.mutidatasource.DataSourceDetermineContextHolder;
import com.heaven.palace.moonpalace.util.MappingUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.shiro.subject.Subject;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author zhoushengen
 * @version 1.0
 * @date 2023/2/23 14:36
 */
@Slf4j
public class CustomResourceAuthorityManage<T extends BaseModel<T>> {

    @Autowired
    private IService<T> baseService;

    @Autowired
    private CustomResourceAuthorityService resourceAuthorityService;

    @Autowired
    private RedissonClient redissonClient;

    private final ThreadPoolExecutor exec = new ThreadPoolExecutor(5, 10, 10, TimeUnit.SECONDS, new LinkedBlockingDeque<>(1), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

    /**
     * 前置数据获取
     *
     * @param dataPreVO
     * @return
     */
    public ObjectRestResponse<DataPreResVO<T>> dataPre(ResourceDataPreVO dataPreVO, String resourceType, BusinessCacheEnum businessCacheEnum) {
        Object dbId = redissonClient.getBucket(businessCacheEnum.getCacheKeyPrefix().concat(ShiroKit.getUser().getAccount())).get();
        DataSourceDetermineContextHolder.setCurrentDataSource((Integer) dbId);
        try {
            DataPreResVO<T> dataPreResVO = new DataPreResVO<>();
            Integer resourceId = dataPreVO.getResourceId();
            if (null != resourceId && resourceId > 0) {
                dataPreResVO.setResource(baseService.selectById(resourceId));
            }
            List<CustomUserGroupSwitchVO> customUserGroupSwitchVOS =
                resourceAuthorityService.queryAuthoritySwitch(resourceId,
                    resourceType, dataPreVO.getGroupTypeCodes());
            dataPreResVO.setSwitches(customUserGroupSwitchVOS.stream().collect(Collectors.groupingBy(CustomUserGroupSwitchVO::getTypeCode)));

            return new ObjectRestResponse<>().data(dataPreResVO);
        } finally {
            DataSourceDetermineContextHolder.clearDataSource();
        }
    }


    /**
     * 菜单复杂新增
     *
     * @param addReq
     * @return
     */
    public ObjectRestResponse<Void> complexAdd(ResourceAddReqVO<T> addReq, String resourceType, BusinessCacheEnum businessCacheEnum) {
        Object dbId = redissonClient.getBucket(businessCacheEnum.getCacheKeyPrefix().concat(ShiroKit.getUser().getAccount())).get();
        DataSourceDetermineContextHolder.setCurrentDataSource((Integer) dbId);
        try {
            T resource = baseService.add(addReq.getResource());
            if (CollectionUtils.isNotEmpty(addReq.getGroupIds())) {
                addNewAuthority(addReq.getGroupIds(), resource.getId(), resourceType);
            }
            return new ObjectRestResponse<>().message("新增成功！");
        } finally {
            DataSourceDetermineContextHolder.clearDataSource();
        }

    }


    /**
     * 复杂修改
     *
     * @param updateReqVO
     * @return
     */
    public ObjectRestResponse<Void> complexUpdate(ResourceUpdateReqVO<T> updateReqVO, String resourceType, BusinessCacheEnum businessCacheEnum) {
        T resource = updateReqVO.getResource();
        Object dbId = redissonClient.getBucket(businessCacheEnum.getCacheKeyPrefix().concat(ShiroKit.getUser().getAccount())).get();
        DataSourceDetermineContextHolder.setCurrentDataSource((Integer) dbId);
        try {
            baseService.update(resource);
            boolean initGroupsNotNull = CollectionUtils.isNotEmpty(updateReqVO.getInitGroupIds());
            boolean finalGroupsNotNull = CollectionUtils.isNotEmpty(updateReqVO.getFinalGroupIds());
            if (initGroupsNotNull && !finalGroupsNotNull) {
                batchDeleteAuthorityByGroup(updateReqVO.getInitGroupIds(), resourceType, resource.getId());
            } else if (!initGroupsNotNull && finalGroupsNotNull) {
                addNewAuthority(updateReqVO.getFinalGroupIds(), resource.getId(), resourceType);
            } else if (initGroupsNotNull && finalGroupsNotNull) {
                List<Integer> copyInitGroupIds = MappingUtils.beanListConvert(updateReqVO.getInitGroupIds(), Integer.class);
                List<Integer> copyFinalGroupIds = MappingUtils.beanListConvert(updateReqVO.getFinalGroupIds(), Integer.class);
                updateReqVO.getInitGroupIds().removeAll(updateReqVO.getFinalGroupIds());
                copyFinalGroupIds.removeAll(copyInitGroupIds);
                if (CollectionUtils.isNotEmpty(updateReqVO.getInitGroupIds())) {
                    batchDeleteAuthorityByGroup(updateReqVO.getInitGroupIds(), resourceType, resource.getId());
                }
                if (CollectionUtils.isNotEmpty(copyFinalGroupIds)) {
                    addNewAuthority(copyFinalGroupIds, resource.getId(), resourceType);
                }
            }
            return new ObjectRestResponse<>().message("修改成功！");
        } finally {
            DataSourceDetermineContextHolder.clearDataSource();
        }

    }

    /**
     * 复杂权限删除
     *
     * @param groupIds
     * @param resourceType
     * @param resourceId
     */
    private void batchDeleteAuthorityByGroup(List<Integer> groupIds, String resourceType, Integer resourceId) {
        // 复杂操作不允许多线程
        groupIds.forEach(id -> {
            CustomResourceAuthority CustomResourceAuthority = new CustomResourceAuthority();
            CustomResourceAuthority.setAuthorityId(String.valueOf(id));
            CustomResourceAuthority.setResourceId(String.valueOf(resourceId));
            CustomResourceAuthority.setResourceType(resourceType);
            List<CustomResourceAuthority> baseResourceAuthorities = resourceAuthorityService.selectByCondition(CustomResourceAuthority);
            resourceAuthorityService.deleteById(baseResourceAuthorities.get(0).getId());
        });
    }

    /**
     * 复杂删除
     *
     * @param ids
     * @return
     */
    @SneakyThrows
    public ObjectRestResponse<Void> complexBatchDelete(List<Integer> ids, AuthorityConstant.RESOURCE_TYPE resourceType, BusinessCacheEnum businessCacheEnum) {
        Object dbId = redissonClient.getBucket(businessCacheEnum.getCacheKeyPrefix().concat(ShiroKit.getUser().getAccount())).get();
        Subject subject = ShiroKit.getSubject();
        CompletableFuture<Void> futures = CompletableFuture.allOf(ids.stream().map(id -> CompletableFuture.runAsync(() -> {
            ShiroKit.setUser(subject);
            DataSourceDetermineContextHolder.setCurrentDataSource((Integer) dbId);
            try {
                baseService.delete(id);
                CustomResourceAuthority CustomResourceAuthority = new CustomResourceAuthority();
                CustomResourceAuthority.setResourceId(String.valueOf(id));
                CustomResourceAuthority.setResourceType(resourceType.getType());
                List<CustomResourceAuthority> baseResourceAuthorities = resourceAuthorityService.selectByCondition(CustomResourceAuthority);
                if (CollectionUtils.isNotEmpty(baseResourceAuthorities)) {
                    baseResourceAuthorities.forEach(resource -> {
                        resourceAuthorityService.deleteById(resource.getId());
                    });
                }
            } finally {
                DataSourceDetermineContextHolder.clearDataSource();
                LogCurrentDateHolder.clear();
            }
        }, exec)).toArray(CompletableFuture[]::new));
        futures.get(10, TimeUnit.SECONDS);
        return new ObjectRestResponse<>().message("删除成功！");
    }


    /**
     * 基础权限新增
     *
     * @param groupIds
     * @param resourceId
     */
    private void addNewAuthority(List<Integer> groupIds, Integer resourceId, String resourceType) {
        groupIds.forEach(groupId -> {
            CustomResourceAuthority CustomResourceAuthority = new CustomResourceAuthority();
            CustomResourceAuthority.setAuthorityId(String.valueOf(groupId));
            CustomResourceAuthority.setAuthorityType("group");
            CustomResourceAuthority.setResourceId(String.valueOf(resourceId));
            CustomResourceAuthority.setResourceType(resourceType);
            CustomResourceAuthority.setParentId("-1");
            CustomResourceAuthority.setCreateTime(new Date());
            CustomResourceAuthority.setCreateBy(ShiroKit.getUser().getAccount());
            resourceAuthorityService.add(CustomResourceAuthority);
        });
    }
}
