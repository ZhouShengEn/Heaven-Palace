package com.heaven.palace.purplecloudpalace.util;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * @author shuaixiaoji
 * @Description
 * @date 2021/11/18 17:11
 */
public class PriceConvertUtils {

    private static final BigDecimal RATIO_100 = new BigDecimal(100);

    public static Integer multiplyHunderd(BigDecimal originValue) {
        return Optional.ofNullable(originValue).orElse(BigDecimal.ZERO).multiply(RATIO_100).intValue();
    }

    public static BigDecimal divHunderd(Integer originValue) {
        return new BigDecimal(Optional.ofNullable(originValue).orElse(0)).divide(RATIO_100, 2, BigDecimal.ROUND_HALF_UP);
    }

    public static String divHunderd(String originValue) {
        return String.valueOf(new BigDecimal(originValue).divide(RATIO_100, 2, BigDecimal.ROUND_HALF_UP));
    }

    public static BigDecimal divHunderd(BigDecimal originValue) {
        return Optional.ofNullable(originValue).orElse(BigDecimal.ZERO).divide(RATIO_100, 2, BigDecimal.ROUND_HALF_UP);
    }

}
