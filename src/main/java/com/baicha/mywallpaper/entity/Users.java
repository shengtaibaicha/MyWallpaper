package com.baicha.mywallpaper.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 
 * @TableName users
 */
@TableName(value ="users")
@Data
public class Users {
    /**
     * 
     */
    private String userId;

    /**
     * 
     */
    private String userName;

    /**
     * 
     */
    private String userEmail;

    /**
     * 
     */
    private String userAvatar;

    /**
     * 
     */
    private String userPassword;
}