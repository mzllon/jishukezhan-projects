package com.jishukezhan.json.gson.ser;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.jishukezhan.core.date.DatePattern;
import com.jishukezhan.core.date.ZoneIdConstant;
import com.jishukezhan.core.lang.Preconditions;

import java.lang.reflect.Type;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Serializer for Java 8 temporal {@link ZonedDateTime}s.
 *
 * @author Miles.Tang
 */
public class ZonedDateTimeSerializer implements JsonSerializer<ZonedDateTime> {

    private DateTimeFormatter formatter;

    public ZonedDateTimeSerializer() {
        this(DatePattern.NORMAL_DATETIME_FORMATTER);
    }

    public ZonedDateTimeSerializer(DateTimeFormatter formatter) {
        this.formatter = Preconditions.requireNonNull(formatter, "formatter == null");
        if (this.formatter.getZone() == null) {
            this.formatter.withZone(ZoneIdConstant.ASIA_SHANGHHAI);
        }
    }

    @Override
    public JsonElement serialize(ZonedDateTime zonedDateTime, Type type, JsonSerializationContext jsonSerializationContext) {
        if (zonedDateTime == null) {
            return null;
        }
        return new JsonPrimitive(zonedDateTime.format(formatter));
    }

    public static final ZonedDateTimeSerializer INSTANCE = new ZonedDateTimeSerializer();

}
