/**
 * Copyright 2022-8-25
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.heaven.palace.purplecloudpalace.modular.code.service.impl;

import com.heaven.palace.purplecloudpalace.modular.code.model.TemplateFileModel;
import com.heaven.palace.purplecloudpalace.modular.code.model.TemplateModel;
import com.heaven.palace.purplecloudpalace.modular.code.service.IGenService;
import com.heaven.palace.purplecloudpalace.modular.code.service.ITemplateFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ZhouShengEn
 * 2022年8月25日
 */
@Service("genService")
public class GenServiceImpl implements IGenService {

    @Autowired
    private ITemplateFileService templateFileService;

    @Override
    public List<String> loadTemplateFile(List<TemplateModel> templateList) {
        List<String> list = new ArrayList<String>();
        for (TemplateModel templateEntity : templateList) {
            list.add(getTemplateFile(templateEntity));
        }
        return list;
    }

    private String getTemplateFile(TemplateModel templateEntity) {
        TemplateFileModel entity = new TemplateFileModel();
        entity.setTemplateId(templateEntity.getId());
        return templateFileService.selectOne(entity).getFile();
    }

}
