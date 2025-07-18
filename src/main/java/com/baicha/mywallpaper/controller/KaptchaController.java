package com.baicha.mywallpaper.controller;

import cn.hutool.core.util.IdUtil;
import com.baicha.mywallpaper.model.Respons;
import com.google.code.kaptcha.Producer;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/wallpaper/kaptcha")
public class KaptchaController {

    @Autowired
    private Producer kaptchaProducer;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/get")
    public Respons getKaptcha(HttpServletResponse response) throws IOException {
        // 生成验证码文本
        String capText = kaptchaProducer.createText();
        // 将验证码存入redis并设置过期时间
        String redisKey = IdUtil.fastSimpleUUID();
        redisTemplate.opsForValue().set(redisKey, capText, 5, TimeUnit.MINUTES);
        // 把redis的key唯一表示写入session
//        session.setAttribute("redisKey", redisKey);
        // 生成图片
        BufferedImage bi = kaptchaProducer.createImage(capText);
        // 设置响应类型为图片
        response.setContentType("image/jpeg");
        response.setHeader("redisKey", redisKey);
        // 输出图片流
        ImageIO.write(bi, "jpg", response.getOutputStream());
        return Respons.ok();
    }
}
