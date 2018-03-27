package com.myuan.user.service;


import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * @author liuwei
 * @date 2018/3/1 18:39
 *  验证码
 */
@Service
public class CodeService {

    @Autowired
    private Producer captchaProducer;

    public void createCode(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();

        response.setDateHeader("Expires", 0);

        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");

        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");

        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");

        // return a jpeg
        response.setContentType("image/jpeg");

        // create the text for the image
        String capText = captchaProducer.createText();

        // store the text in the session
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);

        // create the image with the text
        try {
            BufferedImage bi = captchaProducer.createImage(capText);
            ServletOutputStream out = response.getOutputStream();

            // write the data out

            ImageIO.write(bi, "jpg", out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
