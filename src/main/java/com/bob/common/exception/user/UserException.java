package com.bob.common.exception.user;

import com.bob.common.exception.base.BaseException;

/**
 * @author: zhang bob
 * @date: 2021-05-18 14:19
 * @description: 用户异常类
 */
public class UserException extends BaseException {

    private static final long serialVersionUID = 1L;

    public UserException(String code, Object[] args) {
        super("user", code, args, null);
    }
}
