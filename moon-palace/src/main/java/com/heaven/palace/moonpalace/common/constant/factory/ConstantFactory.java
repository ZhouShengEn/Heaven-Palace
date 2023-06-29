package com.heaven.palace.moonpalace.common.constant.factory;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.heaven.palace.moonpalace.common.constant.cache.Cache;
import com.heaven.palace.moonpalace.common.constant.cache.CacheKey;
import com.heaven.palace.moonpalace.common.constant.state.ManagerStatus;
import com.heaven.palace.moonpalace.common.constant.state.MenuStatus;
import com.heaven.palace.moonpalace.common.persistence.dao.DeptMapper;
import com.heaven.palace.moonpalace.common.persistence.dao.DictMapper;
import com.heaven.palace.moonpalace.common.persistence.dao.MenuMapper;
import com.heaven.palace.moonpalace.common.persistence.dao.NoticeMapper;
import com.heaven.palace.moonpalace.common.persistence.dao.RoleMapper;
import com.heaven.palace.moonpalace.common.persistence.dao.UserMapper;
import com.heaven.palace.moonpalace.common.persistence.model.Dept;
import com.heaven.palace.moonpalace.common.persistence.model.Dict;
import com.heaven.palace.moonpalace.common.persistence.model.Menu;
import com.heaven.palace.moonpalace.common.persistence.model.Notice;
import com.heaven.palace.moonpalace.common.persistence.model.Role;
import com.heaven.palace.moonpalace.common.persistence.model.User;
import com.heaven.palace.moonpalace.support.StrKit;
import com.heaven.palace.moonpalace.util.Convert;
import com.heaven.palace.moonpalace.util.SpringContextHolder;
import com.heaven.palace.moonpalace.util.ToolUtil;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * 常量的生产工厂
 *
 * @author ZhouShengEn
 * @date 2022年8月25日
 */
@Component
@DependsOn("springContextHolder")
public class ConstantFactory implements IConstantFactory {

    @Resource
    private RoleMapper roleMapper;
    @Resource
    private DeptMapper deptMapper;
    @Resource
    private DictMapper dictMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private MenuMapper menuMapper;
    @Resource
    private NoticeMapper noticeMapper;

    public static IConstantFactory me() {
        return SpringContextHolder.getBean("constantFactory");
    }

    /**
     * 根据用户id获取用户名称
     *
     * @author ZhouShengEn
     * @Date 2017/5/9 23:41
     */
    @Override
    @Cacheable(value = Cache.USER, key = "'" + CacheKey.USER_NAME + "'+#userId")
    public String getUserNameById(Integer userId) {
        User user = userMapper.selectById(userId);
        if (user != null) {
            return user.getName();
        } else {
            return "--";
        }
    }

    /**
     * 根据用户id获取用户账号
     *
     * @author ZhouShengEn
     * @date 2022年8月25日
     */
    @Override
    public String getUserAccountById(Integer userId) {
        User user = userMapper.selectById(userId);
        if (user != null) {
            return user.getAccount();
        } else {
            return "--";
        }
    }

    /**
     * 通过角色ids获取角色名称
     */
    @Override
    @Cacheable(value = Cache.CONSTANT, key = "'" + CacheKey.ROLES_NAME + "'+#roleIds")
    public String getRoleName(String roleIds) {
        Integer[] roles = Convert.toIntArray(roleIds);
        StringBuilder sb = new StringBuilder();
        for (int role : roles) {
            Role roleObj = roleMapper.selectById(role);
            if (ToolUtil.isNotEmpty(roleObj) && ToolUtil.isNotEmpty(roleObj.getName())) {
                sb.append(roleObj.getName()).append(",");
            }
        }
        return StrKit.removeSuffix(sb.toString(), ",");
    }

    /**
     * 通过角色id获取角色名称
     */
    @Override
    @Cacheable(value = Cache.CONSTANT, key = "'" + CacheKey.SINGLE_ROLE_NAME + "'+#roleId")
    public String getSingleRoleName(Integer roleId) {
        if (0 == roleId) {
            return "--";
        }
        Role roleObj = roleMapper.selectById(roleId);
        if (ToolUtil.isNotEmpty(roleObj) && ToolUtil.isNotEmpty(roleObj.getName())) {
            return roleObj.getName();
        }
        return "";
    }

    /**
     * 通过角色id获取角色英文名称
     */
    @Override
    @Cacheable(value = Cache.CONSTANT, key = "'" + CacheKey.SINGLE_ROLE_TIP + "'+#roleId")
    public String getSingleRoleTip(Integer roleId) {
        if (0 == roleId) {
            return "--";
        }
        Role roleObj = roleMapper.selectById(roleId);
        if (ToolUtil.isNotEmpty(roleObj) && ToolUtil.isNotEmpty(roleObj.getName())) {
            return roleObj.getTips();
        }
        return "";
    }

    /**
     * 获取部门名称
     */
    @Override
    @Cacheable(value = Cache.CONSTANT, key = "'" + CacheKey.DEPT_NAME + "'+#deptId")
    public String getDeptName(Integer deptId) {
        Dept dept = deptMapper.selectById(deptId);
        if (ToolUtil.isNotEmpty(dept) && ToolUtil.isNotEmpty(dept.getFullname())) {
            return dept.getFullname();
        }
        return "";
    }

    /**
     * 获取菜单的名称们(多个)
     */
    @Override
    public String getMenuNames(String menuIds) {
        Integer[] menus = Convert.toIntArray(menuIds);
        StringBuilder sb = new StringBuilder();
        for (int menu : menus) {
            Menu menuObj = menuMapper.selectById(menu);
            if (ToolUtil.isNotEmpty(menuObj) && ToolUtil.isNotEmpty(menuObj.getName())) {
                sb.append(menuObj.getName()).append(",");
            }
        }
        return StrKit.removeSuffix(sb.toString(), ",");
    }

    /**
     * 获取菜单名称
     */
    @Override
    public String getMenuName(Integer menuId) {
        if (ToolUtil.isEmpty(menuId)) {
            return "";
        } else {
            Menu menu = menuMapper.selectById(menuId);
            if (menu == null) {
                return "";
            } else {
                return menu.getName();
            }
        }
    }

    /**
     * 获取菜单名称通过编号
     */
    @Override
    public String getMenuNameByCode(String code) {
        if (ToolUtil.isEmpty(code)) {
            return "";
        } else {
            QueryWrapper<Menu> menuWrapper = new QueryWrapper<Menu>();
            Menu menu = menuMapper.selectOne(menuWrapper.eq("code", code));
            if (menu == null) {
                return "";
            } else {
                return menu.getName();
            }
        }
    }

    /**
     * 获取字典名称
     */
    @Override
    public String getDictName(Integer dictId) {
        if (ToolUtil.isEmpty(dictId)) {
            return "";
        } else {
            Dict dict = dictMapper.selectById(dictId);
            if (dict == null) {
                return "";
            } else {
                return dict.getName();
            }
        }
    }

    /**
     * 获取通知标题
     */
    @Override
    public String getNoticeTitle(Integer dictId) {
        if (ToolUtil.isEmpty(dictId)) {
            return "";
        } else {
            Notice notice = noticeMapper.selectById(dictId);
            if (notice == null) {
                return "";
            } else {
                return notice.getTitle();
            }
        }
    }

    /**
     * 根据字典名称和字典中的值获取对应的名称
     */
    @Override
    public String getDictsByName(String name, Integer val) {
  
        QueryWrapper<Dict> wrapper = new QueryWrapper<Dict>();
        Dict dict = dictMapper.selectOne(wrapper.eq("name", name));
        if (dict == null) {
            return "";
        } else {
            QueryWrapper<Dict> wrapper2 = new QueryWrapper<Dict>();
            wrapper2.eq("pid", dict.getId());
            List<Dict> dicts = dictMapper.selectList(wrapper2);
            for (Dict item : dicts) {
                if (item.getNum() != null && item.getNum().equals(val)) {
                    return item.getName();
                }
            }
            return "";
        }
    }

    /**
     * 获取性别名称
     */
    @Override
    public String getSexName(Integer sex) {
        return getDictsByName("性别", sex);
    }

    /**
     * 获取用户登录状态
     */
    @Override
    public String getStatusName(Integer status) {
        return ManagerStatus.valueOf(status);
    }

    /**
     * 获取菜单状态
     */
    @Override
    public String getMenuStatusName(Integer status) {
        return MenuStatus.valueOf(status);
    }

    /**
     * 查询字典
     */
    @Override
    public List<Dict> findInDict(Integer id) {
        if (ToolUtil.isEmpty(id)) {
            return null;
        } else {
            QueryWrapper<Dict> wrapper2 = new QueryWrapper<Dict>();
            wrapper2.eq("pid", id);
            List<Dict> dicts = dictMapper.selectList(wrapper2.eq("pid", id));
            if (dicts == null || dicts.size() == 0) {
                return null;
            } else {
                return dicts;
            }
        }
    }

    /**
     * 获取被缓存的对象(用户删除业务)
     */
    @Override
    public String getCacheObject(String para) {
        return "";
    }

    /**
     * 获取子部门id
     */
    @Override
    public List<Integer> getSubDeptId(Integer deptid) {

        QueryWrapper<Dept> wrapper2 = new QueryWrapper<Dept>();
        List<Dept> depts = this.deptMapper.selectList(wrapper2.like("pids", "%[" + deptid + "]%"));

        ArrayList<Integer> deptids = new ArrayList<Integer>();

        if (depts != null || depts.size() > 0) {
            for (Dept dept : depts) {
                deptids.add(dept.getId());
            }
        }

        return deptids;
    }

    /**
     * 获取所有父部门id
     */
    @Override
    public List<Integer> getParentDeptIds(Integer deptid) {
        Dept dept = deptMapper.selectById(deptid);
        String pids = dept.getPids();
        String[] split = pids.split(",");
        ArrayList<Integer> parentDeptIds = new ArrayList<Integer>();
        for (String s : split) {
            parentDeptIds.add(Integer.valueOf(StrKit.removeSuffix(StrKit.removePrefix(s, "["), "]")));
        }
        return parentDeptIds;
    }


}
