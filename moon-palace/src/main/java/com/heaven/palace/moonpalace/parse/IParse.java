package com.heaven.palace.moonpalace.parse;

import com.heaven.palace.moonpalace.model.GenBeanEntity;
import com.heaven.palace.moonpalace.model.GenerationEntity;

import java.util.List;

/**
 * 模板解析接口
 *
 * @author ZhouShengEn
 * @date 2022年8月25日
 */
public interface IParse {

    static final String GEN_PARAMS = "g";
    static final String TABLE_DETAIL = "t";

    /**
     * 解析模板 生成文件
     */
    public List<String> parse(GenerationEntity generationEntity, GenBeanEntity tableEntity, List<String> fileList);

}
