package com.jishukezhan.core.lang;

import com.jishukezhan.core.exceptions.IoRuntimeException;
import com.jishukezhan.core.io.resource.Resource;
import com.jishukezhan.core.io.resource.ResourceLoader;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class PlaceholderPropertiesResolver {

    private String placeholderPrefix = "${";
    private String placeholderSuffix = "}";

    private List<Resource> locations;

    private Map<String, String> resolveProperties;

    private Map<String, String> variables;

    public PlaceholderPropertiesResolver(String... locations) {
        this.locations = new ResourceLoader(locations).load();
        resolve();
    }

//    public PlaceholderPropertiesResolver(String placeholderPrefix, String placeholderSuffix, String... locations) {
//        this.placeholderPrefix = ObjectUtils.requireNonNull(placeholderPrefix);
//        this.placeholderSuffix = ObjectUtils.requireNonNull(placeholderSuffix);
//        this.locations = new ResourceLoader(ArrayUtils.requireNotEmpty(locations)).parse();
//    }
//
//    public Map<String, String> getVariables() {
//        return variables;
//    }
//
//    public void setVariables(Map<String, String> variables) {
//        this.variables = variables;
//    }

    /**
     * 处理{@code properties}文件
     */
    private void resolve() {

        //==============解析properties文件=============
        List<Properties> propertiesList = new ArrayList<>();
        int count = 0;
        for (Resource resource : locations) {
            Properties properties = new Properties();
            try {
                properties.load(resource.getInputStream());
                propertiesList.add(properties);
                count += properties.size();
            } catch (IOException e) {
                throw new IoRuntimeException(e);
            }
        }


        //===============取出其中的key=value=================
        HashMap<String, String> stringValueMap = new HashMap<>(count);
        for (Properties properties : propertiesList) {
            for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                stringValueMap.put((String) entry.getKey(), (String) entry.getValue());
            }
        }


        //===========解析占位符=================
        String key, value;
        resolveProperties = new HashMap<>(stringValueMap.size());
        for (Map.Entry<String, String> entry : stringValueMap.entrySet()) {
            key = entry.getKey();
            value = entry.getValue();
            resolveProperties.put(key, this.replacePlaceholders(value, stringValueMap));
        }
    }

    /**
     * 占位符替换处理
     *
     * @param value          待处理的字符串
     * @param stringValueMap 原始键值对
     * @return 处理后的字符串
     */
    private String replacePlaceholders(String value, Map<String, String> stringValueMap) {
        if (!value.contains(placeholderPrefix)) {
            return value;
        }
        StringBuilder buffer = new StringBuilder();
        final char[] chars = value.toCharArray();
        for (int pos = 0, length = chars.length; pos < length; pos++) {
            if (chars[pos] == '$') {
                // peek ahead
                if (chars[pos + 1] == '{') {
                    StringBuilder placeholder = new StringBuilder(100);
                    int x = pos + 2;
                    for (; x < length && chars[x] != '}'; x++) {
                        placeholder.append(chars[x]);
                        if (x == length - 1) {
                            return value;
                        }
                    }
                    //step1.首先从当前属性文件中查找
                    String extractValue = stringValueMap.get(placeholder.toString());
                    //step2.然后从系统环境变量查找
                    if (StringUtil.isEmpty(extractValue)) {
                        extractValue = extractFromSystem(placeholder.toString());
                    }
                    buffer.append(extractValue == null ? "" : extractValue);
                    pos = x + 1;
                    // make sure spinning forward did not put us past the end of the buffer...
                    if (pos >= chars.length) {
                        break;
                    }
                }
            }
            buffer.append(chars[pos]);
        }
        return this.replacePlaceholders(buffer.toString(), stringValueMap);
    }

    /**
     * 从系统环境变量查找值
     *
     * @param key 键名
     * @return 值
     */
    private String extractFromSystem(String key) {
        return System.getProperty(key);
    }

    /**
     * 判断{@code key}是否存在
     *
     * @param key 键名，非空
     * @return 如果存在则返回{@code true}，否则返回{@code false}
     */
    public boolean containsProperty(String key) {
        return StringUtil.hasLength(key) && resolveProperties.get(key) != null;
    }

    /**
     * 返回所有的键值对
     */
    public Map<String, String> getAllProperties() {
        return new HashMap<>(resolveProperties);
    }

    /**
     * 返回{@code key}对应的值
     *
     * @param key 键名，非空
     * @return 如果存在则返回值，否则返回{@code null}
     */
    public String getProperty(String key) {
        return getProperty(key, (String) null);
    }

    /**
     * 返回{@code key}对应的值，如果值不存在，则返回{@code defaultValue}
     *
     * @param key          键名，非空
     * @param defaultValue 当值为空是则返回该值
     * @return 如果存在则返回值，否则返回{@code defaultValue}
     */
    public String getProperty(String key, String defaultValue) {
        if (StringUtil.isEmpty(key)) {
            return defaultValue;
        }
        String value = resolveProperties.get(key);
        return StringUtil.isEmpty(value) ? defaultValue : value;
    }

    public <T> T getProperty(String key, Class<T> targetType) {
        return getProperty(key, targetType, null);
    }

    @SuppressWarnings("unchecked")
    public <T> T getProperty(String key, Class<T> targetType, T defaultValue) {
        if (StringUtil.isEmpty(key)) {
            return defaultValue;
        }
        String value = resolveProperties.get(key);
        if (StringUtil.isEmpty(value)) {
            return defaultValue;
        }
        if (targetType == String.class) {
            return (T) value;
        } else if (targetType == int.class) {
            return (T) new Integer(value);
        } else if (targetType == Integer.class) {
            return (T) new Integer(value);
        } else if (targetType == Short.class) {
            return (T) new Short(value);
        } else if (targetType == short.class) {
            return (T) Short.valueOf(value);
        } else if (targetType == Byte.class) {
            return (T) new Byte(value);
        } else if (targetType == byte.class) {
            return (T) new Byte(value);
        } else if (targetType == Character.class) {
            return (T) new Character(value.toCharArray()[0]);
        } else if (targetType == char.class) {
            return (T) new Character(value.toCharArray()[0]);
        } else if (targetType == Float.class) {
            return (T) new Float(value);
        } else if (targetType == float.class) {
            return (T) new Float(value);
        } else if (targetType == Long.class) {
            return (T) new Long(value);
        } else if (targetType == long.class) {
            return (T) new Long(value);
        } else if (targetType == Double.class) {
            return (T) new Double(value);
        } else if (targetType == double.class) {
            return (T) new Double(value);
        } else if (targetType == Boolean.class) {
            return (T) Boolean.valueOf(value);
        } else if (targetType == boolean.class) {
            return (T) Boolean.valueOf(value);
        } else if (targetType == BigDecimal.class) {
            return (T) new BigDecimal(value);
        } else {
            return (T) value;
        }
    }

    /**
     * 处理占位符{@code ${...}}字符串，通过调用{@linkplain #getProperty(String)}替换为对应的值。
     * 如果无法替换则忽略
     *
     * @param text 待处理的字符串
     * @return 返回已处理的字符串
     */
    public String resolvePlaceholders(String text) {
        if (StringUtil.isEmpty(text)) {
            return null;
        }
        return this.replacePlaceholders(text, this.resolveProperties);
    }

}
