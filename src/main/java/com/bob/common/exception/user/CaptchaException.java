package com.bob.common.exception.user;


/**
 * @author: zhang bob
 * @date: 2021-05-18 14:16
 * @description: 验证码异常类
 */
public class CaptchaException extends UserException {
    private static final long serialVersionUID = 1L;

    public CaptchaException () {
        super("user.jcaptcha.error", null);
    }
}
