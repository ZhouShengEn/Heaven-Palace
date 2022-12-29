package com.heaven.palace.purplecloudpalace.config.orika.convert;

import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

/**
 * @author :zhoushengen
 * @date : 2022/8/2
 */
public class String2IntConvert extends BidirectionalConverter<String, Integer> {
    @Override
    public Integer convertTo(String s, Type<Integer> type, MappingContext mappingContext) {
        Integer result = null;
        if (s != null){
            result = Integer.valueOf(s);
        }
        return result;
    }

    @Override
    public String convertFrom(Integer integer, Type<String> type, MappingContext mappingContext) {
        String result = null;
        if (null != integer){
            result = String.valueOf(integer);
        }
        return result;
    }
}
