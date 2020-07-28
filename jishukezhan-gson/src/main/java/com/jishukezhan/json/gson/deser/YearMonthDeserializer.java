package com.jishukezhan.json.gson.deser;

import com.google.gson.*;
import com.jishukezhan.core.lang.Preconditions;
import com.jishukezhan.core.lang.StringUtil;

import java.lang.reflect.Type;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

/**
 * Serializer for Java 8 temporal {@link YearMonth}s.
 *
 * @author Miles.Tang
 */
public class YearMonthDeserializer implements JsonDeserializer<YearMonth> {

    private DateTimeFormatter formatter;

    public YearMonthDeserializer() {
        this(DateTimeFormatter.ofPattern("yyyy-MM"));
    }

    public YearMonthDeserializer(DateTimeFormatter formatter) {
        this.formatter = Preconditions.requireNonNull(formatter, "formatter == null");
    }

    @Override
    public YearMonth deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        if (element.isJsonPrimitive()) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (primitive.isString()) {
                String value = primitive.getAsString();
                if (StringUtil.isEmpty(value)) {
                    return null;
                }
                return YearMonth.parse(value, formatter);
            }
        }
        return null;
    }

    public static final YearMonthDeserializer INSTANCE = new YearMonthDeserializer();

}
