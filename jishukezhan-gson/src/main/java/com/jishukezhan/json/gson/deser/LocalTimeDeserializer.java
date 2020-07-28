package com.jishukezhan.json.gson.deser;

import com.google.gson.*;
import com.jishukezhan.core.date.DatePattern;
import com.jishukezhan.core.lang.Preconditions;
import com.jishukezhan.core.lang.StringUtil;
import com.jishukezhan.json.gson.ser.LocalTimeSerializer;

import java.lang.reflect.Type;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Deserializer for Java 8 temporal {@link LocalTime}s.
 *
 * @author Miles.Tang
 */
public class LocalTimeDeserializer implements JsonDeserializer<LocalTime> {

    private DateTimeFormatter formatter;

    public LocalTimeDeserializer() {
        this(DatePattern.NORMAL_TIME_FORMATTER);
    }

    public LocalTimeDeserializer(DateTimeFormatter formatter) {
        this.formatter = Preconditions.requireNonNull(formatter, "formatter == null");
    }

    @Override
    public LocalTime deserialize(JsonElement element, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (element.isJsonPrimitive()) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (primitive.isString()) {
                String value = primitive.getAsString();
                if (StringUtil.isEmpty(value)) {
                    return null;
                }
                return LocalTime.parse(value, formatter);
            }
        }
        return null;
    }

    public static final LocalTimeSerializer INSTANCE = new LocalTimeSerializer();

}
