package com.jishukezhan.json.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.JsonTokenId;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.*;
import com.fasterxml.jackson.datatype.jsr310.ser.*;
import com.jishukezhan.core.date.DatePattern;
import com.jishukezhan.core.lang.Preconditions;
import com.jishukezhan.json.JSON;
import com.jishukezhan.json.JSONBuilder;
import com.jishukezhan.json.annotation.JSONName;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

@JSONName("jackson")
public class JacksonJSONBuilder extends JSONBuilder {

    private ObjectMapper objectMapper;

    public JacksonJSONBuilder() {
        this.objectMapper = createObjectMapper();
    }

    public JacksonJSONBuilder(ObjectMapper objectMapper) {
        this.objectMapper = Preconditions.requireNonNull(objectMapper, "objectMapper == null");
    }

    /**
     * 构建对象
     *
     * @return {@linkplain JSONBuilder}
     */
    @Override
    public JSON build() {

        JacksonHandler jacksonHandler = new JacksonHandler();
        jacksonHandler.setObjectMapper(objectMapper);

        JSON json = new JSON();
        json.setHandler(jacksonHandler);

        return json;
    }

    public static JavaTimeModule createJavaTimeModule() {
        JavaTimeModule javaTimeModule = new JavaTimeModule();

        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DatePattern.NORMAL_DATE_FORMATTER));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DatePattern.NORMAL_DATE_FORMATTER));

        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DatePattern.NORMAL_DATETIME_FORMATTER));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DatePattern.NORMAL_DATETIME_FORMATTER));

        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DatePattern.NORMAL_TIME_FORMATTER));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DatePattern.NORMAL_TIME_FORMATTER));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
        javaTimeModule.addSerializer(Year.class, new YearSerializer(formatter));
        javaTimeModule.addDeserializer(Year.class, new YearDeserializer(formatter));

        formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        javaTimeModule.addSerializer(YearMonth.class, new YearMonthSerializer(formatter));
        javaTimeModule.addDeserializer(YearMonth.class, new YearMonthDeserializer(formatter));

        formatter = DateTimeFormatter.ofPattern("MM-dd");
        javaTimeModule.addSerializer(MonthDay.class, new MonthDaySerializer(formatter));
        javaTimeModule.addDeserializer(MonthDay.class, new MonthDayDeserializer(formatter));

        javaTimeModule.addSerializer(OffsetDateTime.class, new OffsetDateTimeSerializer(
                OffsetDateTimeSerializer.INSTANCE, false, DatePattern.UTC_MS_WITH_ZONE_OFFSET_FORMATTER) {
        });
//        javaTimeModule.addDeserializer(OffsetDateTime.class, new InstantDeserializer<OffsetDateTime>(
//                InstantDeserializer.OFFSET_DATE_TIME, DatePattern.UTC_MS_WITH_ZONE_OFFSET_FORMATTER) {
//        });
        javaTimeModule.addDeserializer(OffsetDateTime.class, new OffsetDateTimeDeserializer());

        javaTimeModule.addSerializer(ZonedDateTime.class, new ZonedDateTimeSerializer(DatePattern.UTC_MS_WITH_ZONE_OFFSET_FORMATTER));
//        javaTimeModule.addDeserializer(ZonedDateTime.class,
//                new InstantDeserializer<ZonedDateTime>(InstantDeserializer.ZONED_DATE_TIME, DatePattern.UTC_MS_WITH_ZONE_OFFSET_FORMATTER) {
//                });
        javaTimeModule.addDeserializer(ZonedDateTime.class, new ZonedDateTimeDeserializer());

        return javaTimeModule;
    }

    public static ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(createJavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // 过滤transient
        objectMapper.setAnnotationIntrospector(new JacksonAnnotationIntrospector() {
            @Override
            public boolean hasIgnoreMarker(AnnotatedMember annotatedMember) {
                return Modifier.isTransient(annotatedMember.getMember().getModifiers()) || super.hasIgnoreMarker(annotatedMember);
            }
        });
        return objectMapper;
    }

    public static class OffsetDateTimeDeserializer extends InstantDeserializer<OffsetDateTime> {

        public OffsetDateTimeDeserializer() {
            super(InstantDeserializer.OFFSET_DATE_TIME, DatePattern.UTC_MS_WITH_ZONE_OFFSET_FORMATTER);
        }

        @Override
        public OffsetDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException {
            switch (parser.getCurrentTokenId()) {
                case JsonTokenId.ID_NUMBER_FLOAT:
                    return _fromDecimal(context, parser.getDecimalValue());
                case JsonTokenId.ID_NUMBER_INT:
                    return _fromLong(context, parser.getLongValue());
                case JsonTokenId.ID_STRING: {
                    String string = parser.getText().trim();
                    if (string.length() == 0) {
                        return null;
                    }
                    return OffsetDateTime.parse(string, _formatter);
                }
                case JsonTokenId.ID_EMBEDDED_OBJECT:
                    // 20-Apr-2016, tatu: Related to [databind#1208], can try supporting embedded
                    //    values quite easily
                    return (OffsetDateTime) parser.getEmbeddedObject();

                case JsonTokenId.ID_START_ARRAY:
                    return _deserializeFromArray(parser, context);
            }
            return _handleUnexpectedToken(context, parser, JsonToken.VALUE_STRING,
                    JsonToken.VALUE_NUMBER_INT, JsonToken.VALUE_NUMBER_FLOAT);
        }

        private String replaceZeroOffsetAsZIfNecessary(String text) {
            if (replaceZeroOffsetAsZ) {
                return ISO8601_UTC_ZERO_OFFSET_SUFFIX_REGEX.matcher(text).replaceFirst("Z");
            }

            return text;
        }

        /**
         * Constants used to check if the time offset is zero. See [jackson-modules-java8#18]
         *
         * @since 2.9.0
         */
        private static final Pattern ISO8601_UTC_ZERO_OFFSET_SUFFIX_REGEX = Pattern.compile("\\+00:?(00)?$");
    }

    public static class ZonedDateTimeDeserializer extends InstantDeserializer<ZonedDateTime> {

        public ZonedDateTimeDeserializer() {
            super(InstantDeserializer.ZONED_DATE_TIME, DatePattern.UTC_MS_WITH_ZONE_OFFSET_FORMATTER);
        }

        @Override
        public ZonedDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException {
            switch (parser.getCurrentTokenId()) {
                case JsonTokenId.ID_NUMBER_FLOAT:
                    return _fromDecimal(context, parser.getDecimalValue());
                case JsonTokenId.ID_NUMBER_INT:
                    return _fromLong(context, parser.getLongValue());
                case JsonTokenId.ID_STRING: {
                    String string = parser.getText().trim();
                    if (string.length() == 0) {
                        return null;
                    }
                    return ZonedDateTime.parse(string, _formatter);
                }
                case JsonTokenId.ID_EMBEDDED_OBJECT:
                    // 20-Apr-2016, tatu: Related to [databind#1208], can try supporting embedded
                    //    values quite easily
                    return (ZonedDateTime) parser.getEmbeddedObject();

                case JsonTokenId.ID_START_ARRAY:
                    return _deserializeFromArray(parser, context);
            }
            return _handleUnexpectedToken(context, parser, JsonToken.VALUE_STRING,
                    JsonToken.VALUE_NUMBER_INT, JsonToken.VALUE_NUMBER_FLOAT);
        }
    }
}
