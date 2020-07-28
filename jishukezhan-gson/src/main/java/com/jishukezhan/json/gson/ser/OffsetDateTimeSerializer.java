package com.jishukezhan.json.gson.ser;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.jishukezhan.core.date.DatePattern;

import java.lang.reflect.Type;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class OffsetDateTimeSerializer implements JsonSerializer<OffsetDateTime> {

    private DateTimeFormatter formatter;

    public OffsetDateTimeSerializer() {
        this(DatePattern.UTC_MS_WITH_ZONE_OFFSET_FORMATTER);
    }

    public OffsetDateTimeSerializer(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }


    @Override
    public JsonElement serialize(OffsetDateTime offsetDateTime, Type type, JsonSerializationContext jsonSerializationContext) {

        return new JsonPrimitive(offsetDateTime.format(formatter));
    }

    public static final OffsetDateTimeSerializer INSTANCE = new OffsetDateTimeSerializer();

}
