package com.jishukezhan.json.gson.ser;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.jishukezhan.core.date.DatePattern;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Serializer for Java 8 temporal {@link LocalDate}s.
 *
 * @author Miles.Tang
 */
public class LocalDateSerializer implements JsonSerializer<LocalDate> {

    private DateTimeFormatter formatter;

    public LocalDateSerializer() {
        this(DatePattern.NORMAL_DATE_FORMATTER);
    }

    public LocalDateSerializer(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }


    @Override
    public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
        if (src == null) {
            return null;
        }
        return new JsonPrimitive(src.format(formatter));
    }

    public static final LocalDateSerializer INSTANCE = new LocalDateSerializer();

}
