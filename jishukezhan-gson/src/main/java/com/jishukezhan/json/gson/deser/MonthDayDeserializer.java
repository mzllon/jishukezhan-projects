package com.jishukezhan.json.gson.deser;

import com.google.gson.*;
import com.jishukezhan.core.lang.Preconditions;
import com.jishukezhan.core.lang.StringUtil;

import java.lang.reflect.Type;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;

/**
 * Deserializer for Java 8 temporal {@link MonthDay}s.
 *
 * @author Miles.Tang
 */
public class MonthDayDeserializer implements JsonDeserializer<MonthDay> {

    private DateTimeFormatter formatter;

    public MonthDayDeserializer() {
        this(DateTimeFormatter.ofPattern("MM-dd"));
    }

    public MonthDayDeserializer(DateTimeFormatter formatter) {
        this.formatter = Preconditions.requireNonNull(formatter, "formatter == null");
    }

    @Override
    public MonthDay deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        if (element.isJsonPrimitive()) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (primitive.isString()) {
                String value = primitive.getAsString();
                if (StringUtil.isEmpty(value)) {
                    return null;
                }
                return MonthDay.parse(value, formatter);
            }

        }
        return null;
    }

    public static final MonthDayDeserializer INSTANCE = new MonthDayDeserializer();

}
