package com.heaven.palace.purplecloudpalace.modular.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.heaven.palace.purplecloudpalace.base.controller.BaseController;
import com.heaven.palace.purplecloudpalace.common.annotion.BussinessLog;
import com.heaven.palace.purplecloudpalace.common.constant.factory.ConstantFactory;
import com.heaven.palace.purplecloudpalace.common.exception.BizExceptionEnum;
import com.heaven.palace.purplecloudpalace.common.exception.BussinessException;
import com.heaven.palace.purplecloudpalace.common.persistence.dao.DictMapper;
import com.heaven.palace.purplecloudpalace.common.persistence.model.Dict;
import com.heaven.palace.purplecloudpalace.core.log.LogObjectHolder;
import com.heaven.palace.purplecloudpalace.modular.system.dao.DictDao;
import com.heaven.palace.purplecloudpalace.modular.system.service.IDictService;
import com.heaven.palace.purplecloudpalace.modular.system.warpper.DictWarpper;
import com.heaven.palace.purplecloudpalace.util.ToolUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 字典控制器
 *
 * @author ZhouShengEn
 * @Date 2022年8月25日
 */
@Controller
@RequestMapping("/dict")
public class DictController extends BaseController {

    private String PREFIX = "/system/dict/";

    @Resource
    DictDao dictDao;

    @Resource
    DictMapper dictMapper;

    @Resource
    IDictService dictService;

    /**
     * 跳转到字典管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "dict.html";
    }

    /**
     * 跳转到添加字典
     */
    @RequestMapping("/dict_add")
    public String deptAdd() {
        return PREFIX + "dict_add.html";
    }

    /**
     * 跳转到修改字典
     */
    @RequestMapping("/dict_edit/{dictId}")
    public String deptUpdate(@PathVariable Integer dictId, Model model) {
        Dict dict = dictMapper.selectById(dictId);
        model.addAttribute("dict", dict);
        List<Dict> subDicts = dictMapper.selectList(new QueryWrapper<Dict>().eq("pid", dictId));
        model.addAttribute("subDicts", subDicts);
        LogObjectHolder.me().set(dict);
        return PREFIX + "dict_edit.html";
    }

    /**
     * 新增字典
     *
     * @param dictValues 格式例如   "1:启用;2:禁用;3:冻结"
     */
    @BussinessLog(value = "添加字典记录", key = "dictName,dictValues", dict = com.heaven.palace.purplecloudpalace.common.constant.Dict.DictMap)
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(String dictName, String dictValues) {
        if (ToolUtil.isOneEmpty(dictName, dictValues)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        this.dictService.addDict(dictName, dictValues);
        return SUCCESS_TIP;
    }

    /**
     * 获取所有字典列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        List<Map<String, Object>> list = this.dictDao.list(condition);
        return super.warpObject(new DictWarpper(list));
    }

    /**
     * 字典详情
     */
    @RequestMapping(value = "/detail/{dictId}")
    @ResponseBody
    public Object detail(@PathVariable("dictId") Integer dictId) {
        return dictMapper.selectById(dictId);
    }

    /**
     * 修改字典
     */
    @BussinessLog(value = "修改字典", key = "dictName,dictValues", dict = com.heaven.palace.purplecloudpalace.common.constant.Dict.DictMap)
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Integer dictId, String dictName, String dictValues) {
        if (ToolUtil.isOneEmpty(dictId, dictName, dictValues)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        dictService.editDict(dictId, dictName, dictValues);
        return SUCCESS_TIP;
    }

    /**
     * 删除字典记录
     */
    @BussinessLog(value = "删除字典记录", key = "dictId", dict = com.heaven.palace.purplecloudpalace.common.constant.Dict.DeleteDict)
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer dictId) {
        //缓存被删除的名称
        LogObjectHolder.me().set(ConstantFactory.me().getDictName(dictId));
        this.dictService.delteDict(dictId);
        return SUCCESS_TIP;
    }

}
