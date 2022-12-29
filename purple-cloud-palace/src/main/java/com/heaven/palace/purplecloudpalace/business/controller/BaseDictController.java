package com.heaven.palace.purplecloudpalace.business.controller;


import com.heaven.palace.purplecloudpalace.base.controller.BaseController;
import com.heaven.palace.purplecloudpalace.business.constant.EnvEnum;
import com.heaven.palace.purplecloudpalace.business.entity.BaseDictionaryItem;
import com.heaven.palace.purplecloudpalace.business.entity.BaseDictionaryType;
import com.heaven.palace.purplecloudpalace.business.service.BaseDictionaryService;
import com.heaven.palace.purplecloudpalace.business.vo.BaseDictionaryItemVo;
import com.heaven.palace.purplecloudpalace.business.vo.BaseDictionaryTypeVo;
import com.heaven.palace.purplecloudpalace.common.constant.BusinessOperationLogConstant;
import com.heaven.palace.purplecloudpalace.common.exception.BizExceptionEnum;
import com.heaven.palace.purplecloudpalace.common.exception.BussinessException;
import com.heaven.palace.purplecloudpalace.common.persistence.model.BusinessOperationLog;
import com.heaven.palace.purplecloudpalace.constant.RedisCacheKeyConst;
import com.heaven.palace.purplecloudpalace.core.log.LogObjectHolder;
import com.heaven.palace.purplecloudpalace.core.shiro.ShiroKit;
import com.heaven.palace.purplecloudpalace.core.shiro.ShiroUser;
import com.heaven.palace.purplecloudpalace.modular.system.service.BusinessOperationLogService;
import com.heaven.palace.purplecloudpalace.node.ZTreeNode;
import com.heaven.palace.purplecloudpalace.util.MappingUtils;
import com.heaven.palace.purplecloudpalace.util.ToolUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Change;
import org.javers.core.diff.Diff;
import org.javers.core.diff.changetype.ValueChange;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

import static org.javers.core.diff.ListCompareAlgorithm.LEVENSHTEIN_DISTANCE;


/**
 * @author :zhoushengen
 * @date : 2022/9/2
 */
@Slf4j
@RequestMapping(value = "/business/dict")
@Controller
public class BaseDictController extends BaseController {

    private final static String PREFIX = "/business/dict/";

    @Resource
    BaseDictionaryService baseDictionaryService;

    @Autowired
    BusinessOperationLogService businessOperationLogService;
    
    @Autowired
    RedissonClient redissonClient;

    private Javers javers = JaversBuilder.javers().withListCompareAlgorithm(LEVENSHTEIN_DISTANCE).build();


    /**
     * 跳转到字典管理首页
     */
    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("logList", businessOperationLogService
                .queryListByType(BusinessOperationLogConstant.BUSINESS_TYPE.BUSINESS_DICT_ITEM.name(), "CREATE_TIME"
                        , (Integer) redissonClient.getBucket(RedisCacheKeyConst.BUSINESS_DICT_ITEM_ENV_CACHE_KEY_PREFIX.concat(ShiroKit.getUser().account)).get()));
        Object env = redissonClient.getBucket(RedisCacheKeyConst.BUSINESS_DICT_ITEM_ENV_CACHE_KEY_PREFIX.concat(ShiroKit.getUser().account)).get();
        model.addAttribute("env", null != env? EnvEnum.getEnvById((Integer) env).getName(): EnvEnum.DEV.getName());
        return PREFIX + "dict.html";
    }

    /**
     * 跳转到字典管理首页
     */
    @RequestMapping("/type")
    public String typeIndex(Model model) {
        model.addAttribute("logList", businessOperationLogService
                .queryListByType(BusinessOperationLogConstant.BUSINESS_TYPE.BUSINESS_DICT_TYPE.name(), "CREATE_TIME"
                        , (Integer) redissonClient.getBucket(RedisCacheKeyConst.BUSINESS_DICT_TYPE_ENV_CACHE_KEY_PREFIX.concat(ShiroKit.getUser().account)).get()));
        Object env = redissonClient.getBucket(RedisCacheKeyConst.BUSINESS_DICT_TYPE_ENV_CACHE_KEY_PREFIX.concat(ShiroKit.getUser().account)).get();
        model.addAttribute("env", null != env? EnvEnum.getEnvById((Integer) env).getName(): EnvEnum.DEV.getName());
        return PREFIX + "dict_type.html";
    }

    /**
     * 获取所有字典列表
     */
    @RequestMapping(value = "/item/list")
    @ResponseBody
    public List<BaseDictionaryItemVo> list(BaseDictionaryItemVo baseDictionaryItem) {
        return baseDictionaryService.queryItems(MappingUtils.beanConvert(baseDictionaryItem, BaseDictionaryItem.class));
    }

    /**
     * 获取所有字典类型列表
     */
    @RequestMapping(value = "/type/list")
    @ResponseBody
    public List<BaseDictionaryTypeVo> typeList(BaseDictionaryType baseDictionaryType) {
        return baseDictionaryService.queryTypes(baseDictionaryType);
    }

    /**
     * 获取所有字典类型列表(Tree)
     */
    @RequestMapping(value = "/type/tree/")
    @ResponseBody
    public List<ZTreeNode> typeTreeList() {
        List<ZTreeNode> zTreeNodes = baseDictionaryService.queryTypeTree();
        zTreeNodes.add(ZTreeNode.createParent());
        return zTreeNodes;
    }


    /**
     * 跳转到添加字典
     */
    @RequestMapping("/dict_add")
    public String deptAdd() {
        return PREFIX + "dict_add.html";
    }

    /**
     * 跳转到添加字典
     */
    @RequestMapping("/type/dict_add")
    public String typeDeptAdd() {
        return PREFIX + "dict_type_add.html";
    }

    /**
     * 删除字典记录
     */
    @RequestMapping(value = "/type/delete")
    @ResponseBody
    public Object typeDelete(@RequestParam Integer dictTypeId) {
        BaseDictionaryType baseDictionaryType = baseDictionaryService.selectDictTypeById(dictTypeId);
        baseDictionaryService.deleteDictType(dictTypeId);
        asyncRecordDeleteOperLog(dictTypeId, baseDictionaryType.getText(), BusinessOperationLogConstant.BUSINESS_TYPE.BUSINESS_DICT_TYPE.name()
                , (Integer) redissonClient.getBucket(RedisCacheKeyConst.BUSINESS_DICT_TYPE_ENV_CACHE_KEY_PREFIX.concat(ShiroKit.getUser().account)).get());
        return SUCCESS_TIP;
    }

    /**
     * 删除字典记录
     */
    @RequestMapping(value = "/item/delete")
    @ResponseBody
    public Object itemDelete(@RequestParam Integer dictId) {
        BaseDictionaryItem dict = baseDictionaryService.selectDictItemById(dictId);
        baseDictionaryService.deleteItemDict(dictId);
        asyncRecordDeleteOperLog(dictId, dict.getName(), BusinessOperationLogConstant.BUSINESS_TYPE.BUSINESS_DICT_ITEM.name()
                , (Integer) redissonClient.getBucket(RedisCacheKeyConst.BUSINESS_DICT_ITEM_ENV_CACHE_KEY_PREFIX.concat(ShiroKit.getUser().account)).get());
        return SUCCESS_TIP;
    }

    /**
     * 添加字典
     */
    @RequestMapping(value = "/item/add")
    @ResponseBody
    public Object dictItemAdd(BaseDictionaryItemVo baseDictionaryItemVo) {
        if (ToolUtil.isOneEmpty(baseDictionaryItemVo, baseDictionaryItemVo.getText(), baseDictionaryItemVo.getName(), baseDictionaryItemVo.getTypeId())) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        baseDictionaryService.insertDictItem(baseDictionaryItemVo);
        asyncRecordAddOperLog(baseDictionaryItemVo.getId(), baseDictionaryItemVo.getName()
                , BusinessOperationLogConstant.BUSINESS_TYPE.BUSINESS_DICT_ITEM.name()
                , (Integer) redissonClient.getBucket(RedisCacheKeyConst.BUSINESS_DICT_ITEM_ENV_CACHE_KEY_PREFIX.concat(ShiroKit.getUser().account)).get());
        return SUCCESS_TIP;
    }

    /**
     * 添加字典类型
     */
    @RequestMapping(value = "/type/add")
    @ResponseBody
    public Object typeAdd(BaseDictionaryType baseDictionaryType) {
        if (ToolUtil.isOneEmpty(baseDictionaryType, baseDictionaryType.getCode(), baseDictionaryType.getText())) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        baseDictionaryService.insertDictType(baseDictionaryType);
        asyncRecordAddOperLog(baseDictionaryType.getId(), baseDictionaryType.getText()
                , BusinessOperationLogConstant.BUSINESS_TYPE.BUSINESS_DICT_TYPE.name()
                , (Integer) redissonClient.getBucket(RedisCacheKeyConst.BUSINESS_DICT_TYPE_ENV_CACHE_KEY_PREFIX.concat(ShiroKit.getUser().account)).get());
        return SUCCESS_TIP;
    }

    /**
     * 跳转到修改字典类型页面
     */
    @RequestMapping("/type/dict_edit/{dictId}")
    public String deptDictTypeUpdate(@PathVariable Integer dictId, Model model) {
        BaseDictionaryType dictType = baseDictionaryService.selectDictTypeById(dictId);
        model.addAttribute("dictType", dictType);
        LogObjectHolder.me().set(dictType);
        return PREFIX + "dict_type_edit.html";
    }

    /**
     * 跳转到修改字典页面
     */
    @RequestMapping("/item/dict_edit/{dictId}")
    public String deptItemDictUpdate(@PathVariable Integer dictId, Model model) {
        BaseDictionaryItem dict = baseDictionaryService.selectDictItemById(dictId);
        model.addAttribute("dictItem", MappingUtils.beanConvert(dict, BaseDictionaryItemVo.class));
        LogObjectHolder.me().set(dict);
        return PREFIX + "dict_edit.html";
    }

    /**
     * 修改字典类型
     */
    @RequestMapping("/type/update")
    @ResponseBody
    public Object dictTypeUpdate(BaseDictionaryType baseDictionaryType) {
        if (ToolUtil.isOneEmpty(baseDictionaryType, baseDictionaryType.getId())) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        BaseDictionaryType compareBaseDictionaryType = baseDictionaryService.selectDictTypeById(baseDictionaryType.getId());
        baseDictionaryService.dictTypeUpdateBySelective(baseDictionaryType);
        asyncRecordModifyOperLog(compareBaseDictionaryType, baseDictionaryType
                , BusinessOperationLogConstant.BUSINESS_TYPE.BUSINESS_DICT_TYPE.name()
                , (Integer) redissonClient.getBucket(RedisCacheKeyConst.BUSINESS_DICT_TYPE_ENV_CACHE_KEY_PREFIX.concat(ShiroKit.getUser().account)).get());
        return SUCCESS_TIP;
    }

    /**
     * 修改字典
     */
    @RequestMapping("/item/update")
    @ResponseBody
    public Object dictItemUpdate(BaseDictionaryItemVo baseDictionaryItemVo) {
        if (ToolUtil.isOneEmpty(baseDictionaryItemVo, baseDictionaryItemVo.getId())) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        BaseDictionaryItem baseDictionaryItem = MappingUtils.beanConvert(baseDictionaryItemVo, BaseDictionaryItem.class);
        BaseDictionaryItem compareBaseDictionaryItem = baseDictionaryService.selectDictItemById(baseDictionaryItem.getId());
        baseDictionaryService.dictItemUpdateBySelective(baseDictionaryItem);

        asyncRecordModifyOperLog(baseDictionaryItem, compareBaseDictionaryItem
                , BusinessOperationLogConstant.BUSINESS_TYPE.BUSINESS_DICT_ITEM.name()
                , (Integer) redissonClient.getBucket(RedisCacheKeyConst.BUSINESS_DICT_ITEM_ENV_CACHE_KEY_PREFIX.concat(ShiroKit.getUser().account)).get()
        );
        return SUCCESS_TIP;
    }

    /**
     * 异步记录新增操作记录
     * @param id
     * @param name
     */
    private void asyncRecordAddOperLog(Integer id, String name, String type, Integer env){
        ForkJoinPool.commonPool().execute(() -> {
            BusinessOperationLog businessOperationLog = new BusinessOperationLog();
            businessOperationLog.setType(type);
            businessOperationLog.setTitle(BusinessOperationLogConstant.TITLE.INSERT.getName());
            businessOperationLog.setContent(new StringBuilder("新增 '").append(name).append("', id=").append(id).append(";").toString());
            businessOperationLogService.insert(assembleBaseUserInfo(businessOperationLog), env);
        });
    }

    /**
     * 异步记录删除操作记录
     * @param id
     * @param name
     */
    private void asyncRecordDeleteOperLog(Integer id, String name, String type, Integer env){
        ForkJoinPool.commonPool().execute(() -> {
            BusinessOperationLog businessOperationLog = new BusinessOperationLog();
            businessOperationLog.setTitle(BusinessOperationLogConstant.TITLE.DELETE.getName());
            businessOperationLog.setType(type);
            businessOperationLog.setContent(new StringBuilder("删除 '").append(name).append("', id=").append(id).append(";").toString());
            businessOperationLogService.insert(assembleBaseUserInfo(businessOperationLog), env);
        });
    }

    /**
     * 异步记录修改操作记录
     * @param obj
     * @param compareObj
     */
    private void asyncRecordModifyOperLog(Object obj, Object compareObj, String type, Integer env) {
        ForkJoinPool.commonPool().execute(() -> {
            Diff diff = javers.compare(obj, compareObj);
            if (diff.hasChanges()) {
                BusinessOperationLog businessOperationLog = new BusinessOperationLog();
                businessOperationLog.setTitle(BusinessOperationLogConstant.TITLE.UPDATE.getName());
                businessOperationLog.setType(type);
                StringBuilder stringBuilder = new StringBuilder();
                for (Change change :
                        diff.getChanges()) {
                    if (change instanceof ValueChange) {
                        ValueChange valueChange = (ValueChange) change;
                        stringBuilder.append("属性：").append((valueChange.getPropertyName())).append(" 由'")
                                .append(valueChange.getLeft()).append("'修改为'").append(valueChange.getRight()).append("';\r\n");
                    }
                }
                if (StringUtils.isEmpty(stringBuilder.toString())){
                    return;
                }
                businessOperationLog.setContent(stringBuilder.toString());
                businessOperationLogService.insert(assembleBaseUserInfo(businessOperationLog), env);
            }
        });
    }

    private BusinessOperationLog assembleBaseUserInfo(BusinessOperationLog businessOperationLog){
        ShiroUser user = ShiroKit.getUser();
        if (null == user) {
            businessOperationLog.setCreateBy("未登录");
            businessOperationLog.setUpdateBy("未登录");
        }else {
            businessOperationLog.setCreateBy(user.getName());
            businessOperationLog.setUpdateBy(user.getName());

        }
        businessOperationLog.setCreateTime(new Date());
        businessOperationLog.setUpdateTime(new Date());
        return businessOperationLog;
        
       
    }

}
