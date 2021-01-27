package com.bob.web.system.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;

/**
 * @author: zhang bob
 * @date: 2021-01-26 14:08
 * @description: 验证码管理类
 */
@Controller
@RequestMapping(("/captcha"))
public class SystemCaptchaController {

    @Resource(name = "captchaProducer")
    private Producer captchaProducer;
    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    /**
     * 生成图片验证码
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/captchaImage")
    public ModelAndView getKaptchaImage(HttpServletRequest request, HttpServletResponse response) {

        try(ServletOutputStream out = response.getOutputStream();) {
            HttpSession session = request.getSession();
            response.setDateHeader("Expires", 0);
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            response.addHeader("Cache-Control", "post-check=0, pre-check=0");
            response.setHeader("Pragma", "no-cache");
            response.setContentType("image/jpeg");
            //获取验证码类型
            String type = request.getParameter("type");
            String capStr = null;
            String code = null;
            BufferedImage buff = null;
            //判断类型（math：计算，char：字符）
            if ("math".equals(type)) {
                String capText = captchaProducerMath.createText();
                capStr = capText.substring(0, capText.lastIndexOf("@"));
                code = capText.substring(capText.lastIndexOf("@") + 1);
                buff = captchaProducerMath.createImage(capStr);
            } else if ("char".equals(type)) {
                capStr = code = captchaProducer.createText();
                buff = captchaProducer.createImage(capStr);
            }

            session.setAttribute(Constants.KAPTCHA_SESSION_KEY, code);
            ImageIO.write(buff, "jpg", out);
            out.flush();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}












