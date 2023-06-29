package com.heaven.palace.moonpalace.modular.custom.authority.service.impl;

import com.github.pagehelper.PageHelper;
import com.heaven.palace.moonpalace.base.response.ObjectRestResponse;
import com.heaven.palace.moonpalace.base.response.PageResultResponse;
import com.heaven.palace.moonpalace.component.distributedlock.annotion.GlobalLock;
import com.heaven.palace.moonpalace.constant.BusinessLogConst.SqlTypeEnum;
import com.heaven.palace.moonpalace.core.businesslog.context.BusinessLogTempObject;
import com.heaven.palace.moonpalace.core.businesslog.context.LogCurrentDateHolder;
import com.heaven.palace.moonpalace.core.businesslog.operation.BusinessLogOperationFactory;
import com.heaven.palace.moonpalace.core.shiro.ShiroKit;
import com.heaven.palace.moonpalace.modular.custom.authority.dao.CustomResourceAuthorityDao;
import com.heaven.palace.moonpalace.modular.custom.authority.entity.CustomResourceAuthority;
import com.heaven.palace.moonpalace.modular.custom.authority.service.CustomResourceAuthorityService;
import com.heaven.palace.moonpalace.modular.custom.authority.vo.AuthorityPageReqVO;
import com.heaven.palace.moonpalace.modular.custom.global.constant.BusinessCacheEnum;
import com.heaven.palace.moonpalace.modular.custom.ugroup.vo.CustomUserGroupSwitchVO;
import com.heaven.palace.moonpalace.mutidatasource.annotion.DataSource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * CustomResourceAuthorityServiceImpl
 *
 * @author ZhouShengEn
 * @date 2023-02-03 17:35
 */
@Slf4j
@Service
public class CustomResourceAuthorityServiceImpl implements CustomResourceAuthorityService {

    @Autowired
    private CustomResourceAuthorityDao customResourceAuthorityDao;

    @Autowired
    private CustomResourceAuthorityServiceImpl proxy;

    private final ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 10, 20, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(1), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

    @Override
    @DataSource(businessCacheKey = BusinessCacheEnum.CUSTOM_RESOURCE_AUTHORITY)
    public PageResultResponse<CustomResourceAuthority> page(AuthorityPageReqVO pageReqVO) {
        com.github.pagehelper.Page<Object> result =
            PageHelper.startPage(pageReqVO.getPage(), pageReqVO.getLimit(), "id desc");
        List<CustomResourceAuthority> baseResourceAuthorities = customResourceAuthorityDao.page(pageReqVO);
        return new PageResultResponse<>(result.getTotal(), baseResourceAuthorities);
    }

    @Override
    @DataSource(businessCacheKey = BusinessCacheEnum.CUSTOM_RESOURCE_AUTHORITY, logSqlType = SqlTypeEnum.INSERT)
    @GlobalLock(businessCacheKey = BusinessCacheEnum.CUSTOM_RESOURCE_AUTHORITY)
    public ObjectRestResponse<Void> add(CustomResourceAuthority CustomResourceAuthority) {
        customResourceAuthorityDao.insert(CustomResourceAuthority);
        BusinessLogOperationFactory.BUSINESS_LOG_TEMP_OBJECT
            .set(BusinessLogTempObject.builder().targetId(CustomResourceAuthority.getId()).build());
        return new ObjectRestResponse<>().message("添加成功！");
    }

    @Override
    @DataSource(businessCacheKey = BusinessCacheEnum.CUSTOM_RESOURCE_AUTHORITY, logSqlType = SqlTypeEnum.UPDATE)
    @GlobalLock(businessCacheKey = BusinessCacheEnum.CUSTOM_RESOURCE_AUTHORITY)
    public ObjectRestResponse<Void> update(CustomResourceAuthority CustomResourceAuthority) {
        CustomResourceAuthority oldObj = customResourceAuthorityDao.selectById(CustomResourceAuthority.getId());
        BusinessLogOperationFactory.BUSINESS_LOG_TEMP_OBJECT.set(BusinessLogTempObject.builder()
            .targetId(CustomResourceAuthority.getId()).oldObject(oldObj).newObj(CustomResourceAuthority).build());
        customResourceAuthorityDao.updateById(CustomResourceAuthority);
        return new ObjectRestResponse<>().message("修改成功！");
    }

    @SneakyThrows
    @Override
    public ObjectRestResponse<Void> batchDelete(List<Integer> ids) {
        Subject subject = ShiroKit.getSubject();
        CompletableFuture<Void> futures = CompletableFuture.allOf(ids.stream().map(id -> CompletableFuture.runAsync(() -> {
            try {
                ShiroKit.setUser(subject);
                proxy.deleteById(id);
            } finally {
                LogCurrentDateHolder.clear();
            }
        }, executor)).toArray(CompletableFuture[]::new));
        futures.get(10, TimeUnit.SECONDS);
        return new ObjectRestResponse<>().message("删除成功！");
    }


    @SneakyThrows
    @Override
    public void deleteByCondition(CustomResourceAuthority customResourceAuthority) {
        List<CustomResourceAuthority> baseResourceAuthorities = proxy.selectByCondition(customResourceAuthority);
        if (CollectionUtils.isNotEmpty(baseResourceAuthorities)) {
            batchDelete(baseResourceAuthorities.stream().map(CustomResourceAuthority::getId).collect(Collectors.toList()));
        }

    }

    @DataSource(businessCacheKey = BusinessCacheEnum.CUSTOM_RESOURCE_AUTHORITY, logSqlType = SqlTypeEnum.DELETE)
    @Override
    @GlobalLock(businessCacheKey = BusinessCacheEnum.CUSTOM_RESOURCE_AUTHORITY)
    public void deleteById(Integer id) {
        CustomResourceAuthority CustomResourceAuthority = customResourceAuthorityDao.selectById(id);
        customResourceAuthorityDao.deleteById(id);
        BusinessLogOperationFactory.BUSINESS_LOG_TEMP_OBJECT.set(BusinessLogTempObject.builder()
                .targetId(CustomResourceAuthority.getId()).oldObject(CustomResourceAuthority).build());
    }


    @DataSource(businessCacheKey = BusinessCacheEnum.CUSTOM_RESOURCE_AUTHORITY)
    @Override
    public List<CustomResourceAuthority> selectByCondition(CustomResourceAuthority CustomResourceAuthority) {
        return customResourceAuthorityDao.selectByCondition(CustomResourceAuthority);
    }

    @DataSource(businessCacheKey = BusinessCacheEnum.CUSTOM_RESOURCE_AUTHORITY)
    @Override
    public List<CustomUserGroupSwitchVO> queryAuthoritySwitch(Integer resourceId, String resourceType, List<String> groupTypeCodes){
        return customResourceAuthorityDao.queryAuthoritySwitch(resourceId, resourceType, groupTypeCodes);
    }
}