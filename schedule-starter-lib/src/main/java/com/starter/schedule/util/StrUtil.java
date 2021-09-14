package com.starter.schedule.util;

/**
 * @author frankq
 * @date 2021/9/8
 */
public class StrUtil {

    public static String joinStr(String... str) {
        StringBuilder sb = new StringBuilder();
        for (String sign : str) {
            if (null != sign) {
                sb.append(sign);
            }
        }
        return sb.toString();
    }

}
