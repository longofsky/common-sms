package com.adachina.common.util.json;

import com.adachina.common.util.Const;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author alexzhu
 */
public class JsonUtil implements java.io.Serializable {

    private static final long serialVersionUID = -8872078079583695100L;
    private static JsonUtil instance = new JsonUtil();
    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    private JsonUtil() {
    }

    public static JsonUtil getInstance() {
        return instance;
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> object2Map(Object obj, SerializerFeature... serializerFeature) {
        logger.info("object2Map " + serializerFeature);
        if (obj == null) {
            return new HashMap<>(Const.DS_INITIAL_SIZE_MAP);
        }
        String json = object2JSON(obj, SerializerFeature.WriteDateUseDateFormat,
                SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteMapNullValue);
        Map<String, Object> map = (Map<String, Object>) JSONObject.parse(json);

        return map;
    }

    public static List<Map<String, Object>> objects2List(Object obj, SerializerFeature... serializerFeature) {
        logger.info("objects2List " + serializerFeature);
        if (obj == null) {
            return new ArrayList<Map<String, Object>>();
        }
        String json = object2JSON(obj);
        List<Map<String, Object>> rows = (List<Map<String, Object>>) JSONObject.parse(json);

        return rows;
    }

    public static String object2JSON(Object obj, SerializerFeature... serializerFeature) {
        if (obj == null) {
            return "{}";
        }
        return JSON.toJSONString(obj, serializerFeature);
    }

    public static String object2JSON(Object obj) {
        if (obj == null) {
            return "{}";
        }
        return JSON.toJSONString(obj, SerializerFeature.WriteDateUseDateFormat, SerializerFeature.QuoteFieldNames, SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteNullStringAsEmpty);
    }
    
    /**
     * 对象转化成 Json字符串，忽略空属性
     * @param obj
     * @return
     */
    public static String object2JSONIgnoreNull(Object obj) {
        if (obj == null) {
            return "{}";
        }
        return JSON.toJSONString(obj, SerializerFeature.WriteDateUseDateFormat, SerializerFeature.QuoteFieldNames);
    }

    public static JSONObject object2JSONObject(Object obj) {
        return JSON.parseObject(object2JSON(obj));

    }

    public static <T> T json2Object(String json, Class<T> clazz) {
        if (json == null || json.isEmpty()) {
            return null;
        }
        return JSON.parseObject(json, clazz);
    }

    public static <T> T json2Reference(String json, TypeReference<T> reference) {
        if (json == null || json.isEmpty()) {
            return null;
        }
        return JSON.parseObject(json, reference);
    }

    public static <T> T json2Reference(String json, TypeReference<T> reference, Feature... features) {
        if (json == null || json.isEmpty()) {
            return null;
        }
        return JSON.parseObject(json, reference, features);
    }

    public static <T> T map2Object(Map<String, Object> map, Class<T> clazz) {
        return json2Object(JSON.toJSONString(map), clazz);
    }

    public static JSONObject string2JSON(String str) {
        return JSON.parseObject(str);
    }

    public static JSONArray string2JSONArray(String str) {
        return JSON.parseArray(str);
    }
}