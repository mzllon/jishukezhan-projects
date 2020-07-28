package com.jishukezhan.core.utils;

import com.jishukezhan.annotation.NonNull;
import com.jishukezhan.annotation.Nullable;
import com.jishukezhan.core.exceptions.ZipException;
import com.jishukezhan.core.io.FileUtil;
import com.jishukezhan.core.io.IOUtil;
import com.jishukezhan.core.lang.*;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ZipUtil {

    /**
     * 打包到当前路径，使用默认编码
     *
     * @param srcPath 源文件路径
     * @return 打包好的压缩文件
     */
    public static File zip(@NonNull String srcPath) {
        return zip(srcPath, CharsetUtil.defaultCharset());
    }

    /**
     * 打包到当前路径，使用指定编码编码
     *
     * @param srcPath 源文件路径
     * @param charset 指定编码，可空
     * @return 打包好的压缩文件
     */
    public static File zip(@NonNull String srcPath, @Nullable Charset charset) {
        return zip(new File(Preconditions.requireNotEmpty(srcPath, "srcPath is null or empty")), charset);
    }

    /**
     * 打包到当前路径，使用默认编码
     *
     * @param srcFile 源文件路径
     * @return 打包好的压缩文件
     */
    public static File zip(@NonNull File srcFile) {
        return zip(srcFile, CharsetUtil.defaultCharset());
    }

    /**
     * 打包到当前路径，使用指定编码编码
     *
     * @param srcFile 源文件路径
     * @param charset 指定编码，可空
     * @return 打包好的压缩文件
     */
    public static File zip(@NonNull File srcFile, @Nullable Charset charset) {
        File zipFile = new File(srcFile.getParentFile(), FileUtil.mainName(srcFile) + ".zip");
        zip(zipFile, charset, false, srcFile);
        return zipFile;
    }

    /**
     * 打包到当前路径，使用默认编码
     *
     * @param zipFile  生成的Zip文件，包括文件名。注意：zipPath不能是srcPath路径下的子文件夹
     * @param srcFiles 源文件路径
     */
    public static void zip(@NonNull File zipFile, @NonNull File... srcFiles) {
        zip(zipFile, CharsetUtil.defaultCharset(), srcFiles);
    }

    /**
     * 打包到当前路径，使用指定编码
     *
     * @param zipFile  生成的Zip文件，包括文件名。注意：zipPath不能是srcPath路径下的子文件夹
     * @param charset  指定编码，可空
     * @param srcFiles 源文件路径
     */
    public static void zip(@NonNull File zipFile, @Nullable Charset charset, @NonNull File... srcFiles) {
        zip(zipFile, charset, false, srcFiles);
    }

    /**
     * 对文件或文件目录进行压缩
     *
     * @param zipFile    生成的Zip文件，包括文件名。注意：zipFile不能是srcFile(s)路径下的子文件夹
     * @param charset    编码
     * @param withSrcDir 是否包含被打包目录，只针对压缩目录有效。若为false，则只压缩目录下的文件或目录，为true则将本目录也压缩
     * @param srcFiles   要压缩的源文件或目录。如果压缩一个文件，则为该文件的全路径；如果压缩一个目录，则为该目录的顶层目录路径
     */
    public static void zip(@NonNull File zipFile, @Nullable Charset charset, boolean withSrcDir, @NonNull File... srcFiles) {
        zip(zipFile, charset, withSrcDir, null, srcFiles);
    }

    /**
     * 对文件或文件目录进行压缩
     *
     * @param zipFile    生成的Zip文件，包括文件名。注意：zipFile不能是srcFile(s)路径下的子文件夹
     * @param charset    编码
     * @param withSrcDir 是否包含被打包目录，只针对压缩目录有效。若为false，则只压缩目录下的文件或目录，为true则将本目录也压缩
     * @param filter     文件过滤器，通过实现此接口，自定义要过滤的文件（过滤掉哪些文件或文件夹不加入压缩）
     * @param srcFiles   要压缩的源文件或目录。如果压缩一个文件，则为该文件的全路径；如果压缩一个目录，则为该目录的顶层目录路径
     */
    public static void zip(@NonNull File zipFile, @Nullable Charset charset, boolean withSrcDir,
                           @Nullable FileFilter filter, @NonNull File... srcFiles) {
        validateFiles(zipFile, srcFiles);
        try (ZipOutputStream out = getZipOutputStream(zipFile, charset)) {
            zip(out, charset, withSrcDir, filter, srcFiles);
        } catch (IOException e) {
            throw new ZipException(e);
        }
    }

    /**
     * 对文件或文件目录进行压缩
     *
     * @param out        生成的Zip到的目标流，包括文件名。注意：zipPath不能是srcPath路径下的子文件夹
     * @param charset    编码
     * @param withSrcDir 是否包含被打包目录，只针对压缩目录有效。若为false，则只压缩目录下的文件或目录，为true则将本目录也压缩
     * @param filter     文件过滤器，通过实现此接口，自定义要过滤的文件（过滤掉哪些文件或文件夹不加入压缩）
     * @param srcFiles   要压缩的源文件或目录。如果压缩一个文件，则为该文件的全路径；如果压缩一个目录，则为该目录的顶层目录路径
     */
    public static void zip(OutputStream out, Charset charset, boolean withSrcDir, FileFilter filter, File... srcFiles) {
        zip(getZipOutputStream(out, charset), withSrcDir, filter, srcFiles);
    }

    /**
     * 对文件或文件目录进行压缩
     *
     * @param zipOutputStream 生成的Zip到的目标流，不关闭此流
     * @param withSrcDir      是否包含被打包目录，只针对压缩目录有效。若为false，则只压缩目录下的文件或目录，为true则将本目录也压缩
     * @param filter          文件过滤器，通过实现此接口，自定义要过滤的文件（过滤掉哪些文件或文件夹不加入压缩）
     * @param srcFiles        要压缩的源文件或目录。如果压缩一个文件，则为该文件的全路径；如果压缩一个目录，则为该目录的顶层目录路径
     */
    public static void zip(ZipOutputStream zipOutputStream, boolean withSrcDir, FileFilter filter, File... srcFiles) {
        String srcRootDir;
        try {
            for (File srcFile : srcFiles) {
                if (null == srcFile) {
                    continue;
                }
                // 如果只是压缩一个文件，则需要截取该文件的父目录
                srcRootDir = srcFile.getCanonicalPath();
                if (srcFile.isFile() || withSrcDir) {
                    // 若是文件，则将父目录完整路径都截取掉；若设置包含目录，则将上级目录全部截取掉，保留本目录名
                    srcRootDir = srcFile.getCanonicalFile().getParentFile().getCanonicalPath();
                }
                // 调用递归压缩方法进行目录或文件压缩
                zip(srcFile, srcRootDir, zipOutputStream, filter);
                zipOutputStream.flush();
            }
        } catch (IOException e) {
            throw new ZipException(e);
        }
    }

    /**
     * 递归压缩文件夹<br>
     * srcRootDir决定了路径截取的位置，例如：<br>
     * file的路径为d:/a/b/c/d.txt，srcRootDir为d:/a/b，则压缩后的文件与目录为结构为c/d.txt
     *
     * @param out        压缩文件存储对象
     * @param srcRootDir 被压缩的文件夹根目录
     * @param file       当前递归压缩的文件或目录对象
     * @param filter     文件过滤器，通过实现此接口，自定义要过滤的文件（过滤掉哪些文件或文件夹不加入压缩）
     */
    private static void zip(File file, String srcRootDir, ZipOutputStream out, FileFilter filter) {
        if (null == file || (null != filter && !filter.accept(file))) {
            return;
        }

        // 获取文件相对于压缩文件夹根目录的子路径
        final String subPath = FileUtil.getRelativePath(file, srcRootDir);
        if (file.isDirectory()) {// 如果是目录，则压缩压缩目录中的文件或子目录
            final File[] files = file.listFiles();
            if (ArrayUtil.isEmpty(files) && StringUtil.hasLength(subPath)) {
                // 加入目录，只有空目录时才加入目录，非空时会在创建文件时自动添加父级目录
                addDir(subPath, out);
            }
            // 压缩目录下的子文件或目录
            //noinspection ConstantConditions
            for (File childFile : files) {
                zip(childFile, srcRootDir, out, filter);
            }
        } else {// 如果是文件或其它符号，则直接压缩该文件
            addFile(file, subPath, out);
        }
    }

    /**
     * 在压缩包中新建目录
     *
     * @param path 压缩的路径
     * @param out  压缩文件存储对象
     */
    private static void addDir(String path, ZipOutputStream out) {
        path = StringUtil.addSuffixIfNot(path, StringUtil.SLASH);
        try {
            out.putNextEntry(new ZipEntry(path));
        } catch (IOException e) {
            throw new ZipException(e);
        } finally {
            closeEntry(out);
        }
    }

    /**
     * 添加文件到压缩包
     *
     * @param file 需要压缩的文件
     * @param path 在压缩文件中的路径
     * @param out  压缩文件存储对象
     */
    private static void addFile(File file, String path, ZipOutputStream out) {
        addFile(FileUtil.openFileInputStream(file), path, out);
    }

    /**
     * 添加文件流到压缩包，添加后关闭流
     *
     * @param in   需要压缩的输入流
     * @param path 压缩的路径
     * @param out  压缩文件存储对象
     */
    private static void addFile(InputStream in, String path, ZipOutputStream out) {
        if (null == in) {
            return;
        }
        try {
            out.putNextEntry(new ZipEntry(path));
            IOUtil.copy(in, out);
        } catch (IOException e) {
            throw new ZipException(e);
        } finally {
            IOUtil.closeQuietly(in);
            closeEntry(out);
        }
    }

    /**
     * 关闭当前Entry，继续下一个Entry
     *
     * @param out ZipOutputStream
     */
    private static void closeEntry(ZipOutputStream out) {
        try {
            out.closeEntry();
        } catch (IOException e) {
            // ignore
        }
    }

    /**
     * 获得 {@link ZipOutputStream}
     *
     * @param zipFile 压缩文件
     * @param charset 编码
     * @return {@link ZipOutputStream}
     */
    private static ZipOutputStream getZipOutputStream(File zipFile, Charset charset) {
        return getZipOutputStream(FileUtil.openFileOutputStream(zipFile), charset);
    }

    /**
     * 获得 {@link ZipOutputStream}
     *
     * @param out     压缩文件流
     * @param charset 编码
     * @return {@link ZipOutputStream}
     */
    private static ZipOutputStream getZipOutputStream(@NonNull OutputStream out, @Nullable Charset charset) {
        if (out instanceof ZipOutputStream) {
            return (ZipOutputStream) out;
        }
        return new ZipOutputStream(out, charset == null ? CharsetUtil.defaultCharset() : charset);
    }

    /**
     * 判断压缩文件保存的路径是否为源文件路径的子文件夹，如果是，则抛出异常（防止无限递归压缩的发生）
     *
     * @param zipFile  压缩后的产生的文件路径
     * @param srcFiles 被压缩的文件或目录
     */
    private static void validateFiles(File zipFile, File... srcFiles) {
        if (zipFile.isDirectory()) {
            throw new ZipException("Zip file '" + zipFile.getAbsoluteFile() + "' must not be a directory !");
        }

        for (File srcFile : srcFiles) {
            if (null == srcFile) {
                continue;
            }
            if (!srcFile.exists()) {
                throw new ZipException("File '" + srcFile.getAbsolutePath() + "' not exist!");
            }

            try {
                final File parentFile = zipFile.getCanonicalFile().getParentFile();
                // 压缩文件不能位于被压缩的目录内
                if (srcFile.isDirectory() && parentFile.getCanonicalPath().contains(srcFile.getCanonicalPath())) {
                    throw new ZipException("Zip file path '" + zipFile.getCanonicalPath() + "' must not be the child directory of '" + srcFile.getCanonicalPath() + "'");
                }
            } catch (IOException e) {
                throw new ZipException(e);
            }
        }
    }

    /**
     * 解压到文件名相同的目录中，默认编码
     *
     * @param zipFilePath 压缩文件路径
     * @return 解压的目录
     */
    public static File unzip(String zipFilePath) {
        return unzip(zipFilePath, CharsetUtil.UTF_8);
    }

    /**
     * 解压到文件名相同的目录中
     *
     * @param zipFilePath 压缩文件路径
     * @param charset     编码
     * @return 解压的目录
     */
    public static File unzip(String zipFilePath, Charset charset) {
        return unzip(new File(zipFilePath), charset);
    }

    /**
     * 解压到文件名相同的目录中，使用UTF-8编码
     *
     * @param zipFile 压缩文件
     * @return 解压的目录
     */
    public static File unzip(File zipFile) {
        return unzip(zipFile, CharsetUtil.UTF_8);
    }

    /**
     * 解压到文件名相同的目录中
     *
     * @param zipFile 压缩文件
     * @param charset 编码
     * @return 解压的目录
     */
    public static File unzip(File zipFile, Charset charset) {
        return unzip(zipFile, new File(zipFile.getParentFile(), FileUtil.mainName(zipFile)), charset);
    }

    /**
     * 解压
     *
     * @param zipFile   zip文件
     * @param targetDir 解压到的目录
     * @param charset   编码
     * @return 解压的目录
     */
    public static File unzip(File zipFile, File targetDir, Charset charset) {
        ZipFile zip;
        try {
            zip = new ZipFile(zipFile, charset);
        } catch (IOException e) {
            throw new ZipException(e);
        }
        return unzip(zip, targetDir);
    }

    /**
     * 解压
     *
     * @param zipFile zip文件，附带编码信息，使用完毕自动关闭
     * @param outFile 解压到的目录
     * @return 解压的目录
     */
    // @SuppressWarnings("unchecked")
    public static File unzip(ZipFile zipFile, File outFile) throws ZipException {
        try {
            Enumeration<? extends ZipEntry> em = zipFile.entries();
            ZipEntry zipEntry;
            File outItemFile;
            while (em.hasMoreElements()) {
                zipEntry = em.nextElement();
                // FileUtil.file会检查slip漏洞，漏洞说明见http://blog.nsfocus.net/zip-slip-2/
                outItemFile = buildFile(outFile, zipEntry.getName());
                if (zipEntry.isDirectory()) {
                    // 创建对应目录
                    //noinspection ResultOfMethodCallIgnored
                    outItemFile.mkdirs();
                } else {
                    // 写出文件
                    write(zipFile, zipEntry, outItemFile);
                }
            }
        } catch (IOException e) {
            throw new ZipException(e);
        } finally {
            IOUtil.closeQuietly(zipFile);
        }
        return outFile;
    }

    /**
     * 根据压缩包中的路径构建目录结构，在Win下直接构建，在Linux下拆分路径单独构建
     *
     * @param outFile  最外部路径
     * @param fileName 文件名，可以包含路径
     * @return 文件或目录
     */
    private static File buildFile(File outFile, String fileName) {
        if (!FileUtil.isWindows()
                // 检查文件名中是否包含"/"，不考虑以"/"结尾的情况
                && fileName.lastIndexOf(CharUtil.SLASH, fileName.length() - 2) > 0) {
            // 在Linux下多层目录创建存在问题，/会被当成文件名的一部分，此处做处理
            // 使用/拆分路径（zip中无\），级联创建父目录
            final String[] pathParts = StringUtil.split(fileName, '/', true, false);
            final int lastPartIndex = pathParts.length - 1;//目录个数
            for (int i = 0; i < lastPartIndex; i++) {
                //由于路径拆分，slip不检查，在最后一步检查
                outFile = new File(outFile, pathParts[i]);
            }
            //noinspection ResultOfMethodCallIgnored
            outFile.mkdirs();
            // 最后一个部分如果非空，作为文件名
            fileName = pathParts[lastPartIndex];
        }
        File targetFile = new File(outFile, fileName);
        FileUtil.checkSlip(outFile, targetFile);
        return targetFile;
    }

    /**
     * 从Zip中读取文件流并写出到文件
     *
     * @param zipFile     Zip文件
     * @param zipEntry    zip文件中的子文件
     * @param outItemFile 输出到的文件
     * @throws IOException IO异常
     */
    private static void write(ZipFile zipFile, ZipEntry zipEntry, File outItemFile) throws IOException {
        InputStream in = null;
        try {
            in = zipFile.getInputStream(zipEntry);
            FileUtil.copyStream(in, outItemFile);
        } finally {
            IOUtil.closeQuietly(in);
        }
    }

}
