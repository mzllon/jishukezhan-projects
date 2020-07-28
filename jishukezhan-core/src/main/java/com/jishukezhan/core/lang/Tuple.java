package com.jishukezhan.core.lang;

import com.jishukezhan.core.support.ArrayIterator;
import com.jishukezhan.core.support.CloneSupport;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Consumer;

/**
 * 元组: 不可变数组类型,用于多值返回,多值可以支持每个元素值类型不同
 *
 * @author miles.tang
 */
public class Tuple extends CloneSupport<Tuple> implements Iterable<Object>, Serializable {

    private static final long serialVersionUID = 1990L;

    private final Object[] elements;

    public Tuple(Object... elements) {
        this.elements = elements;
    }

    /**
     * 获得所有元素
     *
     * @return 获得所有元素
     */
    public Object[] getElements() {
        return elements;
    }

    /**
     * 获取指定位置元素
     *
     * @param <T>   返回对象类型
     * @param index 位置
     * @return 元素
     */
    @SuppressWarnings("unchecked")
    public <T> T get(int index) {
        return (T) elements[index];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tuple tuple = (Tuple) o;
        return Arrays.equals(elements, tuple.elements);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(elements);
        return result;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Object> iterator() {
        return new ArrayIterator<>(elements);
    }

    /**
     * Performs the given action for each element of the {@code Iterable}
     * until all elements have been processed or the action throws an
     * exception.  Unless otherwise specified by the implementing class,
     * actions are performed in the order of iteration (if an iteration order
     * is specified).  Exceptions thrown by the action are relayed to the
     * caller.
     *
     * @param action The action to be performed for each element
     * @throws NullPointerException if the specified action is null
     * @implSpec <p>The default implementation behaves as if:
     * <pre>{@code
     *     for (T t : this)
     *         action.accept(t);
     * }</pre>
     * @since 1.8
     */
    @Override
    public void forEach(Consumer<? super Object> action) {
        Preconditions.requireNonNull(action);
        for (Object element : elements) {
            action.accept(element);
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(elements);
    }

}
