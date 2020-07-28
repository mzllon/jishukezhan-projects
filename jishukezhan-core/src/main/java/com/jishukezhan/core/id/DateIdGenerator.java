package com.jishukezhan.core.id;

import com.jishukezhan.core.date.DatePattern;
import com.jishukezhan.core.date.DateUtil;
import com.jishukezhan.core.lang.StringUtil;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 带有日期的ID生成器,32位ID组成规则：
 * yyyyMMddHHmmssSSS组成的17为日期字符串
 * 用一个中划线分割
 * 剩余14个字符串随机字符串
 *
 * @author miles.tang
 */
public class DateIdGenerator implements IdGenerator<Object> {

    /**
     * 返回ID
     *
     * @param obj 参数
     * @return ID
     */
    @Override
    public String get(Object obj) {
        String datetimePrefix = DateUtil.format(DatePattern.PURE_DATETIME_MS_FORMATTER);
        return datetimePrefix + StringUtil.DASH + getSeqId() + getRandomString(8);
    }

    /**
     * 返回ID
     *
     * @return ID
     */
    @Override
    public String get() {
        return get(null);
    }

    private static final int maxId = 999999;
    //    private static int curId = 0;
    private static AtomicInteger cursor = new AtomicInteger();
    private static final Object lock = new Object();

//    private String getShortId() { //25
//        return datetimePrefix() + getSeqId("#000000") + getRandomString(2);
//    }

    private String getSeqId() {
        int id = cursor.incrementAndGet();
        if (id > maxId) {
            synchronized (lock) {
                cursor.set(0);
                id = cursor.incrementAndGet();
            }
        }
//        synchronized (lock) {
//            if (curId >= maxId) {
//                curId = 0;
//            }
////            NumberFormat format = new DecimalFormat(type);
////            return format.format(value);
//        }
        return String.format("%06d", id);
    }

    private String getRandomString(int count) {
        StringBuilder sb = new StringBuilder(count);
        for (int i = 0; i < count; i++) {
            int psd = (int) (Math.random() * (26 * 2 + 10));
            if (psd >= 26 + 10) { //a~z
                char a = (char) (psd + 97 - 10 - 26);
                sb.append(a);
            } else if (psd >= 10) { //A~Z
                char a = (char) (psd + 65 - 10);
                sb.append(a);
            } else { //0~9
                sb.append(psd);
            }
        }
        return sb.toString();
    }

}
