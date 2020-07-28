package com.jishukezhan.core.bean;

import org.testng.annotations.Test;

import java.util.Date;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class BeanUtilTest {

    @Test
    public void testIsBeanType() {
        assertFalse(BeanUtil.isBeanType(Bean1.class));
        assertTrue(BeanUtil.isBeanType(Bean2.class));
        assertTrue(BeanUtil.isBeanType(Bean3.class));
        assertFalse(BeanUtil.isBeanType(Bean4.class));
        assertFalse(BeanUtil.isBeanType(Bean5.class));
        assertFalse(BeanUtil.isBeanType(Bean6.class));
        assertTrue(BeanUtil.isBeanType(Bean7.class));
        assertTrue(BeanUtil.isBeanType(Bean8.class));
    }

    @Test
    public void testGetPropertyDescriptorList() {
    }

    @Test
    public void testGetPropertyDescriptorMap() {
    }

    @Test
    public void testGetPropertyDescriptor() {
    }

    @Test
    public void testGetPropertyValue() {
    }

    @Test
    public void testBeanToMap() {
    }

    @Test
    public void testBeanToMap1() {
    }

    @Test
    public void testBeanToMap2() {
    }

    @Test
    public void testToMapAsValueString() {
    }

    @Test
    public void testCopyProperties() {
    }

    @Test
    public void testCopyProperties1() {
    }

    public static class Bean1 {
        public static String id;
    }

    public static class Bean2 {
        public static String id;
        public String name;
        public Date date;
    }

    public static class Bean3 {
        public String id;
        public String name;
        public Date date;
    }

    public static class Bean4 {
        private String id;
        private String name;
        private Date date;

    }

    public static class Bean5 {
        private String id;
        private String name;
        private Date date;

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public Date getDate() {
            return date;
        }
    }

    public static class Bean6 {
        private String id;
        private String name;
        private Date date;

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setDate(Date date) {
            this.date = date;
        }
    }

    public static class Bean7 {
        private String id;
        private String name;
        private Date date;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }
    }

    public static class Bean8 {
        private String id;
        private String name;
        private boolean open;

        public boolean isOpen() {
            return open;
        }

        public void setOpen(boolean open) {
            this.open = open;
        }
    }

}