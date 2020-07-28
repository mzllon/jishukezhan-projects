package com.jishukezhan.core.lang;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ArrayUtilTest {

    @Test
    public void isArray() {
        String str = null;
        assertFalse(ArrayUtil.isArray(str));

        str = "Array";
        assertFalse(ArrayUtil.isArray(str));

        int num = 0;
        assertFalse(ArrayUtil.isArray(num));

        int[] array = null;
        assertFalse(ArrayUtil.isArray(array));

        array = new int[0];
        assertTrue(ArrayUtil.isArray(array));

        array = new int[]{1, 2};
        assertTrue(ArrayUtil.isArray(array));

    }

    @Test
    public void isPrimitiveArray() {
        int[] intArray = new int[0];
        assertTrue(ArrayUtil.isPrimitiveArray(intArray));

        short[] shortArray = new short[0];
        assertTrue(ArrayUtil.isPrimitiveArray(shortArray));

        byte[] byteArray = new byte[0];
        assertTrue(ArrayUtil.isPrimitiveArray(byteArray));

        String[] strArray = new String[0];
        assertFalse(ArrayUtil.isPrimitiveArray(strArray));

        Object[] objArray = new Object[]{1, 2, 3};
        assertFalse(ArrayUtil.isPrimitiveArray(objArray));

    }

    @Test
    public void isEmpty() {
        char[] ca = null;
        assertTrue(ArrayUtil.isEmpty(ca));

        ca = new char[0];
        assertTrue(ArrayUtil.isEmpty(ca));

        ca = new char[]{' '};
        assertFalse(ArrayUtil.isEmpty(ca));

        boolean[] ba = null;
        assertTrue(ArrayUtil.isEmpty(ba));

        ba = new boolean[0];
        assertTrue(ArrayUtil.isEmpty(ba));

        ba = new boolean[]{true};
        assertFalse(ArrayUtil.isEmpty(ba));

        byte[] byteA = null;
        assertTrue(ArrayUtil.isEmpty(byteA));

        byteA = new byte[0];
        assertTrue(ArrayUtil.isEmpty(byteA));

        byteA = new byte[]{'a'};
        assertFalse(ArrayUtil.isEmpty(byteA));

        short[] shortA = null;
        assertTrue(ArrayUtil.isEmpty(shortA));

        shortA = new short[0];
        assertTrue(ArrayUtil.isEmpty(shortA));

        shortA = new short[]{10};
        assertFalse(ArrayUtil.isEmpty(shortA));

        int[] intArray = null;
        assertTrue(ArrayUtil.isEmpty(intArray));

        intArray = new int[0];
        assertTrue(ArrayUtil.isEmpty(intArray));

        intArray = new int[]{1, 23};
        assertFalse(ArrayUtil.isEmpty(intArray));

        long[] longA = null;
        assertTrue(ArrayUtil.isEmpty(longA));

        longA = new long[0];
        assertTrue(ArrayUtil.isEmpty(longA));

        longA = new long[]{11111};
        assertFalse(ArrayUtil.isEmpty(longA));

        float[] floatA = null;
        assertTrue(ArrayUtil.isEmpty(floatA));

        floatA = new float[0];
        assertTrue(ArrayUtil.isEmpty(floatA));

        floatA = new float[]{0.0f};
        assertFalse(ArrayUtil.isEmpty(floatA));

        double[] doubleA = null;
        assertTrue(ArrayUtil.isEmpty(doubleA));

        doubleA = new double[0];
        assertTrue(ArrayUtil.isEmpty(doubleA));

        doubleA = new double[]{0.0};
        assertFalse(ArrayUtil.isEmpty(doubleA));

        String[] strArray = null;
        assertTrue(ArrayUtil.isEmpty(strArray));

        strArray = new String[0];
        assertTrue(ArrayUtil.isEmpty(strArray));

        strArray = new String[]{""};
        assertFalse(ArrayUtil.isEmpty(strArray));

        strArray = new String[]{null};
        assertFalse(ArrayUtil.isEmpty(strArray));

    }

    @Test
    public void isNotEmpty() {
        char[] ca = null;
        assertFalse(ArrayUtil.isNotEmpty(ca));

        ca = new char[0];
        assertFalse(ArrayUtil.isNotEmpty(ca));

        ca = new char[]{' '};
        assertTrue(ArrayUtil.isNotEmpty(ca));

        boolean[] ba = null;
        assertFalse(ArrayUtil.isNotEmpty(ba));

        ba = new boolean[0];
        assertFalse(ArrayUtil.isNotEmpty(ba));

        ba = new boolean[]{true};
        assertTrue(ArrayUtil.isNotEmpty(ba));

        byte[] byteA = null;
        assertFalse(ArrayUtil.isNotEmpty(byteA));

        byteA = new byte[0];
        assertFalse(ArrayUtil.isNotEmpty(byteA));

        byteA = new byte[]{'a'};
        assertTrue(ArrayUtil.isNotEmpty(byteA));

        short[] shortA = null;
        assertFalse(ArrayUtil.isNotEmpty(shortA));

        shortA = new short[0];
        assertFalse(ArrayUtil.isNotEmpty(shortA));

        shortA = new short[]{10};
        assertTrue(ArrayUtil.isNotEmpty(shortA));

        int[] intArray = null;
        assertFalse(ArrayUtil.isNotEmpty(intArray));

        intArray = new int[0];
        assertFalse(ArrayUtil.isNotEmpty(intArray));

        intArray = new int[]{1, 23};
        assertTrue(ArrayUtil.isNotEmpty(intArray));

        long[] longA = null;
        assertFalse(ArrayUtil.isNotEmpty(longA));

        longA = new long[0];
        assertFalse(ArrayUtil.isNotEmpty(longA));

        longA = new long[]{11111};
        assertTrue(ArrayUtil.isNotEmpty(longA));

        float[] floatA = null;
        assertFalse(ArrayUtil.isNotEmpty(floatA));

        floatA = new float[0];
        assertFalse(ArrayUtil.isNotEmpty(floatA));

        floatA = new float[]{0.0f};
        assertTrue(ArrayUtil.isNotEmpty(floatA));

        double[] doubleA = null;
        assertFalse(ArrayUtil.isNotEmpty(doubleA));

        doubleA = new double[0];
        assertFalse(ArrayUtil.isNotEmpty(doubleA));

        doubleA = new double[]{0.0};
        assertTrue(ArrayUtil.isNotEmpty(doubleA));

        String[] strArray = null;
        assertFalse(ArrayUtil.isNotEmpty(strArray));

        strArray = new String[0];
        assertFalse(ArrayUtil.isNotEmpty(strArray));

        strArray = new String[]{""};
        assertTrue(ArrayUtil.isNotEmpty(strArray));

        strArray = new String[]{null};
        assertTrue(ArrayUtil.isNotEmpty(strArray));

    }

    @Test
    public void join() {
        char[] ca = null;
        assertNull(ArrayUtil.join(ca));

        ca = new char[0];
        assertEquals(ArrayUtil.join(ca), "");

        ca = new char[]{' '};
        assertEquals(ArrayUtil.join(ca), " ");

        boolean[] ba = null;
        assertNull(ArrayUtil.join(ba));

        ba = new boolean[0];
        assertEquals(ArrayUtil.join(ba), "");

        ba = new boolean[]{true};
        assertEquals(ArrayUtil.join(ba), "true");

        byte[] byteA = null;
        assertNull(ArrayUtil.join(byteA));

        byteA = new byte[0];
        assertEquals(ArrayUtil.join(byteA), "");

        byteA = new byte[]{'a'};
        assertEquals(ArrayUtil.join(byteA), "97");

        short[] shortA = null;
        assertNull(ArrayUtil.join(shortA));

        shortA = new short[0];
        assertEquals(ArrayUtil.join(shortA), "");

        shortA = new short[]{10};
        assertEquals(ArrayUtil.join(shortA), "10");

        int[] intArray = null;
        assertNull(ArrayUtil.join(intArray));

        intArray = new int[0];
        assertEquals(ArrayUtil.join(intArray), "");

        intArray = new int[]{1, 23};
        assertEquals(ArrayUtil.join(intArray), "1,23");

        long[] longA = null;
        assertNull(ArrayUtil.join(longA));

        longA = new long[0];
        assertEquals(ArrayUtil.join(longA), "");

        longA = new long[]{11111};
        assertEquals(ArrayUtil.join(longA), "11111");

        float[] floatA = null;
        assertNull(ArrayUtil.join(floatA));

        floatA = new float[0];
        assertEquals(ArrayUtil.join(floatA), "");

        floatA = new float[]{0.0f};
        assertEquals(ArrayUtil.join(floatA), "0.0");

        double[] doubleA = null;
        assertNull(ArrayUtil.join(doubleA));

        doubleA = new double[0];
        assertEquals(ArrayUtil.join(doubleA), "");

        doubleA = new double[]{0.0};
        assertEquals(ArrayUtil.join(doubleA), "0.0");

        String[] strArray = null;
        assertNull(ArrayUtil.join(strArray));

        strArray = new String[0];
        assertEquals(ArrayUtil.join(strArray), "");

        strArray = new String[]{""};
        assertEquals(ArrayUtil.join(strArray), "");

        strArray = new String[]{null};
        assertEquals(ArrayUtil.join(strArray), "null");

    }

    @Test
    public void toPrimitive() {
        Character[] ca = null;
        assertNull(ArrayUtil.toPrimitive(ca));

        ca = new Character[0];
        assertEquals(ArrayUtil.toPrimitive(ca), new char[0]);

        ca = new Character[]{' '};
        assertEquals(ArrayUtil.toPrimitive(ca), new char[]{' '});

        Boolean[] ba = null;
        assertNull(ArrayUtil.toPrimitive(ba));

        ba = new Boolean[0];
        assertEquals(ArrayUtil.toPrimitive(ba), new boolean[0]);

        ba = new Boolean[]{true};
        assertEquals(ArrayUtil.toPrimitive(ba), new boolean[]{true});

        Byte[] byteA = null;
        assertNull(ArrayUtil.toPrimitive(byteA));

        byteA = new Byte[0];
        assertEquals(ArrayUtil.toPrimitive(byteA), new byte[0]);

        byteA = new Byte[]{'a'};
        assertEquals(ArrayUtil.toPrimitive(byteA), new byte[]{97});

        Short[] shortA = null;
        assertNull(ArrayUtil.toPrimitive(shortA));

        shortA = new Short[0];
        assertEquals(ArrayUtil.toPrimitive(shortA), new short[0]);

        shortA = new Short[]{10};
        assertEquals(ArrayUtil.toPrimitive(shortA), new short[]{10});

        Integer[] intArray = null;
        assertNull(ArrayUtil.toPrimitive(intArray));

        intArray = new Integer[0];
        assertEquals(ArrayUtil.toPrimitive(intArray), new int[0]);

        intArray = new Integer[]{1, 23};
        assertEquals(ArrayUtil.toPrimitive(intArray), new int[]{1, 23});

        Long[] longA = null;
        assertNull(ArrayUtil.toPrimitive(longA));

        longA = new Long[0];
        assertEquals(ArrayUtil.toPrimitive(longA), new long[0]);

        longA = new Long[]{11111L};
        assertEquals(ArrayUtil.toPrimitive(longA), new long[]{11111});

        Float[] floatA = null;
        assertNull(ArrayUtil.toPrimitive(floatA));

        floatA = new Float[0];
        assertEquals(ArrayUtil.toPrimitive(floatA), new float[0]);

        floatA = new Float[]{0.0f};
        assertEquals(ArrayUtil.toPrimitive(floatA), new float[]{0.0f});

        Double[] doubleA = null;
        assertNull(ArrayUtil.toPrimitive(doubleA));

        doubleA = new Double[0];
        assertEquals(ArrayUtil.toPrimitive(doubleA), new double[0]);

        doubleA = new Double[]{0.0};
        assertEquals(ArrayUtil.toPrimitive(doubleA), new double[]{0.0});

    }

    @Test
    public void toObject() {
        char[] ca = null;
        assertNull(ArrayUtil.toObject(ca));

        ca = new char[0];
        assertEquals(ArrayUtil.toObject(ca), new Character[0]);

        ca = new char[]{' '};
        assertEquals(ArrayUtil.toObject(ca), new Character[]{' '});

        boolean[] ba = null;
        assertNull(ArrayUtil.toObject(ba));

        ba = new boolean[0];
        assertEquals(ArrayUtil.toObject(ba), new Boolean[0]);

        ba = new boolean[]{true};
        assertEquals(ArrayUtil.toObject(ba), new Boolean[]{true});

        byte[] byteA = null;
        assertNull(ArrayUtil.toObject(byteA));

        byteA = new byte[0];
        assertEquals(ArrayUtil.toObject(byteA), new Byte[0]);

        byteA = new byte[]{'a'};
        assertEquals(ArrayUtil.toObject(byteA), new Byte[]{97});

        short[] shortA = null;
        assertNull(ArrayUtil.toObject(shortA));

        shortA = new short[0];
        assertEquals(ArrayUtil.toObject(shortA), new Short[0]);

        shortA = new short[]{10};
        assertEquals(ArrayUtil.toObject(shortA), new Short[]{10});

        int[] intArray = null;
        assertNull(ArrayUtil.toObject(intArray));

        intArray = new int[0];
        assertEquals(ArrayUtil.toObject(intArray), new Integer[0]);

        intArray = new int[]{1, 23};
        assertEquals(ArrayUtil.toObject(intArray), new Integer[]{1, 23});

        long[] longA = null;
        assertNull(ArrayUtil.toObject(longA));

        longA = new long[0];
        assertEquals(ArrayUtil.toObject(longA), new Long[0]);

        longA = new long[]{11111};
        assertEquals(ArrayUtil.toObject(longA), new Long[]{11111L});

        float[] floatA = null;
        assertNull(ArrayUtil.toObject(floatA));

        floatA = new float[0];
        assertEquals(ArrayUtil.toObject(floatA), new Float[0]);

        floatA = new float[]{0.0f};
        assertEquals(ArrayUtil.toObject(floatA), new Float[]{0.0f});

        double[] doubleA = null;
        assertNull(ArrayUtil.toObject(doubleA));

        doubleA = new double[0];
        assertEquals(ArrayUtil.toObject(doubleA), new Double[0]);

        doubleA = new double[]{0.0};
        assertEquals(ArrayUtil.toObject(doubleA), new Double[]{0.0});

    }

    @Test
    public void getComponentType() {
        assertNull(ArrayUtil.getComponentType((Object) null));
        int[] intArray = {1, 2, 3};
        assertEquals(ArrayUtil.getComponentType(intArray), int.class);

        assertEquals(ArrayUtil.getComponentType(String[].class), String.class);
        assertNotEquals(ArrayUtil.getComponentType(intArray), Integer.class);
    }

    @Test
    public void containsElement() {
        int[] array = null;
        assertFalse(ArrayUtil.containsElement(array, 1));

        array = new int[0];
        assertFalse(ArrayUtil.containsElement(array, 1));

        array = new int[]{2, 3, 4};
        assertFalse(ArrayUtil.containsElement(array, 1));

        array = new int[]{1, 2, 1};
        assertTrue(ArrayUtil.containsElement(array, 1));

    }

    @Test
    public void containsElement2() {
        String[] array = null;
        assertFalse(ArrayUtil.containsElement(array, null));

        assertFalse(ArrayUtil.containsElement(array, "1"));

        array = new String[0];
        assertFalse(ArrayUtil.containsElement(array, "1"));

        array = new String[]{"jdk", "jre", "jvm"};
        assertFalse(ArrayUtil.containsElement(array, "1"));

        assertTrue(ArrayUtil.containsElement(array, "jdk"));

        array = new String[]{"1", "2", "3"};
        assertTrue(ArrayUtil.containsElement(array, "1"));

        assertFalse(ArrayUtil.containsElement(array, 1));


    }

    @Test
    public void reverse1() {
        String[] array = null;
        assertNull(ArrayUtil.reverse(array));

        array = new String[]{};
        assertEquals(ArrayUtil.reverse(array), new String[]{});

        array = new String[]{"1", "2"};
        String[] actualArray = ArrayUtil.reverse(array);

        assertEquals(actualArray, new String[]{"2", "1"});

    }

    @Test
    public void reverse2() {
        String[] array = {"1", "2", "3", "4"};
        String[] expectedArray = {"3", "2"};
        String[] actualArray = ArrayUtil.reverse(array, 1, 3);
        assertEquals(actualArray, expectedArray);
    }

}
