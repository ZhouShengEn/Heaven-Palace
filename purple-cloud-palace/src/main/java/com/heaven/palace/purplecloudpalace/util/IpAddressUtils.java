package com.heaven.palace.purplecloudpalace.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author zhoushegnen
 * IP地址工具类，主要识别多网段重叠
 */
public class IpAddressUtils {

    /**
     * 分段位数
     */
    public final static int SEGMENT_DIGIT = 8;

    /**
     * 网段位数
     */
    public final static int CIDR_DIGIT = 32;

    /**
     * ip地址分割符-切割用
     */
    public final static String IP_ADDRESS_SPLIT = "\\.";

    /**
     * ip地址分割符-添加用
     */
    public final static String IP_ADDRESS_POINT = ".";
    
    /**
     * ip与掩码间的分割符
     */
    public final static String IP_MASK_SPLIT = "/";

    /**
     * IP地址正则表达式
     */
    public static final String IP_DNS_RULE =
            "((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))";

    /**
     * 根据掩码统计网段可分配ip地址数(包含网络地址及广播地址)
     *
     * @param mask 掩码：如 24
     * @return
     */
    public static Double countHostIpAddress(int mask) {
        int hostNum = CIDR_DIGIT - mask;
        return Math.pow(2, hostNum);

    }


    /**
     * 根据网段地址和掩码计算有效ip地址范围（不包含网络地址和广播地址）
     *
     * @param destination
     * @param mask
     * @return
     */
    public static List<IpAddressRange> calculateIpAddressRange(String destination, int mask) {

        ArrayList<IpAddressRange> ipAddressRanges = new ArrayList<>();

        String[] segments = destination.split(IP_ADDRESS_SPLIT);

        int integerNetworkNum = mask / SEGMENT_DIGIT;

        int tailNetworkNum = mask % SEGMENT_DIGIT;

        for (int i = 1; i <= segments.length; i++) {
            Integer defaultValue = Integer.valueOf(segments[i - 1]);
            if (i <= integerNetworkNum) {
                ipAddressRanges.add(IpAddressRange.builder()
                        .position(i)
                        .disabled(Boolean.TRUE)
                        .defaultValue(defaultValue)
                        .build());
            } else {
                // 范围最大值计算
                Double rangeMaxValue;
                if (i == integerNetworkNum + 1) {
                    rangeMaxValue = Math.pow(2, SEGMENT_DIGIT - tailNetworkNum) - 1 + defaultValue;
                } else {
                    rangeMaxValue = Math.pow(2, SEGMENT_DIGIT) - 1 + defaultValue;
                }
                ipAddressRanges.add(IpAddressRange.builder()
                        .position(i)
                        .disabled(Boolean.FALSE)
                        .defaultValue(defaultValue)
                        .range(IpAddressRange.Range.builder()
                                .rangeMinimum(defaultValue)
                                .rangeMaximum(rangeMaxValue.intValue())
                                .rangeStep(1)
                                .build())
                        .build());

            }

        }

        return ipAddressRanges;

    }

    /**
     * 校验ip地址是否在目标网段范围内（不包含网络地址和广播地址）
     *
     * @param ipAddress
     * @param destination
     * @param mask
     * @return
     */
    public static boolean validIpAddress(String ipAddress, String destination, int mask) {
        // 先根据网段和掩码算出可用ip范围
        List<IpAddressRange> ipAddressRanges
                = calculateIpAddressRange(destination, mask);
        String[] ipSegments = ipAddress.split(IP_ADDRESS_SPLIT);
        
        for (int i = 0; i < ipSegments.length; i++) {
            IpAddressRange ipAddressRange = ipAddressRanges.get(i);
            Integer ipSegment = Integer.valueOf(ipSegments[i]);
            // 不可编辑时说明是固定值，判断是否与固定值相等
            if (ipAddressRange.getDisabled() && !ipSegment.equals(ipAddressRange.getDefaultValue())) {
                return false;
            }
            IpAddressRange.Range range = ipAddressRange.getRange();
            // 可编辑时范围区间判断
            if (!ipAddressRange.getDisabled() && !ObjectUtils.isEmpty(range)) {
                // 非末位段时，判断是否不在区间范围值内
                if (!intervalValid(ipSegment, range.rangeMinimum, true, range.rangeMaximum, true)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 根据网段和掩码计算范围极值
     *
     * @param destination
     * @param mask
     * @param isMax
     * @return
     */
    public static String extremumCalculate(String destination, int mask, boolean isMax) {
        List<IpAddressRange> ipAddressRanges = calculateIpAddressRange(destination, mask);

        return isMax ?
                extremumCalculateInner(ipAddressRanges, IpAddressRange.Range::getExtremumMax, IpAddressRange.Range::getRangeMaximum)
                : extremumCalculateInner(ipAddressRanges, IpAddressRange.Range::getExtremumMin, IpAddressRange.Range::getRangeMinimum);
    }

    /**
     * 范围极值内部实现
     *
     * @param ipAddressRanges
     * @param extremum
     * @param rangeMum
     * @return
     */
    protected static String extremumCalculateInner(List<IpAddressRange> ipAddressRanges
            , Function<IpAddressRange.Range, Integer> extremum
            , Function<IpAddressRange.Range, Integer> rangeMum) {

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < ipAddressRanges.size(); i++) {
            IpAddressRange ipAddressRange = ipAddressRanges.get(i);
            if (ipAddressRange.getDisabled()) {
                result.append(ipAddressRange.getDefaultValue());
            } else {
                IpAddressRange.Range range = ipAddressRange.getRange();
                if (null != range.getExtremumMax()) {
                    result.append(extremum.apply(range));
                } else {
                    result.append(rangeMum.apply(range));
                }
            }
            if (i < (ipAddressRanges.size() - 1)) {
                result.append(IP_ADDRESS_POINT);
            }
        }
        return result.toString();
    }

    /**
     * 区间范围校验
     *
     * @param target
     * @param left
     * @param includeLeft
     * @param right
     * @param includeRight
     * @return
     */
    public static boolean intervalValid(Integer target, Integer left, Boolean includeLeft, Integer right, Boolean includeRight) {
        if (includeLeft && target >= left) {
            if (includeRight && target <= right) {
                return true;
            } else {
                return !includeRight && target < right;
            }
        } else if (!includeLeft && target > left) {
            if (includeRight && target <= right) {
                return true;
            } else {
                return !includeRight && target < right;
            }
        }
        return false;
    }

    /**
     * 网段重叠校验
     * @param cidrList
     * @return 为null校验成功，否则返回第一次出现重叠的两个网段
     */
    public static String[] validCidrOverlap(List<Cidr> cidrList){
        // 记录每一个坐标对应的下一跳坐标
        HashMap<String, Map<String, Cidr>> rangeMaps = new HashMap<>();
        // 坐标轴（数值线）
        ArrayList<String> ipNumRangeLine = new ArrayList<>();
        // 每一个网段的范围极值作为一组坐标塞入rangeMaps和坐标轴
        cidrList.forEach(cidr -> {
            String maxIp = extremumCalculate(cidr.destination, cidr.mask, true);
            String minIp = extremumCalculate(cidr.destination, cidr.mask, false);
            String maxIpNum = calculateIpNum(maxIp).concat(IP_MASK_SPLIT).concat(String.valueOf(cidr.mask));
            String minIpNum = calculateIpNum(minIp).concat(IP_MASK_SPLIT).concat(String.valueOf(cidr.mask));
            ipNumRangeLine.add(maxIpNum);
            ipNumRangeLine.add(minIpNum);
            HashMap<String, Cidr> maxRangeMap = new HashMap<>(1);
            maxRangeMap.put(maxIpNum, cidr);
            rangeMaps.put(minIpNum, maxRangeMap);
        });
        // 将坐标轴上所有坐标按照数组坐标大小排序
        ipNumRangeLine.sort(Comparator.comparing(ipNum -> ipNum.split(IP_MASK_SPLIT)[0]));
        // 下一跳坐标的指针
        Map<String, Cidr> targetIpNumMap = null;
        for (int i = 0; i < ipNumRangeLine.size(); i++) {
            String ipNum = ipNumRangeLine.get(i);
            Map<String, Cidr> doubleCidrMap = rangeMaps.get(ipNum);
            if (null != targetIpNumMap){
                // 如果下一跳坐标不是rangeMaps里绑定的最大值坐标说明范围出现重叠
                if (null == targetIpNumMap.get(ipNum)){
                    String cidr1 = doubleCidrMap.values().stream().map(cidr -> cidr.destination.concat(IP_MASK_SPLIT).concat(String.valueOf(cidr.mask))).findFirst().get();
                    String cidr2 = targetIpNumMap.values().stream().map(cidr -> cidr.destination.concat(IP_MASK_SPLIT).concat(String.valueOf(cidr.mask))).findFirst().get();
                    return new String[]{cidr1, cidr2};
                }else {
                    // 指针重新定位
                    targetIpNumMap = null;
                }
            }else {
                targetIpNumMap = doubleCidrMap;    
            }
        }
        return null;
    }

    private static String calculateIpNum(String maxIp) {
        String[] segments = maxIp.split(IP_ADDRESS_SPLIT);
        return BigDecimal.valueOf((long) Integer.parseInt(segments[0]) * ((Double)Math.pow(2, SEGMENT_DIGIT*3)).intValue())
                .add(BigDecimal.valueOf((long) Integer.parseInt(segments[1]) * ((Double)Math.pow(2, SEGMENT_DIGIT*2)).intValue()))
                .add(BigDecimal.valueOf((long) Integer.parseInt(segments[2]) * ((Double)Math.pow(2, SEGMENT_DIGIT)).intValue()))
                .add(BigDecimal.valueOf((long) Integer.parseInt(segments[3]) * ((Double)Math.pow(2, 0)).intValue())).toString();
    }

    public static void main(String[] args) {
        ArrayList<Cidr> cidrs = new ArrayList<>();
        cidrs.add(new Cidr("192.168.0.0", 24));
        cidrs.add(new Cidr("192.168.128.32", 27));
        cidrs.add(new Cidr("192.168.128.0", 25));
        validCidrOverlap(cidrs);
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Cidr {
        /**
         * 目标网段
         */
        private String destination;

        /**
         * 掩码
         */
        private Integer mask;
        
    }


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class IpAddressRange {
        /**
         * ip位，1，2，3，4
         */
        private Integer position;
        /**
         * 当前位输入框是否禁用，true/false
         */
        private Boolean disabled;
        /**
         * 当前位默认值
         */
        private Integer defaultValue;
        /**
         * 输入框范围
         */
        private Range range;


        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Range {
            /**
             * 范围可取最小值
             */
            private Integer rangeMinimum;
            /**
             * 范围可取最大值
             */
            private Integer rangeMaximum;
            /**
             * 范围极大值(用于末段位范围，如果前面的取值都是最大值，末段位范围的最大值应取该值)
             */
            private Integer extremumMax;
            /**
             * 范围极小值(用于末段位范围，如果前面的取值都是最小值，末段位范围的最小值应取该值)
             */
            private Integer extremumMin;
            /**
             * 范围间隔（步频/公差……）
             */
            private Integer rangeStep;
        }
    }

}
