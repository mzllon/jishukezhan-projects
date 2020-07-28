package com.jishukezhan.core.lang;

import org.testng.annotations.Test;

import java.util.*;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

public class StringUtilTest {

    @Test
    public void isEmpty() {
        String str = null;
        assertTrue(StringUtil.isEmpty(str));

        str = "";
        assertTrue(StringUtil.isEmpty(str));

        str = " ";
        assertFalse(StringUtil.isEmpty(str));

        str = "\t";
        assertFalse(StringUtil.isEmpty(str));
    }

    @Test
    public void isAnyEmpty() {
        String str = null;
        assertTrue(StringUtil.isAnyEmpty(str));

        String[] array = null;
        assertTrue(StringUtil.isAnyEmpty(array));

        array = new String[]{};
        assertTrue(StringUtil.isAnyEmpty(array));

        array = new String[]{null, null, null};
        assertTrue(StringUtil.isAnyEmpty(array));

        array = new String[]{null, "", null};
        assertTrue(StringUtil.isAnyEmpty(array));

        array = new String[]{"", "\t", ""};
        assertTrue(StringUtil.isAnyEmpty(array));

        array = new String[]{"", "", " "};
        assertTrue(StringUtil.isAnyEmpty(array));

        array = new String[]{"hello", "\t", " "};
        assertFalse(StringUtil.isAnyEmpty(array));
    }

    @Test
    public void isAllEmpty() {
        String str = null;
        assertTrue(StringUtil.isAllEmpty(str));

        String[] array = null;
        assertTrue(StringUtil.isAllEmpty(array));

        array = new String[]{};
        assertTrue(StringUtil.isAllEmpty(array));

        array = new String[]{null, null, null};
        assertTrue(StringUtil.isAllEmpty(array));

        array = new String[]{null, "", null};
        assertTrue(StringUtil.isAllEmpty(array));

        array = new String[]{"", "\t", ""};
        assertFalse(StringUtil.isAllEmpty(array));

        array = new String[]{"", "", " "};
        assertFalse(StringUtil.isAllEmpty(array));

        array = new String[]{"hello", "\t", " "};
        assertFalse(StringUtil.isAllEmpty(array));
    }

    @Test
    public void hasLength() {
        String str = null;
        assertFalse(StringUtil.hasLength(str));

        str = "";
        assertFalse(StringUtil.hasLength(str));

        str = " ";
        assertTrue(StringUtil.hasLength(str));

        str = "\t";
        assertTrue(StringUtil.hasLength(str));

        str = "mzlion";
        assertTrue(StringUtil.hasLength(str));
    }

    @Test
    public void haveAnyLength() {
        String str = null;
        assertFalse(StringUtil.haveAnyLength(str));

        String[] array = null;
        assertFalse(StringUtil.haveAnyLength(array));

        array = new String[]{};
        assertFalse(StringUtil.haveAnyLength(array));

        array = new String[]{null, null, null};
        assertFalse(StringUtil.haveAnyLength(array));

        array = new String[]{null, "", null};
        assertFalse(StringUtil.haveAnyLength(array));

        array = new String[]{"", "\t", ""};
        assertTrue(StringUtil.haveAnyLength(array));

        array = new String[]{"", "", " "};
        assertTrue(StringUtil.haveAnyLength(array));

        array = new String[]{"hello", "\t", " "};
        assertTrue(StringUtil.haveAnyLength(array));
    }

    @Test
    public void haveAllLength1() {
        String str = null;
        assertFalse(StringUtil.haveAllLength(str));

        String[] array = null;
        assertFalse(StringUtil.haveAllLength(array));

        array = new String[]{};
        assertFalse(StringUtil.haveAllLength(array));

        array = new String[]{null, null, null};
        assertFalse(StringUtil.haveAllLength(array));

        array = new String[]{null, "", null};
        assertFalse(StringUtil.haveAllLength(array));

        array = new String[]{"", "\t", ""};
        assertFalse(StringUtil.haveAllLength(array));

        array = new String[]{"", "", " "};
        assertFalse(StringUtil.haveAllLength(array));

        array = new String[]{"hello", "\t", " "};
        assertTrue(StringUtil.haveAllLength(array));
    }

    @Test
    public void haveAnyLength2() {
        List<String> list = null;
        assertFalse(StringUtil.haveAnyLength(list));

        list = Collections.emptyList();
        assertFalse(StringUtil.haveAnyLength(list));

        list = new ArrayList<>();
        assertFalse(StringUtil.haveAnyLength(list));

        list = new ArrayList<>();
        list.add(null);
        assertFalse(StringUtil.haveAnyLength(list));

        list.add(null);
        assertFalse(StringUtil.haveAnyLength(list));

        list.add("");
        assertFalse(StringUtil.haveAnyLength(list));

        list.add(" ");
        assertTrue(StringUtil.haveAnyLength(list));

        list = new ArrayList<>();
        list.add("\t");
        assertTrue(StringUtil.haveAnyLength(list));
    }

    @Test
    public void haveAllLength2() {
        List<String> list = null;
        assertFalse(StringUtil.haveAllLength(list));

        list = Collections.emptyList();
        assertFalse(StringUtil.haveAllLength(list));

        list = new ArrayList<>();
        assertFalse(StringUtil.haveAllLength(list));

        list = new ArrayList<>();
        list.add(null);
        assertFalse(StringUtil.haveAllLength(list));

        list.add(null);
        assertFalse(StringUtil.haveAllLength(list));

        list.add("");
        assertFalse(StringUtil.haveAllLength(list));

        list.add(" ");
        assertFalse(StringUtil.haveAllLength(list));

        list = new ArrayList<>();
        list.add("\t");
        list.add(" ");
        assertTrue(StringUtil.haveAllLength(list));
    }

    @Test
    public void hasText() {
        String str = null;
        assertFalse(StringUtil.hasText(str));

        str = "";
        assertFalse(StringUtil.hasText(str));

        str = " ";
        assertFalse(StringUtil.hasText(str));

        str = "\t";
        assertFalse(StringUtil.hasText(str));

        str = "\t ";
        assertFalse(StringUtil.hasText(str));

        str = "\tjava ";
        assertTrue(StringUtil.hasText(str));

        str = "mzlion";
        assertTrue(StringUtil.hasText(str));
    }

    @Test
    public void containsWhitespace() {
        String str = null;
        assertFalse(StringUtil.containsWhitespace(str));

        str = "";
        assertFalse(StringUtil.containsWhitespace(str));

        str = " ";
        assertTrue(StringUtil.containsWhitespace(str));

        str = "abc d";
        assertTrue(StringUtil.containsWhitespace(str));

        str = "abc\td";
        assertTrue(StringUtil.containsWhitespace(str));

        str = "Str";
        assertFalse(StringUtil.containsWhitespace(str));
    }

    @Test
    public void trim() {
        String str = null;
        assertNull(StringUtil.trim(str));

        str = "";
        assertEquals(StringUtil.trim(str), "");

        str = "abcd";
        assertEquals(StringUtil.trim(str), "abcd");

        str = " abc ";
        assertEquals(StringUtil.trim(str), "abc");

        str = "\t\tab ";
        assertEquals(StringUtil.trim(str), "ab");

        str = " a b c ";
        assertEquals(StringUtil.trim(str), "a b c");
    }

    @Test
    public void trimLeft() {
        String str = null;
        assertNull(StringUtil.trimLeft(str));

        str = "";
        assertEquals(StringUtil.trimLeft(str), "");

        str = "abcd";
        assertEquals(StringUtil.trimLeft(str), "abcd");

        str = " abc ";
        assertEquals(StringUtil.trimLeft(str), "abc ");

        str = "\t\tab ";
        assertEquals(StringUtil.trimLeft(str), "ab ");

        str = " a b c ";
        assertEquals(StringUtil.trimLeft(str), "a b c ");
    }

    @Test
    public void trimRight() {
        String str = null;
        assertNull(StringUtil.trimRight(str));

        str = "";
        assertEquals(StringUtil.trimRight(str), "");

        str = "abcd";
        assertEquals(StringUtil.trimRight(str), "abcd");

        str = " abc ";
        assertEquals(StringUtil.trimRight(str), " abc");

        str = "\t\tab ";
        assertEquals(StringUtil.trimRight(str), "\t\tab");

        str = " a b c ";
        assertEquals(StringUtil.trimRight(str), " a b c");

        str = " a b c \t\t";
        assertEquals(StringUtil.trimRight(str), " a b c");
    }

    @Test
    public void trim1() {
        String[] strs = null;
        assertNull(StringUtil.trim(strs));

        strs = new String[]{};
        assertEquals(strs, new String[]{});

        strs = new String[]{null, null, null};
        String[] trimArray = StringUtil.trim(strs);
        assertEquals(trimArray, strs);

        strs = new String[]{null, "", null};
        trimArray = StringUtil.trim(strs);
        assertEquals(trimArray, strs);

        strs = new String[]{null, "", null};
        trimArray = StringUtil.trim(strs);
        assertEquals(trimArray, strs);

        strs = new String[]{" ", "\t"};
        assertEquals(StringUtil.trim(strs), new String[]{"", ""});

        strs = new String[]{" abc", "edf\t"};
        assertEquals(StringUtil.trim(strs), new String[]{"abc", "edf"});

        strs = new String[]{" java ", "\ta c"};
        assertEquals(StringUtil.trim(strs), new String[]{"java", "a c"});

    }

    @Test
    public void trim2() {
        List<String> list = null;
        StringUtil.trim(list);
        assertNull(list);

        list = Collections.emptyList();
        StringUtil.trim(list);
        assertEquals(list, Collections.emptyList());

        list = new ArrayList<>();
        StringUtil.trim(list);
        assertEquals(list, Collections.emptyList());

        list = new ArrayList<>();
        list.add(null);
        StringUtil.trim(list);
        List<String> result = new ArrayList<>();
        result.add(null);
        assertEquals(list, result);

        list = new ArrayList<>();
        list.add(null);
        list.add("");
        StringUtil.trim(list);
        result = new ArrayList<>();
        result.add(null);
        result.add("");
        assertEquals(list, result);

        list = new ArrayList<>();
        list.add(" ");
        list.add("\t");
        StringUtil.trim(list);
        result = new ArrayList<>();
        result.add("");
        result.add("");
        assertEquals(list, result);

        list = new ArrayList<>();
        list.add(" abc");
        list.add("edf \t");
        list.add(" o k ");
        StringUtil.trim(list);
        result = new ArrayList<>();
        result.add("abc");
        result.add("edf");
        result.add("o k");
        assertEquals(list, result);
    }

    @Test
    public void trimAllWhiteSpace() {
        String str = null;
        assertNull(StringUtil.trimAllWhiteSpace(str));

        str = "";
        assertEquals(StringUtil.trimAllWhiteSpace(str), "");

        str = "abcd";
        assertEquals(StringUtil.trimAllWhiteSpace(str), "abcd");

        str = " abc ";
        assertEquals(StringUtil.trimAllWhiteSpace(str), "abc");

        str = "\t\tab ";
        assertEquals(StringUtil.trimAllWhiteSpace(str), "ab");

        str = " a b c ";
        assertEquals(StringUtil.trimAllWhiteSpace(str), "abc");

        str = " a b c \t\t";
        assertEquals(StringUtil.trimAllWhiteSpace(str), "abc");
    }

    @Test
    public void hide() {
        String str = null;
        assertNull(StringUtil.hide(str, 2, 1));

        str = "";
        assertEquals(StringUtil.hide(str, 2, 1), "");

        str = "13012345678";
        assertEquals(StringUtil.hide(str, 3, 7), "130****5678");
    }


    @Test
    public void hideLength() {
        String str = null;
        assertNull(StringUtil.hide(str, 2, 1));

        str = "";
        assertEquals(StringUtil.hide(str, 2, 1), "");

        str = "13012345678";
        assertEquals(StringUtil.hideLength(str, 3, 4), "130****5678");
    }

    @Test
    public void replace() {
        String str = null;
        assertNull(StringUtil.replace(str, 2, 1, '1'));

        str = "";
        assertEquals(StringUtil.replace(str, 2, 1, '1'), "");

        str = "13012345678";
        assertEquals(StringUtil.replace(str, 3, 7, '.'), "130....5678");
    }

    @Test
    public void replace2() {
        String str = null;
        assertNull(StringUtil.replace(str, null, null));

        str = "";
        assertEquals(StringUtil.replace(str, null, null), "");

        str = "com.mzlion.core";
        assertEquals(StringUtil.replace(str, ".", null), "com.mzlion.core");

        str = "com.mzlion.core";
        assertEquals(StringUtil.replace(str, ".", "/"), "com/mzlion/core");
    }

    @Test
    public void delete() {
        String str = null;
        assertNull(StringUtil.delete(str, null));

        str = "";
        assertEquals(StringUtil.delete(str, null), "");

        str = "hello world";
        assertEquals(StringUtil.delete(str, "l"), "heo word");

        str = "aabbccbbdd";
        assertEquals(StringUtil.delete(str, "bb"), "aaccdd");

    }

    @Test
    public void deletePrefixIgnoreCase() {
        String str = null;
        assertNull(StringUtil.deletePrefixIgnoreCase(str, null));

        str = "";
        assertEquals(StringUtil.deletePrefixIgnoreCase(str, null), "");

        str = "conf.properties";
        assertEquals(StringUtil.deletePrefixIgnoreCase(str, null), "conf.properties");

        str = "classpath:conf.properties";
        assertEquals(StringUtil.deletePrefixIgnoreCase(str, "classpath:"), "conf.properties");

        str = "FILE:///conf.properties";
        assertEquals(StringUtil.deletePrefixIgnoreCase(str, "file://"), "/conf.properties");

    }

    @Test
    public void capitalize() {
        String str = null;
        assertNull(StringUtil.capitalize(str));

        str = "";
        assertEquals(StringUtil.capitalize(str), "");

        str = "hello";
        assertEquals(StringUtil.capitalize(str), "Hello");

        str = "作者Author";
        assertEquals(StringUtil.capitalize(str), "作者Author");

    }

    @Test
    public void unCapitalize() {
        String str = null;
        assertNull(StringUtil.unCapitalize(str));

        str = "";
        assertEquals(StringUtil.unCapitalize(str), "");

        str = "hello";
        assertEquals(StringUtil.unCapitalize(str), "hello");

        str = "HELLO";
        assertEquals(StringUtil.unCapitalize(str), "hELLO");

        str = "作者Author";
        assertEquals(StringUtil.unCapitalize(str), "作者Author");
    }

    @Test
    public void camelCaseToUnderline() {
        String str = null;
        assertNull(StringUtil.camelCaseToUnderline(str));

        str = "";
        assertEquals(StringUtil.camelCaseToUnderline(str), "");

        str = "HelloWorld";
        assertEquals(StringUtil.camelCaseToUnderline(str), "hello_world");

        str = "authView";
        assertEquals(StringUtil.camelCaseToUnderline(str), "auth_view");

        str = "ERPage";
        assertEquals(StringUtil.camelCaseToUnderline(str), "er_page");

    }

    @Test
    public void underlineToCamel() {
        String str = null;
        assertNull(StringUtil.underlineToCamel(str));

        str = "";
        assertEquals(StringUtil.underlineToCamel(str), "");

        str = "order_list";
        assertEquals(StringUtil.underlineToCamel(str), "orderList");
    }

    @Test
    public void startsWithIgnoreCase() {
        String str = null;
        assertFalse(StringUtil.startsWithIgnoreCase(str, null));

        str = "";
        assertFalse(StringUtil.startsWithIgnoreCase(str, null));

        str = " hello ";
        assertFalse(StringUtil.startsWithIgnoreCase(str, null));

        str = " hello ";
        assertTrue(StringUtil.startsWithIgnoreCase(str, " "));

        str = " Hello ";
        assertTrue(StringUtil.startsWithIgnoreCase(str, " h"));

    }

    @Test
    public void endsWithIgnoreCase() {
        String str = null;
        assertFalse(StringUtil.endsWithIgnoreCase(str, null));

        str = "";
        assertFalse(StringUtil.endsWithIgnoreCase(str, null));

        str = " hello ";
        assertFalse(StringUtil.endsWithIgnoreCase(str, null));

        str = " hello ";
        assertTrue(StringUtil.endsWithIgnoreCase(str, " "));

        str = " Hello WORLD";
        assertTrue(StringUtil.endsWithIgnoreCase(str, "WORLD"));

    }

    @Test
    public void equals() {
        String str1 = null, str2 = null;
        assertTrue(StringUtil.equals(str1, str2));

        str1 = "";
        str2 = null;
        assertFalse(StringUtil.equals(str1, str2));

        str1 = null;
        str2 = "";
        assertFalse(StringUtil.equals(str1, str2));

        str1 = "\tjava";
        str2 = "\tjava";
        assertTrue(StringUtil.equals(str1, str2));

        str1 = "\tJava";
        str2 = "\tjava";
        assertFalse(StringUtil.equals(str1, str2));

    }

    @Test
    public void equalsIgnoreCase() {
        String str1 = null, str2 = null;
        assertTrue(StringUtil.equalsIgnoreCase(str1, str2));

        str1 = "";
        str2 = null;
        assertFalse(StringUtil.equalsIgnoreCase(str1, str2));

        str1 = null;
        str2 = "";
        assertFalse(StringUtil.equalsIgnoreCase(str1, str2));

        str1 = "\tjava";
        str2 = "\tjava";
        assertTrue(StringUtil.equalsIgnoreCase(str1, str2));

        str1 = "\tJava";
        str2 = "\tjava";
        assertTrue(StringUtil.equalsIgnoreCase(str1, str2));

    }

    @Test
    public void contains() {
        String str = null;
        String[] array = null;
        assertFalse(StringUtil.contains(str, array));

        str = "";
        assertFalse(StringUtil.contains(str, array));

        str = null;
        array = new String[0];
        assertFalse(StringUtil.contains(str, array));

        str = "java";
        array = new String[]{"PHP", "Java", "Python"};
        assertFalse(StringUtil.contains(str, array));

        str = "Java";
        assertTrue(StringUtil.contains(str, array));
    }

    @Test
    public void containsIgnoreCase() {
        String str = null;
        String[] array = null;
        assertFalse(StringUtil.containsIgnoreCase(str, array));

        str = "";
        assertFalse(StringUtil.containsIgnoreCase(str, array));

        str = null;
        array = new String[0];
        assertFalse(StringUtil.containsIgnoreCase(str, array));

        str = "java";
        array = new String[]{"PHP", "Java", "Python"};
        assertTrue(StringUtil.containsIgnoreCase(str, array));

        str = "Java";
        assertTrue(StringUtil.containsIgnoreCase(str, array));
    }

    @Test
    public void split() {
        String str = null;
        String[] array = StringUtil.split(str);
        assertNull(array);

        str = "";
        array = StringUtil.split(str);
        assertNotNull(array);
        assertEquals(array.length, 0);

        str = "\t";
        array = StringUtil.split(str);
        assertNotNull(array);
        assertEquals(array.length, 1);
        assertEquals(array[0], "\t");

        str = "hello,world";
        array = StringUtil.split(str);
        assertNotNull(array);
        assertEquals(array.length, 2);
        assertEquals(array[0], "hello");
        assertEquals(array[1], "world");
        System.out.println(str + "\t->\t\t" + Arrays.toString(array));

        str = "hello,";
        array = StringUtil.split(str);
        assertNotNull(array);
        assertEquals(array.length, 2);
        assertEquals(array[0], "hello");
        assertEquals(array[1], "");
        System.out.println(str + "\t->\t\t" + Arrays.toString(array));

        str = ",hello,,";
        array = StringUtil.split(str);
        assertNotNull(array);
        assertEquals(array.length, 4);
        assertEquals(array[0], "");
        assertEquals(array[1], "hello");
        assertEquals(array[2], "");
        assertEquals(array[3], "");
        System.out.println(str + "\t->\t\t" + Arrays.toString(array));

        str = ",hello,,";
        array = StringUtil.split(str, ',', true);
        assertNotNull(array);
        assertEquals(array.length, 1);
        assertEquals(array[0], "hello");
        System.out.println(str + "\t->\t\t" + Arrays.toString(array));

        str = ", hello a ,\teverything , will ,be\n,ok";
        array = StringUtil.split(str, ',', true, true);
        assertNotNull(array);
        assertEquals(array.length, 5);
        assertEquals(array, new String[]{"hello a", "everything", "will", "be", "ok"});
        System.out.println(str + "\t->\t\t" + Arrays.toString(array));

        str = "Best wish for you\nThanks.";
        array = StringUtil.split(str, null);
        assertNotNull(array);
        assertEquals(array.length, 5);
        assertEquals(array, new String[]{"Best", "wish", "for", "you", "Thanks."});
        System.out.println(str + "\t->\t\t" + Arrays.toString(array));

        str = "Mac##iPad#iPad Pro##iPhone";
        array = StringUtil.split(str, "##");
        assertNotNull(array);
        assertEquals(array.length, 3);
        assertEquals(array, new String[]{"Mac", "iPad#iPad Pro", "iPhone"});
        System.out.println(str + "\t->\t\t" + Arrays.toString(array));

        str = "\t筷子 || 笔记 || 随|便||";
        array = StringUtil.split(str, "||", true, true);
        assertNotNull(array);
        assertEquals(array.length, 3);
        assertEquals(array, new String[]{"筷子", "笔记", "随|便"});
        System.out.println(str + "\t->\t\t" + Arrays.toString(array));
    }

    @Test
    public void splitToIntList() {
        String str = "";
        List<Integer> intList = StringUtil.splitToIntList(str);
        assertNotNull(intList);
        assertEquals(intList.size(), 0);
        System.out.println(str + "\t->\t\t" + intList);

        str = ",1,2";
        intList = StringUtil.splitToIntList(str);
        assertNotNull(intList);
        assertEquals(intList.size(), 2);
        System.out.println(str + "\t->\t\t" + intList);

        str = "|||||1|2";
        intList = StringUtil.splitToIntList(str, "|");
        assertNotNull(intList);
        assertEquals(intList.size(), 2);
        List<Integer> result = new ArrayList<>(4);
        result.add(1);
        result.add(2);
        assertEquals(intList, result);
        System.out.println(str + "\t->\t\t" + intList);

    }

    @Test
    public void urlParamsToMap() {
        String urlParams = null;
        Map<String, String> params = StringUtil.urlParamsToSingleMap(urlParams);
        assertEquals(params, Collections.emptyMap());

        urlParams = "";
        params = StringUtil.urlParamsToSingleMap(urlParams);
        assertEquals(params, Collections.emptyMap());

        urlParams = "a";
        params = StringUtil.urlParamsToSingleMap(urlParams);
        Map<String, String> result = new HashMap<>(4);
        result.put("a", null);
        assertEquals(params, result);
        System.out.println(urlParams + "\t->\t\t" + params);

        urlParams = "username=";
        params = StringUtil.urlParamsToSingleMap(urlParams);
        result = new HashMap<>(4);
        result.put("username", "");
        assertEquals(params, result);
        System.out.println(urlParams + "\t->\t\t" + params);

        urlParams = "username=admin&password=&";
        params = StringUtil.urlParamsToSingleMap(urlParams);
        result = new HashMap<>(4);
        result.put("username", "admin");
        result.put("password", "");
        assertEquals(params, result);
        System.out.println(urlParams + "\t->\t\t" + params);

    }

    @Test
    public void format() {
        String text = null;
        assertNull(StringUtil.format(text));

        text = "";
        assertEquals(StringUtil.format(text), "");

        text = "String is a string";
        assertEquals(StringUtil.format(text), text);

        text = "Mock Location is {}";
        assertEquals(StringUtil.format(text), text);

        assertEquals(StringUtil.format(text, "Shanghai"), "Mock Location is Shanghai");

        text = "Mock Location is \\{}";
        assertEquals(StringUtil.format(text, "Shanghai"), "Mock Location is {}");

        text = "Mock Location is \\\\{}";
        assertEquals(StringUtil.format(text, "Shanghai"), "Mock Location is \\Shanghai");

    }

}
