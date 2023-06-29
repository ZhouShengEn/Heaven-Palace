/**
 * Copyright 2022-8-25
 *   
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.heaven.palace.moonpalace.modular.code.service;

import com.heaven.palace.moonpalace.base.response.ObjectRestResponse;
import com.heaven.palace.moonpalace.model.GenBeanEntity;
import com.heaven.palace.moonpalace.modular.code.model.TemplateModel;
import com.heaven.palace.moonpalace.modular.code.vo.GenCodeCommitVO;
import com.heaven.palace.moonpalace.modular.code.vo.GenTableCodeCommitVO;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 生成支持服务
 * @author ZhouShengEn
 * 2022年8月25日
 */
public interface IGenService {
    /**
     * 获取文件
     * @param templateList
     * @return
     */
    List<String> loadTemplateFile(List<TemplateModel> templateList);

    /**
     * 查询数据库
     * @param dbId
     * @return
     */
    ObjectRestResponse<List<String>> queryDb(Integer dbId);

    /**
     * 查询表
     * @param dbId
     * @param tableName
     * @return
     */
    ObjectRestResponse<List<GenBeanEntity>> queryTable(@RequestParam Integer dbId, @RequestParam String tableName);

    /**
     * 代码生成
     * @param gcc
     * @param res
     * @return
     */
    void genCode(GenCodeCommitVO gcc, HttpServletResponse res);

    /**
     * 表代码生成
     * @param gcc
     * @param res
     * @return
     */
    void genTableCode(GenTableCodeCommitVO gcc, HttpServletResponse res);

}
