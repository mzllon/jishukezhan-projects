package com.jishukezhan.json.gson.ser;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.jishukezhan.core.lang.Preconditions;

import java.lang.reflect.Type;
import java.time.Year;
import java.time.format.DateTimeFormatter;

/**
 * Serializer for Java 8 temporal {@link Year}s.
 *
 * @author miles.tang
 */
public class YearSerializer implements JsonSerializer<Year> {

    private DateTimeFormatter formatter;

    public YearSerializer() {
        this(DateTimeFormatter.ofPattern("yyyy"));
    }

    public YearSerializer(DateTimeFormatter formatter) {
        this.formatter = Preconditions.requireNonNull(formatter, "formatter == null");
    }

    @Override
    public JsonElement serialize(Year year, Type type, JsonSerializationContext context) {
        return new JsonPrimitive(year.format(formatter));
    }

    public static final YearSerializer INSTANCE = new YearSerializer();

}
