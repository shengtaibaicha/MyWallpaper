package com.baicha.mywallpaper.model.dto;

import lombok.Data;

@Data
    public class UserLoginDto {
        private String userName;
        private String userPassword;
        private String captchaCode;
    }
