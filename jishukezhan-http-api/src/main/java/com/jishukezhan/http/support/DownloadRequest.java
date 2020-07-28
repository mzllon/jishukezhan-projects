package com.jishukezhan.http.support;

import com.jishukezhan.annotation.NonNull;
import com.jishukezhan.core.io.FileUtil;
import com.jishukezhan.core.lang.Preconditions;
import com.jishukezhan.core.lang.StringUtil;
import com.jishukezhan.http.Method;
import com.jishukezhan.http.RequestBody;
import com.jishukezhan.http.Response;
import com.jishukezhan.http.Utils;
import com.jishukezhan.http.exceptions.CilantroStatusException;

import java.io.File;

public class DownloadRequest extends RequestSupport<DownloadRequest> {

    public DownloadRequest(String url) {
        super.url = Preconditions.requireNotEmpty(url, "url is null or empty");
        super.method = Method.GET;
    }

    @Override
    protected RequestBody genBody() {
        return null;
    }

    /**
     * 执行请求,并将结果写到指定目录或文件
     * <p>如果目标是文件夹，则会根据请求尝试解析文件名，如果无法解析出文件名，则自动采用"Cilantro-Download-${index}"</p>
     * <p>如果目标是文件，则直接覆盖本本文件</p>
     *
     * @param file 目标目录或文件
     * @return 最终文件
     */
    public File writeTo(@NonNull File file) {
        return writeTo(file, true);
    }

    /**
     * 执行请求,并将结果写到指定目录或文件
     * <p>如果目标是文件夹，则会根据请求尝试解析文件名，如果无法解析出文件名，则自动采用"Cilantro-Download-${index}"</p>
     * <p>如果目标是文件，根据参数{@code replace}的值{@code true}会替换已存在的文件,否则会重命名</p>
     *
     * @param file    目标目录或文件
     * @param replace 是否支持重命名
     * @return 最终文件
     */
    public File writeTo(@NonNull File file, boolean replace) {
        Preconditions.requireNonNull(file, "file == null");
        Response response = execute();
        if (response.status() >= 200 && response.status() < 300) {
            File target = null;
            if (file.isDirectory()) {
                String filename = Utils.expandFilenameFromContentDisposition(response.headers());
                if (StringUtil.isEmpty(filename)) {
                    filename = "Cilantro-Download";
                }
                target = new File(file, filename);
            } else {
                target = file;
            }
            if (replace) {
                FileUtil.delete(target);
            } else {
                if (target.exists()) {
                    target = getNewFilename(target, 1);
                }
            }

            // 开始写入内容
            FileUtil.copyStream(response.body().byteStream(), target);

            return target;
        } else {
            throw new CilantroStatusException(response.status(), response.reason(), response.request());
        }
    }

    private File getNewFilename(File file, int index) {
        String mainName = FileUtil.mainName(file);
        String fileExt = FileUtil.getFileExt(file);
        if (fileExt == null) {
            fileExt = StringUtil.EMPTY_STRING;
        } else {
            fileExt += StringUtil.DOT;
        }
        File target = new File(file.getParentFile(), mainName + "(" + index + ")" + fileExt);
        if (target.exists()) {
            return getNewFilename(file, index + 1);
        }
        return target;
    }

}
