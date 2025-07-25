package com.baicha.mywallpaper.controller;

import com.baicha.mywallpaper.model.Respons;
import com.baicha.mywallpaper.tools.Kaptcha;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/wallpaper/kaptcha")
public class KaptchaController {

    @Autowired
    private Kaptcha kaptcha;

    @GetMapping("/get")
    @Operation(summary = "获取验证码图片")
    public Respons getKaptcha(HttpServletResponse response) {
        try {
            kaptcha.createKaptcha(response);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Respons.error("获取验证码失败！");
        }
        return Respons.ok();
    }
}
