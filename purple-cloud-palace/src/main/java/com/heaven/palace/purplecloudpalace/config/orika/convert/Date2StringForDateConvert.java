package com.heaven.palace.purplecloudpalace.config.orika.convert;

import com.heaven.palace.purplecloudpalace.util.DateUtil;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * @author :zhoushengen
 * @date : 2022/9/7
 * date类型与String类型互转
 */
public class Date2StringForDateConvert extends BidirectionalConverter<Date, String> {
    @Override
    public String convertTo(Date date, Type<String> type, MappingContext mappingContext) {
        String result = null;
        if (null != date) {
            result = DateFormatUtils.format(date, DateUtil.DATE_FORMAT_1);
        }
        return result;
    }

    @Override
    public Date convertFrom(String s, Type<Date> type, MappingContext mappingContext) {

        try {
            return DateUtils.parseDate(s, DateUtil.DATE_FORMAT_1);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }
}
