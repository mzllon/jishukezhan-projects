package com.jishukezhan.json.gson.deser;

import com.google.gson.*;
import com.jishukezhan.core.date.DatePattern;
import com.jishukezhan.core.date.ZoneOffsetConstant;
import com.jishukezhan.core.lang.Preconditions;
import com.jishukezhan.core.lang.StringUtil;

import java.lang.reflect.Type;
import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;

/**
 * Deserializer for Java 8 temporal {@link OffsetTime}s.
 *
 * @author Miles.Tang
 */
public class OffsetTimeDeserializer implements JsonDeserializer<OffsetTime> {

    private DateTimeFormatter formatter;

    public OffsetTimeDeserializer() {
        this(DatePattern.NORMAL_TIME_FORMATTER.withZone(ZoneOffsetConstant.BEIJING_ZONE_OFFSET));
    }

    public OffsetTimeDeserializer(DateTimeFormatter formatter) {
        this.formatter = Preconditions.requireNonNull(formatter, "formatter == null");
        if (this.formatter.getZone() == null) {
            this.formatter = this.formatter.withZone(ZoneOffsetConstant.BEIJING_ZONE_OFFSET);
        }
    }

    @Override
    public OffsetTime deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        if (element.isJsonPrimitive()) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (primitive.isString()) {
                String value = primitive.getAsString();
                if (StringUtil.isEmpty(value)) {
                    return null;
                }
                return OffsetTime.parse(value, formatter);
            }
        }
        return null;
    }

    public static final OffsetTimeDeserializer INSTANCE = new OffsetTimeDeserializer();

}
