package com.baicha.mywallpaper.controller;

import com.baicha.mywallpaper.entity.Users;
import com.baicha.mywallpaper.model.Respons;
import com.baicha.mywallpaper.service.UsersService;
import com.baicha.mywallpaper.tool.RequestContextHolder;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallpaper/user")
public class UserController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/register")
    public Respons register(@RequestParam(value = "userName") String username,
                            @RequestParam(value = "userPassword") String password,
                            @RequestParam(value = "userEmail") String userEmail,
                            @RequestParam(value = "captchaCode")  String captchaCode,
                            HttpSession session) {
        Respons result = usersService.register(username, password, userEmail, captchaCode, session);
        return result;
    }

    @PostMapping("/login")
    public Respons login(@RequestParam(value = "userName") String username,
                         @RequestParam(value = "userPassword") String password,
                         @RequestParam(value = "captchaCode") String captchaCode,
                         HttpSession session){
        Respons result = usersService.login(username, password, captchaCode, session);
        return result;
    }

    @GetMapping("/test")
    public String test(){
        Object userId = RequestContextHolder.get("userId");
        if (userId == null) {
            return "请先登录";
        }
        return  userId.toString();
    }
}
