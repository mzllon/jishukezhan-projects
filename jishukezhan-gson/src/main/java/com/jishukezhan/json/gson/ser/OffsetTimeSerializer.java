package com.jishukezhan.json.gson.ser;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.jishukezhan.core.date.DatePattern;
import com.jishukezhan.core.lang.Preconditions;

import java.lang.reflect.Type;
import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;

/**
 * Serializer for Java 8 temporal {@link OffsetTime}s.
 *
 * @author miles.tang
 */
public class OffsetTimeSerializer implements JsonSerializer<OffsetTime> {

    private DateTimeFormatter formatter;

    public OffsetTimeSerializer() {
        this(DatePattern.NORMAL_TIME_FORMATTER);
    }

    public OffsetTimeSerializer(DateTimeFormatter formatter) {
        this.formatter = Preconditions.requireNonNull(formatter, "formatter == null");
    }

    @Override
    public JsonElement serialize(OffsetTime offsetTime, Type type, JsonSerializationContext context) {
        return new JsonPrimitive(offsetTime.format(formatter));
    }

    public static final OffsetTimeSerializer INSTANCE = new OffsetTimeSerializer();

}
