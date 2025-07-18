package com.baicha.mywallpaper.service;

import com.baicha.mywallpaper.entity.Users;
import com.baicha.mywallpaper.model.Respons;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
* @author 12793
* @description 针对表【users】的数据库操作Service
* @createDate 2025-07-14 22:20:42
*/
public interface UsersService extends IService<Users> {

    Respons register(String username,
                     String password,
                     String userEmail,
                     String captchaCode,
                     HttpServletRequest request);

    Respons login(
            String username,
            String password,
            String captchaCode,
            HttpServletRequest request
    );
}
