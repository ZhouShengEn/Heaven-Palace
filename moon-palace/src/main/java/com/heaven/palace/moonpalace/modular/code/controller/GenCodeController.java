/**
 * Copyright 2022-8-25
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.heaven.palace.moonpalace.modular.code.controller;

import com.heaven.palace.moonpalace.base.controller.BaseController;
import com.heaven.palace.moonpalace.base.response.ObjectRestResponse;
import com.heaven.palace.moonpalace.model.GenBeanEntity;
import com.heaven.palace.moonpalace.modular.code.service.IGenService;
import com.heaven.palace.moonpalace.modular.code.vo.GenCodeCommitVO;
import com.heaven.palace.moonpalace.modular.code.vo.GenTableCodeCommitVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author ZhouShengEn 2022年8月25日
 */
@RestController
@RequestMapping("gen/code")
@RefreshScope
@Slf4j
@Api(tags = "代码生成模块")
public class GenCodeController extends BaseController {

    @Autowired
    private IGenService genService;

    @RequestMapping(value = "queryDb", method = RequestMethod.GET)
    @ApiOperation(value = "选择数据库")
    public ObjectRestResponse<List<String>> queryDb(@RequestParam Integer dbId) {
       return genService.queryDb(dbId);
    }

    @ApiOperation(value = "选择数据表")
    @RequestMapping(value = "queryTable", method = RequestMethod.GET)
    public ObjectRestResponse<List<GenBeanEntity>> queryTable(@RequestParam Integer dbId, @RequestParam String dbName) {
        return genService.queryTable(dbId, dbName);
    }

    @RequestMapping(value = "genCode", method = RequestMethod.POST)
    @ApiOperation(value = "代码生成")
    public ObjectRestResponse<Void> genCode(@RequestBody GenCodeCommitVO gcc, HttpServletResponse res) {
        genService.genCode(gcc, res);
        return new ObjectRestResponse<>().message("生成成功！");
    }


    @RequestMapping(value = "genTableCode", method = RequestMethod.POST)
    @ApiOperation(value = "表代码生成")
    public ObjectRestResponse<Void> genTableCode(@RequestBody GenTableCodeCommitVO gcc, HttpServletResponse res) {
        genService.genTableCode(gcc, res);
        return new ObjectRestResponse<>().message("生成成功！");
    }








}
