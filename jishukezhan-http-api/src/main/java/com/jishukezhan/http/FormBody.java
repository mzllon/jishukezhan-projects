package com.jishukezhan.http;

import com.jishukezhan.core.http.ContentType;
import com.jishukezhan.core.lang.CharsetUtil;
import com.jishukezhan.core.lang.Preconditions;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class FormBody extends RequestBody {

    private final List<Item> items;
    private final Charset charset;

    FormBody(List<Item> items, Charset charset) {
        this.items = Collections.unmodifiableList(items);
        this.contentType = ContentType.APPLICATION_FORM_URLENCODED;
        this.charset = charset;
    }

    public List<Item> getItems() {
        return items;
    }

    @Override
    public Charset getCharset() {
        return charset;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final List<Item> items = new ArrayList<>();
        private Charset charset;

        public Builder() {
            this(null);
        }

        public Builder(Charset charset) {
            this.charset = CharsetUtil.getCharset(charset, CharsetUtil.UTF_8);
        }

        public Builder add(String name, String value) {
            Preconditions.requireNonNull(name, "'name' must not be null");
            Preconditions.requireNonNull(value, "'value' must not be null");

            items.add(new Item(name, value));
            return this;
        }

        public FormBody build() {
            return new FormBody(items, charset);
        }
    }

    public static class Item {

        private final String name;

        private final String value;

        Item(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }

    }

}
