package com.bob.common.exception.file;

/**
 * @author: zhang bob
 * @date: 2021-06-17 16:00
 * @description: 文件名长度异常类
 */
public class FileNameLengthLimitExceededException extends FileException {
    private static final long serialVersionUID = 1L;

    public FileNameLengthLimitExceededException(int defaultFileNameLength) {
        super("upload.filename.exceed.length", new Object[]{defaultFileNameLength});
    }
}
