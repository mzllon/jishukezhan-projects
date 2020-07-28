package com.jishukezhan.core.bean;

import com.jishukezhan.core.exceptions.BeanException;
import com.jishukezhan.core.lang.Preconditions;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class BeanIntrospectorCache {

    private static class Holder {
        static BeanIntrospectorCache INSTANCE = new BeanIntrospectorCache();
    }

    public static BeanIntrospectorCache getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * 缓存已经内省过的Class
     */
    private final WeakHashMap<Class<?>, List<PropertyDescriptor>> descriptorsCache = new WeakHashMap<>();

    private BeanIntrospectorCache() {

    }

    /**
     * 获取Javabean的属性描述列表
     *
     * @param beanClass 对象内容
     * @return {@code PropertyDescriptor}数组
     */
    public List<PropertyDescriptor> getPropertyDescriptors(Class<?> beanClass) {
        Preconditions.requireNonNull(beanClass, "BeanClass == null");
        List<PropertyDescriptor> propertyDescriptorList;
        synchronized (descriptorsCache) {
            propertyDescriptorList = descriptorsCache.get(beanClass);
        }
        if (propertyDescriptorList == null) {
            try {
                BeanInfo beanInfo = Introspector.getBeanInfo(beanClass);
                PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
                propertyDescriptorList = new ArrayList<>(propertyDescriptors.length);
                for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                    if (!"class".equals(propertyDescriptor.getName())) {
                        propertyDescriptorList.add(propertyDescriptor);
                    }
                }
                synchronized (descriptorsCache) {
                    descriptorsCache.put(beanClass, propertyDescriptorList);
                }
            } catch (IntrospectionException e) {
                throw new BeanException(String.format("Failed to obtain BeanInfo for class [%s]", beanClass.getName()), e);
            }
        }
        return propertyDescriptorList;
    }

    /**
     * 根据属性名称获取对应属性对象
     *
     * @param beanClass    类型
     * @param propertyName 属性名
     * @return {@linkplain PropertyDescriptor}
     */
    public PropertyDescriptor getPropertyDescriptor(Class<?> beanClass, String propertyName) {
        List<PropertyDescriptor> propertyDescriptors = this.getPropertyDescriptors(beanClass);
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            if (propertyDescriptor.getName().equals(propertyName)) {
                return propertyDescriptor;
            }
        }
        return null;
    }

    /**
     * 清理缓存
     */
    public void clearDescriptors() {
        descriptorsCache.clear();
    }

}
