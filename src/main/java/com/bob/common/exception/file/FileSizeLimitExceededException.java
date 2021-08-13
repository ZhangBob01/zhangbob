package com.bob.common.exception.file;

/**
 * @author: zhang bob
 * @date: 2021-06-17 16:34
 * @description: 文件大小异常类
 */
public class FileSizeLimitExceededException extends FileException {
    private static final long serialVersionUID = 1L;

    public FileSizeLimitExceededException(long defaultMaxSize) {
        super("upload.exceed.maxSize", new Object[]{defaultMaxSize});
    }
}
