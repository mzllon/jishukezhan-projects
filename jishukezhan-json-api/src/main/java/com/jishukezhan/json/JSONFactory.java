package com.jishukezhan.json;

import com.jishukezhan.json.annotation.JSONName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

public class JSONFactory {

    private static final Logger log = LoggerFactory.getLogger(JSONFactory.class);

    private static Class<? extends JSONBuilder> defaultJsonBuilderClass;

    private static final Map<String, Class<? extends JSONBuilder>> allRegistryJsonBuilder = new HashMap<>();


    static {
        findJsonBuilderImplClasses();
    }

    private static void findJsonBuilderImplClasses() {
        ServiceLoader<JSONBuilder> loader = ServiceLoader.load(JSONBuilder.class);
        loader.forEach(jsonBuilder -> {
            Class<? extends JSONBuilder> jsonBuilderClass = jsonBuilder.getClass();
            String name = parseName(jsonBuilderClass);
            log.info("Registry a json builder {} for {}", jsonBuilderClass.getCanonicalName(), name);
            allRegistryJsonBuilder.put(name, jsonBuilderClass);
            if (defaultJsonBuilderClass == null) {
                // 多个注册的区匹配的第一个
                defaultJsonBuilderClass = jsonBuilderClass;
            }
        });
    }

    private static String parseName(Class<? extends JSONBuilder> clazz) {
        JSONName jsonNameAnno = clazz.getAnnotation(JSONName.class);
        if (jsonNameAnno != null) {
            String name = jsonNameAnno.value();
            name = name.trim();
            if (!name.isEmpty()) {
                return name;
            }
        }
        return clazz.getName();
    }

    public static JSONBuilder create() {
        return create(defaultJsonBuilderClass);
    }

    public static JSONBuilder create(String jsonName) {
        if (jsonName == null) {
            return create();
        }
        Class<? extends JSONBuilder> clazz = allRegistryJsonBuilder.get(jsonName);
        return create(clazz);
    }

    private static JSONBuilder create(Class<? extends JSONBuilder> jsonBuilderClass) {
        if (jsonBuilderClass != null) {
            try {
                return jsonBuilderClass.newInstance();
            } catch (Exception e) {
                log.error("Can't create a default json builder, " + defaultJsonBuilderClass.getCanonicalName(), e);
            }
        }
//        throw new RuntimeException("Can't find any supported JSON libraries : [gson, jackson, fastjson], check you classpath has one of these jar pairs: [fastjson, easyjson-fastjson], [gson, easyjson-gson], [jackson, easyjson-jackson]");
        return new NonJSONBuilder();
    }

}
