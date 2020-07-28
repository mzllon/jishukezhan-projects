package com.jishukezhan.http;

import com.jishukezhan.annotation.NonNull;
import com.jishukezhan.annotation.Nullable;
import com.jishukezhan.core.http.ContentType;
import com.jishukezhan.core.io.FileUtil;
import com.jishukezhan.core.lang.Preconditions;
import com.jishukezhan.core.lang.StringUtil;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MultipartBody extends RequestBody {

    private String boundary;

    private List<Part> parts;

    MultipartBody(Builder builder) {
        this.contentType = builder.contentType;
        this.boundary = builder.boundary;
        this.parts = Collections.unmodifiableList(builder.parts);
    }

    public String getBoundary() {
        return boundary;
    }

    public List<Part> getParts() {
        return parts;
    }


    public static Builder builder() {
        return new Builder();
    }

    public static class Part {

        private String name;

        private String value;

        private ContentType contentType;

        private RequestBody body;

        private File file;

        private InputStream in;

        Part(String name, String value, ContentType contentType) {
            this.name = name;
            this.value = value;
            this.contentType = contentType;
        }

        Part(String name, RequestBody value, ContentType contentType) {
            this.name = name;
            this.body = value;
            this.contentType = contentType;
        }

        Part(String name, String value, RequestBody body, ContentType contentType) {
            this(name, value, contentType);
            this.body = body;
        }

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }

        @NonNull
        public ContentType getContentType() {
            return contentType;
        }

        public RequestBody getBody() {
            return body;
        }

        @Nullable
        public File getFile() {
            return file;
        }

        @Nullable
        public InputStream getIn() {
            return in;
        }

        public static Part create(String name, String value) {
            return create(name, value, (ContentType) null);
        }

        public static Part create(String name, String value, @Nullable ContentType contentType) {
            return new Part(name, value, contentType == null ? ContentType.DEFAULT_TEXT : contentType);
        }

        public static Part create(String name, @NonNull File file) {
            return create(name, file.getName(), file);
        }

        public static Part create(String name, String filename, @NonNull File file) {
            Preconditions.requireNonNull(file, "file == null");
            Part part = new Part(name, filename, ContentType.parseByFileExt(FileUtil.getFileExt(file)));
            part.file = file;
            return part;
        }

        public static Part create(String name, String filename, InputStream in) {
            return create(name, filename, in, null);
        }

        public static Part create(String name, String filename, @NonNull InputStream in,
                                  @Nullable ContentType contentType) {
            Part part = new Part(name, filename, contentType == null ? ContentType.DEFAULT_BINARY : contentType);
            part.in = in;
            return part;
        }

        public static Part create(String name, String filename, RequestBody body) {
            return create(name, filename, body, null);
        }

        public static Part create(String name, String filename, RequestBody body, @Nullable ContentType contentType) {
            Part part = new Part(name, filename, contentType == null ? ContentType.DEFAULT_BINARY : contentType);
            part.body = body;
            return part;
        }

    }

    public static class Builder {

        private String boundary;

        private ContentType contentType;

        private List<Part> parts = new ArrayList<>();

        public Builder() {
        }

        public Builder boundary(String boundary) {
            this.boundary = boundary;
            this.contentType = ContentType.MULTIPART_FORM_DATA;
            return this;
        }

        public Builder contentType(@NonNull ContentType contentType) {
            this.contentType = Preconditions.requireNonNull(contentType, "contentType == null");
            return this;
        }

        public Builder add(@Nullable String name, @Nullable String value) {
            return add(name, value, (ContentType) null);
        }

        public Builder add(@Nullable String name, @Nullable String value, @Nullable ContentType contentType) {
            if (StringUtil.hasLength(name)) {
                parts.add(Part.create(name, value, contentType));
            }
            return this;
        }

        public Builder add(@Nullable String name, @Nullable File file) {
            if (StringUtil.hasLength(name) && file != null) {
                parts.add(Part.create(name, file));
            }
            return this;
        }

        public Builder add(@Nullable String name, @Nullable String filename, @Nullable RequestBody body) {
            if (StringUtil.haveAllLength(name, filename) && body != null) {
                parts.add(Part.create(name, filename, body));
            }
            return this;
        }

        public Builder add(@NonNull Part part) {
            parts.add(Preconditions.requireNonNull(part, "part == null"));
            return this;
        }

        public MultipartBody build() {
            return new MultipartBody(this);
        }

    }

}
