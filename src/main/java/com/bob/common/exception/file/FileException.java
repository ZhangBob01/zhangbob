package com.bob.common.exception.file;

import com.bob.common.exception.base.BaseException;

/**
 * @author: zhang bob
 * @date: 2021-06-17 15:54
 * @description: 文件异常类
 */
public class FileException extends BaseException {
    private static final long serialVersionUID = 1L;

    public FileException(String code, Object[] args) {
        super("file", code, args, null);
    }
}
