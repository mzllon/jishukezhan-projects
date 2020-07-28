package com.jishukezhan.json.gson.ser;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.jishukezhan.core.lang.Preconditions;

import java.lang.reflect.Type;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

/**
 * Serializer for Java 8 temporal {@link YearMonth}s.
 *
 * @author miles.tang
 */
public class YearMonthSerializer implements JsonSerializer<YearMonth> {

    private DateTimeFormatter formatter;

    public YearMonthSerializer() {
        this(DateTimeFormatter.ofPattern("yyyy-MM"));
    }

    public YearMonthSerializer(DateTimeFormatter formatter) {
        this.formatter = Preconditions.requireNonNull(formatter, "formatter == null");
    }

    @Override
    public JsonElement serialize(YearMonth yearMonth, Type type, JsonSerializationContext context) {
        return new JsonPrimitive(yearMonth.format(formatter));
    }

    public static final YearMonthSerializer INSTANCE = new YearMonthSerializer();

}
