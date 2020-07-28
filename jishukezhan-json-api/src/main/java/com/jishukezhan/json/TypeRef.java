package com.jishukezhan.json;

import com.jishukezhan.core.utils.ClassUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Comparator;

/**
 * <p>
 * 该类主要用于传递泛型的类型，避免在运行时期找不到泛型的实际类型。
 * 具体用法：由于本类是一个抽象类，所以需要子类去实现。比如下面的代码实现String的泛型传递。
 * </p>
 * <pre>
 *  TypeRef ref = new TypeRef&lt;List&lt;String&gt;&gt;() { };
 * </pre>
 *
 * @author miles.tang
 */
public abstract class TypeRef<T> implements Comparator<T> {

    final Type type;
    final Class<? super T> rawType;

    @SuppressWarnings("unchecked")
    protected TypeRef() {
        Type superClass = getClass().getGenericSuperclass();
        if (superClass instanceof Class<?>)
            throw new IllegalArgumentException("Internal error: TypeReference constructed without actual type information");
        type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
        this.rawType = (Class<? super T>) ClassUtil.getRawType(type);
    }

    public Type getType() {
        return type;
    }

    public Class<? super T> getRawType() {
        return rawType;
    }

    @Override
    public int compare(T o1, T o2) {
        return 0;
    }

}
