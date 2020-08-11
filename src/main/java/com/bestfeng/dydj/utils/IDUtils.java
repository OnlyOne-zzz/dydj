package com.bestfeng.dydj.utils;


import org.apache.commons.lang.StringUtils;

import java.util.Objects;
import java.util.Random;

/**
 * @author zh
 */
public class IDUtils {

    private static Random random = new Random();
    private static SnowflakeIdWorker snowflakeIdWorker =
            new SnowflakeIdWorker(random.nextInt(31), random.nextInt(31));

    public static String getId() {
        return Objects.toString(snowflakeIdWorker.nextId());
    }


    public static String getId(String pre) {
        if(StringUtils.isNotBlank(pre)){
            return pre.concat(getId());
        }
        return getId();
    }

    public static Long getLongId() {
        return snowflakeIdWorker.nextId();
    }

}
