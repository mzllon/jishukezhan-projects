package com.jishukezhan.json.gson.deser;

import com.google.gson.*;
import com.jishukezhan.core.date.DatePattern;
import com.jishukezhan.core.lang.Preconditions;
import com.jishukezhan.core.lang.StringUtil;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Deserializer for Java 8 temporal {@link LocalDate}s.
 *
 * @author Miles.Tang
 */
public class LocalDateDeserializer implements JsonDeserializer<LocalDate> {

    private DateTimeFormatter formatter;

    public LocalDateDeserializer() {
        this(DatePattern.NORMAL_DATE_FORMATTER);
    }

    public LocalDateDeserializer(DateTimeFormatter formatter) {
        this.formatter = Preconditions.requireNonNull(formatter, "formatter == null");
    }

    @Override
    public LocalDate deserialize(JsonElement element, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (element.isJsonPrimitive()) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (primitive.isString()) {
                String value = primitive.getAsString();
                if (StringUtil.isEmpty(value)) {
                    return null;
                }
                return LocalDate.parse(value, formatter);
            } else if (primitive.isNumber()) {
                return LocalDate.ofEpochDay(primitive.getAsLong());
            }
        }
        return null;
    }

    public static final LocalDateDeserializer INSTANCE = new LocalDateDeserializer();

}
