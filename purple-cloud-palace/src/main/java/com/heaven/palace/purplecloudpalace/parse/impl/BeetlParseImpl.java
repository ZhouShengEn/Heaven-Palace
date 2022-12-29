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
package com.heaven.palace.purplecloudpalace.parse.impl;

import com.heaven.palace.purplecloudpalace.model.GenBeanEntity;
import com.heaven.palace.purplecloudpalace.model.GenerationEntity;
import com.heaven.palace.purplecloudpalace.parse.IParse;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.StringTemplateResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 解析模板,生产代码
 * @author ZhouShengEn
 * 2022年8月25日
 */
public class BeetlParseImpl implements IParse {

    private static final Logger LOGGER = LoggerFactory.getLogger(BeetlParseImpl.class);

    private static final StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();

    private static Configuration cfg;
    private static GroupTemplate gt;

    static {
        try {
            cfg = Configuration.defaultConfiguration();
            gt = new GroupTemplate(resourceLoader, cfg);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public List<String> parse(GenerationEntity generationEntity, GenBeanEntity tableEntity, List<String> fileList) {
        List<String> renderList = new ArrayList<String>();
        Template t;
        for (String file : fileList) {
            t = gt.getTemplate(file);
            t.binding(IParse.GEN_PARAMS, generationEntity);
            t.binding(IParse.TABLE_DETAIL, tableEntity);
            renderList.add(t.render());
        }
        return renderList;
    }

}
