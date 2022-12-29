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
package com.heaven.palace.purplecloudpalace.modular.code.service;

import java.util.List;

import com.heaven.palace.purplecloudpalace.modular.code.model.TemplateModel;

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

}
