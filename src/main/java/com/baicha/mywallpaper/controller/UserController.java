package com.baicha.mywallpaper.controller;

import com.baicha.mywallpaper.model.Respons;
import com.baicha.mywallpaper.model.dto.UserLoginDto;
import com.baicha.mywallpaper.model.dto.UserRegisterDto;
import com.baicha.mywallpaper.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallpaper/user")
public class UserController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/register")
    @Operation(summary = "用户注册", description = "接收用户名，密码，邮箱，验证码进行用户注册")
    public Respons register(@RequestBody UserRegisterDto user, HttpServletRequest request) {
        Respons result = usersService.register(user.getUserName(), user.getUserPassword(), user.getUserEmail(), user.getCaptchaCode(),
                request);
        return result;
    }

    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "接收用户名，密码，验证码进行用户登录")
    public Respons login(@RequestBody UserLoginDto user, HttpServletRequest request){
        Respons result = usersService.login(user.getUserName(), user.getUserPassword(), user.getCaptchaCode(), request);
        return result;
    }

}
