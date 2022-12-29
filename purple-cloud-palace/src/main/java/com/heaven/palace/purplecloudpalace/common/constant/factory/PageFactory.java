package com.heaven.palace.purplecloudpalace.common.constant.factory;

import com.heaven.palace.purplecloudpalace.support.HttpKit;
import com.heaven.palace.purplecloudpalace.util.ToolUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import javax.servlet.http.HttpServletRequest;


/**
 * BootStrap Table默认的分页参数创建
 *
 * @author ZhouShengEn
 * @date 2022-8-25
 */
public class PageFactory<T> {

    public Page<T> defaultPage() {
        HttpServletRequest request = HttpKit.getRequest();
        int limit = Integer.valueOf(request.getParameter("limit"));
        int offset = Integer.valueOf(request.getParameter("offset"));
        String sort = request.getParameter("sort");
        String order = request.getParameter("order");
        if (ToolUtil.isEmpty(sort)) {
            Page<T> page = new Page<T>((offset / limit + 1), limit);
            return page;
        } else {
            Page<T> page = new Page<T>((offset / limit + 1), limit, Long.parseLong(sort));
            return page;
        }
    }
}
