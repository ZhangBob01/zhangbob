package com.bob.common.exception.user;

/**
 * @author: zhang bob
 * @date: 2021-05-18 14:29
 * @description: 用户不存在异常类
 */
public class UserNotExistsException extends UserException {

    private static final long serialVersionUID = 1L;

    public UserNotExistsException() {
        super("user.not.exists", null);
    }
}
