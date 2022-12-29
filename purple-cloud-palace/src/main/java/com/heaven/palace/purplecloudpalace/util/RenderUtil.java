package com.heaven.palace.purplecloudpalace.util;

import com.heaven.palace.purplecloudpalace.exception.GunsException;
import com.heaven.palace.purplecloudpalace.exception.GunsExceptionEnum;
import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 渲染工具类
 *
 * @author ZhouShengEn
 * @date 2022-8-25
 */
public class RenderUtil {

    /**
     * 渲染json对象
     */
    public static void renderJson(HttpServletResponse response, Object jsonObject) {
        try {
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            writer.write(JSON.toJSONString(jsonObject));
        } catch (IOException e) {
            throw new GunsException(GunsExceptionEnum.WRITE_ERROR);
        }
    }
}
