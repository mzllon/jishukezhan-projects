package com.jishukezhan.core.io;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class FileUtilTest {

    @Test
    public void testGetTempDirectory() {
    }

    @Test
    public void testGetTempDirectoryPath() {
    }

    @Test
    public void testGetUserDirectory() {
    }

    @Test
    public void testGetUserDirectoryPath() {
    }

    @Test
    public void testReadLines() {
    }

    @Test
    public void testReadUtf8Lines() {
    }

    @Test
    public void testTestReadLines() {
    }

    @Test
    public void testReadString() {
    }

    @Test
    public void testReadUtf8String() {
    }

    @Test
    public void testTestReadString() {
    }

    @Test
    public void testTestReadString1() {
    }

    @Test
    public void testReadBytes() {
    }

    @Test
    public void testCopyFile() {
    }

    @Test
    public void testTestCopyFile() {
    }

    @Test
    public void testTestCopyFile1() {
    }

    @Test
    public void testCopyFileToDirectory() {
    }

    @Test
    public void testCopyStream() {
    }

    @Test
    public void testForceMakeDir() {
    }

    @Test
    public void testTestForceMakeDir() {
    }

    @Test
    public void testTestForceMakeDir1() {
    }

    @Test
    public void testCopyDirectory() {
    }

    @Test
    public void testTestCopyDirectory() {
    }

    @Test
    public void testTestCopyDirectory1() {
    }

    @Test
    public void testMoveFile() {
    }

    @Test
    public void testMoveDirectory() {
    }

    @Test
    public void testTestMoveDirectory() {
    }

    @Test
    public void testDelete() {
    }

    @Test
    public void testTestDelete() {
    }

    @Test
    public void testCleanDirectory() {
    }

    @Test
    public void testOpenFileInputStream() {
    }

    @Test
    public void testOpenFileOutputStream() {
    }

    @Test
    public void testGetBufferedOutputStream() {
    }

    @Test
    public void testTestGetBufferedOutputStream() {
    }

    @Test
    public void testMd5Hex() {
    }

    @Test
    public void testSha1Hex() {
    }

    @Test
    public void testPathEquals() {
    }

    @Test
    public void testFormatSize() {
    }

    @Test
    public void testTestFormatSize() {
    }

    @Test
    public void testFormatSizeAsString() {
    }

    @Test
    public void testIsWindows() {
    }

    @Test
    public void testIsFileSeparator() {
    }

    @Test
    public void testTouch() {
    }

    @Test
    public void testTestTouch() {
    }

    @Test
    public void testGetFilename() {
    }

    @Test
    public void testMainName() {
    }

    @Test
    public void testTestMainName() {
    }

    @Test
    public void testGetFileExt() {
    }

    @Test
    public void testTestGetFileExt() {
    }

    @Test
    public void testGetRealBinExt() {
    }

    @Test
    public void testGetRelativePath() {
    }

    @Test
    public void testGetRelativePath2() {
        String path = "~/aa/bb/ccc", fromPath = "~/aa";
        assertEquals(FileUtil.getRelativePath(path, fromPath), "bb/ccc");
        fromPath = "~/aa/bb/ccc";
        assertEquals(FileUtil.getRelativePath(path, fromPath), "");
    }

    @Test
    public void testNormalize() {
        String path = "/foo//";
        assertEquals(FileUtil.normalize(path), "/foo/");
        path = "/foo/./";
        assertEquals(FileUtil.normalize(path), "/foo/");
        path = "/foo/../bar";
        assertEquals(FileUtil.normalize(path), "/bar");
        path = "/foo/../bar/";
        assertEquals(FileUtil.normalize(path), "/bar/");
        path = "/foo/../bar/../baz";
        assertEquals(FileUtil.normalize(path), "/baz");
        path = "//foo//./bar ";
        assertEquals(FileUtil.normalize(path), "/foo/bar");
        path = "/../";
        assertEquals(FileUtil.normalize(path), "/");
        path = "../foo";
        assertEquals(FileUtil.normalize(path), "");
        path = "foo/bar/..";
        assertEquals(FileUtil.normalize(path), "foo");
        path = "foo/../../bar";
        assertEquals(FileUtil.normalize(path), "");
        path = "foo/../bar";
        assertEquals(FileUtil.normalize(path), "bar");
        path = "//server/foo/../bar";
        assertEquals(FileUtil.normalize(path), "/server/bar");
        path = "//server/../bar";
        assertEquals(FileUtil.normalize(path), "/bar");
        path = "C:\\foo\\..\\bar";
        assertEquals(FileUtil.normalize(path), "C:/bar");
        path = "C:\\..\\bar";
        assertEquals(FileUtil.normalize(path), "C:/");
        path = "~/foo/../bar/";
        assertEquals(FileUtil.normalize(path), "~/bar/");
        path = "~/../bar";
        assertEquals(FileUtil.normalize(path), "bar");

    }

}
