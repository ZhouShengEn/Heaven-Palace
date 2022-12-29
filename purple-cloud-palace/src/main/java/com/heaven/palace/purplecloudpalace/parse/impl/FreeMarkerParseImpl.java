package com.heaven.palace.purplecloudpalace.parse.impl;

import com.heaven.palace.purplecloudpalace.model.GenBeanEntity;
import com.heaven.palace.purplecloudpalace.model.GenerationEntity;
import com.heaven.palace.purplecloudpalace.parse.IParse;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * FREEMARK 解析
 *
 * @author ZhouShengEn
 * @date 2022年8月25日
 */
public class FreeMarkerParseImpl implements IParse {

    private static final Logger LOGGER = LoggerFactory.getLogger(FreeMarkerParseImpl.class);

    private static final StringTemplateLoader resourceLoader = new StringTemplateLoader();

    private static Configuration cfg;

    static {
        try {
            cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
            cfg.setTemplateLoader(resourceLoader);
            cfg.setLocale(Locale.CHINA);
            cfg.setDefaultEncoding("UTF-8");
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public List<String> parse(GenerationEntity generationEntity, GenBeanEntity tableEntity, List<String> fileList) {
        List<String> renderList = new ArrayList<String>();
        Template t;
        try {
            for (String file : fileList) {
                resourceLoader.putTemplate(file.hashCode() + "_FTL+KEY", file);
                t = cfg.getTemplate(file.hashCode() + "_FTL+KEY");
                Writer write = new StringWriter();
                Map<String, Object> paramsMap = new HashMap<String, Object>();
                paramsMap.put(IParse.GEN_PARAMS, generationEntity);
                paramsMap.put(IParse.TABLE_DETAIL, tableEntity);
                t.process(paramsMap, write);
                renderList.add(write.toString());
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return renderList;
    }

}
