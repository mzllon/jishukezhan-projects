package com.jishukezhan.core.bean;

import com.jishukezhan.annotation.Nullable;
import com.jishukezhan.core.exceptions.BeanException;
import com.jishukezhan.core.lang.Preconditions;
import com.jishukezhan.core.utils.ClassUtil;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Java Bean工具类
 *
 * @author miles.tang
 */
public class BeanUtil {

    /**
     * 判断是不是普通的JavaBean，判断方法为：
     * <pre>
     *     1. 判断属性是不是有getter/setter方法
     *     2. 判断字段是不是public修饰符
     * </pre>
     *
     * @param tClass
     * @return
     */
    public static boolean isBeanType(@Nullable Class<?> tClass) {
        if (!ClassUtil.isNormalClass(tClass)) {
            return false;
        }

        // 判断你是不是有public修饰符
        boolean result = Arrays.stream(tClass.getDeclaredFields())
                .anyMatch(field -> Modifier.isPublic(field.getModifiers()) && !Modifier.isStatic(field.getModifiers()));
        if (result) {
            return true;
        }

        Method[] methods = tClass.getDeclaredMethods();
        // 是不是有setter
        result = Arrays.stream(methods).anyMatch(method -> method.getName().startsWith("set"));
        if (result) {
            // 必须同时还要有getter/is
//            return Arrays.stream(methods)
//                    .anyMatch(method -> {
//                        System.out.println("method = " + method);
//                        System.out.println("method.getName() = " + method.getName());
//                        return method.getName().startsWith("get") || method.getName().startsWith("is");
//                    });

            return Arrays.stream(methods).anyMatch(method -> method.getName().startsWith("get") || method.getName().startsWith("is"));
        }

        return false;
    }

    /**
     * 获得Bean字段描述集合,获得的结果会缓存在{@link BeanIntrospectorCache}中
     *
     * @param beanClass Bean类
     * @return 字段描述数组
     * @throws BeanException 获取属性异常
     */
    public static List<PropertyDescriptor> getPropertyDescriptorList(Class<?> beanClass) throws BeanException {
        Preconditions.requireNonNull(beanClass, "beanClass == null");
        return BeanIntrospectorCache.getInstance().getPropertyDescriptors(beanClass);
    }

    /**
     * 获得字段名和字段描述Map,获得的结果会缓存在{@link BeanIntrospectorCache}中
     *
     * @param beanClass Bean类
     * @return 字段名和字段描述Map
     * @throws BeanException 获取属性异常
     */
    public static Map<String, PropertyDescriptor> getPropertyDescriptorMap(Class<?> beanClass) throws BeanException {
        List<PropertyDescriptor> list = getPropertyDescriptorList(beanClass);
        final Map<String, PropertyDescriptor> map = new HashMap<>(list.size());
        for (PropertyDescriptor propertyDescriptor : list) {
            map.put(propertyDescriptor.getName(), propertyDescriptor);
        }
        return map;
    }

    /**
     * 根据属性名称获取对应属性对象
     *
     * @param beanClass    类型
     * @param propertyName 属性名
     * @return {@linkplain PropertyDescriptor}
     */
    public static PropertyDescriptor getPropertyDescriptor(Class<?> beanClass, String propertyName) {
        return getPropertyDescriptorMap(beanClass).get(Preconditions.requireNotEmpty(propertyName, "propertyName is null or empty"));
    }

    /**
     * 获取属性值,通过{@linkplain PropertyDescriptor}获取，必须要求要求实现{@code getter}方法
     *
     * @param bean         bean实例
     * @param propertyName 属性名
     * @param <T>          属性值类型
     * @return 属性值, 如果属性找不到或未实现{@code getter}方法返回{@code null}
     */
    @SuppressWarnings("unchecked")
    public static <T> T getPropertyValue(Object bean, String propertyName) {
        Preconditions.requireNonNull(bean, "bean == null");
        PropertyDescriptor pd = getPropertyDescriptor(bean.getClass(), propertyName);
        if (pd == null) {
            return null;
        }
        try {
            Method readMethod = pd.getReadMethod();
            if (readMethod == null) {
                return null;
            }
            return (T) readMethod.invoke(bean);
        } catch (ReflectiveOperationException e) {
            throw new BeanException(e);
        }
    }

    /**
     * 对象转Map,不忽略值为空的字段
     *
     * @param bean bean对象
     * @return Map
     */
    public static Map<String, Object> beanToMap(Object bean) {
        return beanToMap(bean, false);
    }

    /**
     * 对象转Map
     *
     * @param bean          bean对象
     * @param ignoreNullVal 是否忽略值为空的字段
     * @return Map
     */
    public static Map<String, Object> beanToMap(Object bean, boolean ignoreNullVal) {
        Map<String, Object> targetMap = new LinkedHashMap<>();
        beanToMap(bean, targetMap, ignoreNullVal);
        return targetMap;
    }

    /**
     * 对象转Map
     *
     * @param bean          bean对象
     * @param targetMap     目标的Map
     * @param ignoreNullVal 是否忽略值为空的字段
     */
    public static void beanToMap(Object bean, Map<String, Object> targetMap, boolean ignoreNullVal) {
        if (bean == null || targetMap == null) {
            return;
        }
        Method readMethod;
        Object value;
        for (PropertyDescriptor propertyDescriptor : getPropertyDescriptorList(bean.getClass())) {
            readMethod = propertyDescriptor.getReadMethod();
            if (readMethod != null) {
                try {
                    value = readMethod.invoke(bean);
                    if (ignoreNullVal && value != null) {
                        targetMap.put(propertyDescriptor.getName(), value);
                    } else {
                        targetMap.put(propertyDescriptor.getName(), value);
                    }
                } catch (ReflectiveOperationException e) {
                    throw new BeanException(e);
                }
            }
        }

    }

    /**
     * 将Javabean对象转为Map,其中值的类型为{@code String}
     *
     * @param bean       对象
     * @param ignoreNull 是否忽略空值
     * @return Map对象
     */
    public static Map<String, String> toMapAsValueString(Object bean, boolean ignoreNull) {
        Map<String, Object> propertiesMap = beanToMap(bean);
        Map<String, String> resultMap = new HashMap<>(propertiesMap.size());

        Object _value;
        for (String propertyName : propertiesMap.keySet()) {
            _value = propertiesMap.get(propertyName);
            if (_value == null) {
                if (!ignoreNull) {
                    resultMap.put(propertyName, null);
                }
            } else {
                if (_value instanceof Number) {
                    Number number = (Number) _value;
                    resultMap.put(propertyName, number.toString());
                } else if (_value instanceof String) {
                    resultMap.put(propertyName, (String) _value);
                } else if (_value instanceof Date) {
                    resultMap.put(propertyName, String.valueOf(((Date) _value).getTime()));
                } else {
                    resultMap.put(propertyName, _value.toString());
                }
            }
        }
        return resultMap;
    }

    /**
     * Javabean的属性值拷贝，即对象的拷贝
     *
     * @param source 原始对象
     * @param target 目标对象
     */
    public static void copyProperties(Object source, Object target) {
        copyProperties(source, target, (String) null);
    }

    /**
     * Javabean的属性值拷贝，即对象的拷贝
     *
     * @param source           原始对象
     * @param target           目标对象
     * @param ignoreProperties 过滤的属性名
     */
    public static void copyProperties(Object source, Object target, String... ignoreProperties) {
        Preconditions.requireNonNull(source, "source == null");
        Preconditions.requireNonNull(target, "target == null");
        List<PropertyDescriptor> targetPDList = getPropertyDescriptorList(target.getClass());
        List<String> ignoreList = (ignoreProperties == null ? null : Arrays.asList(ignoreProperties));

        for (PropertyDescriptor targetPD : targetPDList) {
            Method writeMethod = targetPD.getWriteMethod();
            if (writeMethod != null && (ignoreList == null || !ignoreList.contains(targetPD.getName()))) {
                PropertyDescriptor sourcePD = getPropertyDescriptor(source.getClass(), targetPD.getName());
                if (sourcePD != null) {
                    Method readMethod = sourcePD.getReadMethod();
                    if (readMethod != null && ClassUtil.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                        if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                            readMethod.setAccessible(true);
                        }
                        try {
                            Object value = readMethod.invoke(source);
                            if (Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                writeMethod.setAccessible(true);
                            }
                            writeMethod.invoke(target, value);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            throw new BeanException(String.format("Could not copy property '%s' from source to target", targetPD.getName()), e);
                        }
                    }
                }
            }
        }

    }

}
