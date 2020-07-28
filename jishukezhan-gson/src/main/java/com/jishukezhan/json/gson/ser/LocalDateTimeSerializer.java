package com.jishukezhan.json.gson.ser;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.jishukezhan.core.date.DatePattern;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Serializer for Java 8 temporal {@link LocalDateTime}s.
 *
 * @author Miles.Tang
 */
public class LocalDateTimeSerializer implements JsonSerializer<LocalDateTime> {

    private DateTimeFormatter formatter;

    public LocalDateTimeSerializer() {
        this(DatePattern.NORMAL_DATETIME_FORMATTER);
    }

    public LocalDateTimeSerializer(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
        if (src == null) {
            return null;
        }
        return new JsonPrimitive(src.format(formatter));
    }

    public static final LocalDateTimeSerializer INSTANCE = new LocalDateTimeSerializer();

}
