package com.jishukezhan.json.gson.ser;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.jishukezhan.core.date.DatePattern;

import java.lang.reflect.Type;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Serializer for Java 8 temporal {@link LocalTime}s.
 *
 * @author miles.tang
 */
public class LocalTimeSerializer implements JsonSerializer<LocalTime> {

    private DateTimeFormatter formatter;

    public LocalTimeSerializer() {
        this(DatePattern.NORMAL_TIME_FORMATTER);
    }

    public LocalTimeSerializer(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public JsonElement serialize(LocalTime src, Type typeOfSrc, JsonSerializationContext context) {
        if (src == null) {
            return null;
        }
        return new JsonPrimitive(src.format(formatter));
    }

    public static final LocalTimeSerializer INSTANCE = new LocalTimeSerializer();

}
