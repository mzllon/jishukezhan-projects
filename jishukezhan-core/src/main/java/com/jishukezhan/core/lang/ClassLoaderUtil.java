package com.jishukezhan.core.lang;

public class ClassLoaderUtil {

    /**
     * 返回一个默认的类加载器：首先会从当前线程获取类加载，如果获取失败则获取当前类的类加载器。
     *
     * @return 返回类类加载器
     * @see Thread#getContextClassLoader()
     */
    public static ClassLoader getDefaultClassLoader() {
        ClassLoader classLoader = null;
        try {
            classLoader = getContextClassLoader();
        } catch (Throwable e) {
            //无法从当前线程获取到类加载器
        }
        if (classLoader == null) {
            //从当前类的加载器
            classLoader = ClassLoaderUtil.class.getClassLoader();
            if (classLoader == null) {
                classLoader = ClassLoader.getSystemClassLoader();
            }
        }
        return classLoader;
    }

    /**
     * 获取当前线程的{@link ClassLoader}
     *
     * @return 当前线程类加载器
     * @see Thread#getContextClassLoader()
     */
    public static ClassLoader getContextClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

}
