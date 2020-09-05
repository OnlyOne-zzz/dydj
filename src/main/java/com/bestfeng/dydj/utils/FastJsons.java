package com.bestfeng.dydj.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * fastJson util
 */
public class FastJsons {

    private final static Logger LOGGER = LoggerFactory
            .getLogger(FastJsons.class);
    private static final SerializerFeature[] featuresWithNullValue = {
            SerializerFeature.WriteMapNullValue,
            SerializerFeature.WriteNullBooleanAsFalse,
            SerializerFeature.WriteNullListAsEmpty,
            SerializerFeature.WriteNullNumberAsZero,
            SerializerFeature.WriteNullStringAsEmpty};

    /**
     * <B>方法名称：</B>将JSON字符串转换为实体对象<BR>
     * <B>概要说明：</B>将JSON字符串转换为实体对象<BR>
     *
     * @param data  JSON字符串
     * @param clzss 转换对象
     * @return T
     */
    public static <T> T convertJSONToObject(String data, Class<T> clzss) {
        try {
            T t = JSON.parseObject(data, clzss);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * <B>方法名称：</B>将JSONObject对象转换为实体对象<BR>
     * <B>概要说明：</B>将JSONObject对象转换为实体对象<BR>
     *
     * @param data  JSONObject对象
     * @param clzss 转换对象
     * @return T
     */
    public static <T> T convertJSONToObject(JSONObject data, Class<T> clzss) {
        try {
            T t = JSONObject.toJavaObject(data, clzss);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * <B>方法名称：</B>将JSON字符串数组转为List集合对象<BR>
     * <B>概要说明：</B>将JSON字符串数组转为List集合对象<BR>
     *
     * @param data  JSON字符串数组
     * @param clzss 转换对象
     * @return List<T>集合对象
     */
    public static <T> List<T> convertJSONToArray(String data, Class<T> clzss) {
        try {
            List<T> t = JSON.parseArray(data, clzss);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将JSON字符串数组转为List集合对象
     *
     * @param data JSON字符串数组
     * @return List<T> 集合对象
     */
    public static <T> List<T> convertJSONToArray(String data) {
        return convertJSONTypeReference(data, new TypeReference<List<T>>() {
        });
    }

    /**
     * Example:
     *
     * <pre>
     * convertJSONTypeReference(&quot;[{\&quot;name\&quot;:\&quot;xx\&quot;},{\&quot;name\&quot;:\&quot;xx1\&quot;}]&quot;,
     * 		new TypeReference&lt;List&lt;Map&lt;String, Object&gt;&gt;&gt;() {
     *        });
     * </pre>
     */
    public static <T> T convertJSONTypeReference(String data,
                                                 TypeReference<T> typeReference) {
        try {
            T listMap = JSON.parseObject(data, typeReference);
            return listMap;
        } catch (Exception e) {
            LOGGER.error("转换JSON失败 异常消息{} \n原文为:{}", e.getMessage(), data);
            return null;
        }
    }

    public static <V> Map<String, V> convertJSONMap(String data) {
        return convertJSONTypeReference(data,
                new TypeReference<Map<String, V>>() {
                });
    }

    /**
     * <B>方法名称：</B>将List<JSONObject>转为List集合对象<BR>
     * <B>概要说明：</B>将List<JSONObject>转为List集合对象<BR>
     *
     * @param data  List<JSONObject>
     * @param clzss 转换对象
     * @return List<T>集合对象
     */
    public static <T> List<T> convertJSONToArray(List<JSONObject> data,
                                                 Class<T> clzss) {
        try {
            List<T> t = new ArrayList<T>();
            for (JSONObject jsonObject : data) {
                t.add(convertJSONToObject(jsonObject, clzss));
            }
            return t;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * <B>方法名称：</B>将对象转为JSON字符串<BR>
     * <B>概要说明：</B>将对象转为JSON字符串<BR>
     *
     * @param obj 任意对象
     * @return JSON字符串
     */
    public static String convertObjectToJSON(Object obj) {
        try {
            String text = JSON.toJSONString(obj);
            return text;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * <B>方法名称：</B>将对象转为(JSON字符串)<BR>
     * <B>概要说明：</B>将对象转为(JSON字符串)<BR>
     *
     * @param obj 任意对象
     * @return JSON字符串
     */
    public static String convertObjectToJSONBracket(Object obj) {
        try {
            String text = JSON.toJSONString(obj);
            return "(" + text + ")";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * <B>方法名称：</B>将对象转为JSONObject对象<BR>
     * <B>概要说明：</B>将对象转为JSONObject对象<BR>
     *
     * @param obj 任意对象
     * @return JSONObject对象
     */
    public static JSONObject convertObjectToJSONObject(Object obj) {
        try {
            if (obj instanceof String) {
                return JSONObject.parseObject(obj.toString());
            }
            if (obj instanceof byte[]) {
                return JSONObject.parseObject(new String((byte[]) obj, StandardCharsets.UTF_8));
            }
            if (obj instanceof char[]) {
                return JSONObject.parseObject(new String((char[]) obj));
            }
            return JSON.parseObject(JSON.toJSONString(obj));
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONObject();
        }
    }

    public static <T> String convertObjectToJSONPropertyPreFilter(Object obj,
                                                                  SerializeFilter filter) {
        try {
            String text = JSON.toJSONString(obj, filter);
            return text;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * <B>方法名称：</B><BR>
     * <B>概要说明：</B><BR>
     */
    public static String convertObjectToJSONWithNullValue(Object obj) {
        try {
            String text = JSON.toJSONString(obj, featuresWithNullValue);
            return text;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String convertObjectToJSON(Object obj, SerializerFeature... features) {
        try {
            String text = JSON.toJSONString(obj, features);
            return text;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T convertJSONToObject(String json, Feature... features) {
        try {
            T t = (T) JSONObject.parse(json, features);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T convertJSONToObject(String json, Class<T> clazz, Feature... features) {
        try {
            T t = JSONObject.parseObject(json, clazz, features);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
