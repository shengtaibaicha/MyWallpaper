package com.baicha.mywallpaper.tools;

import cn.hutool.core.util.IdUtil;
import com.google.code.kaptcha.Producer;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class Kaptcha {

    @Autowired
    private Producer kaptchaProducer;

    @Autowired
    private RedisTemplate redisTemplate;

    public String createKaptcha(HttpServletResponse response) throws Exception {
        // 生成验证码文本
        String capText = kaptchaProducer.createText();
        // 将验证码存入redis并设置过期时间
        String redisKey = IdUtil.fastSimpleUUID();
        redisTemplate.opsForValue().set(redisKey, capText, 5, TimeUnit.MINUTES);
        // 生成图片
        BufferedImage bi = kaptchaProducer.createImage(capText);
        // 把唯一的redisKey设置到响应头
        response.setHeader("redisKey", redisKey);
        // 将图片转换为Base64（使用Java标准库）
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bi, "jpg", baos);
        byte[] imageBytes = baos.toByteArray();

        // 使用Java标准库的Base64编码器
        String base64Str = Base64.getEncoder().encodeToString(imageBytes);
        String base64Image = "data:image/jpeg;base64," + base64Str;
        return  base64Image;
    }
}
