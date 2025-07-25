package com.baicha.mywallpaper.model.dto;

import lombok.Data;

@Data
    public class UserRegisterDto {
        private String userName;
        private String userPassword;
        private String userEmail;
        private String captchaCode;
    }