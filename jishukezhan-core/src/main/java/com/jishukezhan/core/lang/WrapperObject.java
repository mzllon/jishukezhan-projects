package com.jishukezhan.core.lang;

/**
 * 对象包装类，适用于匿名内部类、lambda表达式访问/设置外部基础变量（String,Integer,Long,...)
 *
 * @param <T> 泛型类
 * @author miles.tang on 2020-05-30
 */
public class WrapperObject<T> {

    private T obj;

    public WrapperObject() {

    }

    public WrapperObject(T obj) {
        this.obj = obj;
    }

    public T getObj() {

        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

}
