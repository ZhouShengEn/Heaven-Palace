package com.heaven.palace.moonpalace.modular.custom.element.service.impl;

import com.github.pagehelper.PageHelper;
import com.heaven.palace.moonpalace.base.response.ObjectRestResponse;
import com.heaven.palace.moonpalace.base.response.PageResultResponse;
import com.heaven.palace.moonpalace.component.distributedlock.annotion.GlobalLock;
import com.heaven.palace.moonpalace.constant.BusinessLogConst;
import com.heaven.palace.moonpalace.core.businesslog.context.BusinessLogTempObject;
import com.heaven.palace.moonpalace.core.businesslog.operation.BusinessLogOperationFactory;
import com.heaven.palace.moonpalace.core.shiro.ShiroKit;
import com.heaven.palace.moonpalace.modular.custom.authority.dao.CustomResourceAuthorityDao;
import com.heaven.palace.moonpalace.modular.custom.element.dao.CustomElementDAO;
import com.heaven.palace.moonpalace.modular.custom.element.entity.CustomElement;
import com.heaven.palace.moonpalace.modular.custom.element.service.CustomElementService;
import com.heaven.palace.moonpalace.modular.custom.element.vo.CustomElementPageReqVO;
import com.heaven.palace.moonpalace.modular.custom.global.constant.BusinessCacheEnum;
import com.heaven.palace.moonpalace.mutidatasource.annotion.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author zhoushengen
 * @version 1.0
 * @date 2023/2/27 10:24
 */
@Slf4j
@Service
public class CustomElementServiceImpl implements CustomElementService {

    @Resource
    private CustomElementDAO customElementDAO;

    @Resource
    private CustomResourceAuthorityDao resourceAuthorityDao;

    @Resource
    private RedissonClient redissonClient;


    @Override
    @DataSource(businessCacheKey = BusinessCacheEnum.CUSTOM_ELEMENT, logSqlType = BusinessLogConst.SqlTypeEnum.INSERT)
    @GlobalLock(businessCacheKey = BusinessCacheEnum.CUSTOM_ELEMENT)
    public CustomElement add(CustomElement customElement) {
        customElement.setCreateTime(new Date());
        customElement.setCreateBy(ShiroKit.getUser().getAccount());
        customElementDAO.insert(customElement);
        BusinessLogOperationFactory.BUSINESS_LOG_TEMP_OBJECT
                .set(BusinessLogTempObject.builder().targetId(customElement.getId()).targetName(customElement.getName()).build());
        return customElement;
    }

    @Override
    @DataSource(businessCacheKey = BusinessCacheEnum.CUSTOM_ELEMENT, logSqlType = BusinessLogConst.SqlTypeEnum.UPDATE)
    @GlobalLock(businessCacheKey = BusinessCacheEnum.CUSTOM_ELEMENT)
    public ObjectRestResponse<Void> update(CustomElement customElement) {
        CustomElement oldObj = customElementDAO.selectById(customElement.getId());
        BusinessLogOperationFactory.BUSINESS_LOG_TEMP_OBJECT.set(BusinessLogTempObject.builder()
                .targetId(customElement.getId()).targetName(customElement.getName()).oldObject(oldObj).newObj(customElement).build());
        customElementDAO.updateById(customElement);
        return new ObjectRestResponse<>().message("修改成功！");
    }

    @Override
    @DataSource(businessCacheKey = BusinessCacheEnum.CUSTOM_ELEMENT, logSqlType = BusinessLogConst.SqlTypeEnum.DELETE)
    @GlobalLock(businessCacheKey = BusinessCacheEnum.CUSTOM_ELEMENT)
    public void delete(Integer id) {
        CustomElement customElement = customElementDAO.selectById(id);
        customElementDAO.deleteById(id);
        BusinessLogOperationFactory.BUSINESS_LOG_TEMP_OBJECT.set(
                BusinessLogTempObject.builder().targetId(id).targetName(customElement.getName()).oldObject(customElement).build());
    }

    @Override
    @DataSource(businessCacheKey = BusinessCacheEnum.CUSTOM_ELEMENT)
    public PageResultResponse<CustomElement> page(CustomElementPageReqVO pageReqVO) {
        com.github.pagehelper.Page<Object> result =
                PageHelper.startPage(pageReqVO.getPage(), pageReqVO.getLimit(), "id desc");
        List<CustomElement> page = customElementDAO.page(pageReqVO);
        return new PageResultResponse<>(result.getTotal(), page);

    }

    @DataSource(businessCacheKey = BusinessCacheEnum.CUSTOM_ELEMENT)
    @Override
    public CustomElement selectById(Integer elementId) {
        return customElementDAO.selectById(elementId);
    }
}
