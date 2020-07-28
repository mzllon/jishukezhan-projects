package com.jishukezhan.core.support;

import com.jishukezhan.core.lang.ArrayUtil;
import com.jishukezhan.core.lang.Preconditions;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayIterator<E> implements Iterator<E>, Iterable<E>, Serializable {

    private static final long serialVersionUID = 1990L;

    /**
     * 数组
     */
    private final E[] array;

    /**
     * 起始位置
     */
    private int startIndex;

    /**
     * 结束位置
     */
    private int endIndex;

    /**
     * 当前位置
     */
    private int index;

    /**
     * 构造
     *
     * @param array 数组
     * @throws IllegalArgumentException array对象不为数组抛出此异常
     * @throws NullPointerException     array对象为null
     */
    @SafeVarargs
    public ArrayIterator(final E... array) {
        this(0, array);
    }

    /**
     * 构造
     *
     * @param array      数组
     * @param startIndex 起始位置，当起始位置小于0或者大于结束位置，置为0。
     * @throws IllegalArgumentException array对象不为数组抛出此异常
     * @throws NullPointerException     array对象为null
     */
    @SafeVarargs
    public ArrayIterator(final int startIndex, final E... array) {
        this(startIndex, -1, array);
    }

    /**
     * 构造
     *
     * @param array      数组
     * @param startIndex 起始位置，当起始位置小于0或者大于结束位置，置为0。
     * @param endIndex   结束位置，当结束位置小于0或者大于数组长度，置为数组长度。
     * @throws IllegalArgumentException array对象不为数组抛出此异常
     * @throws NullPointerException     array对象为null
     */
    @SafeVarargs
    public ArrayIterator(final int startIndex, final int endIndex, final E... array) {
        this.endIndex = ArrayUtil.getLength(Preconditions.requireNonNull(array, "The array object must not be null"));
        if (endIndex > 0 && endIndex < this.endIndex) {
            this.endIndex = endIndex;
        }

        if (startIndex >= 0 && startIndex < this.endIndex) {
            this.startIndex = startIndex;
        }
        this.array = array;
        this.index = this.startIndex;
    }

    /**
     * 获得原始数组对象
     *
     * @return 原始数组对象
     */
    public E[] getArray() {
        return array;
    }

    /**
     * 重置数组位置
     */
    public void reset() {
        this.index = this.startIndex;
    }

    @Override
    public boolean hasNext() {
        return index < endIndex;
    }

    @Override
    public E next() {
        if (hasNext()) {
            return array[index++];
        }
        throw new NoSuchElementException();
    }

    /**
     * 不允许操作数组元素
     *
     * @throws UnsupportedOperationException always
     */
    @Override
    public void remove() {
        throw new UnsupportedOperationException("remove() method is not supported");
    }

    @Override
    public Iterator<E> iterator() {
        return this;
    }

}
