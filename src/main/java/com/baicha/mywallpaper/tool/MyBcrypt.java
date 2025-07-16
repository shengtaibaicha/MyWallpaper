package com.baicha.mywallpaper.tool;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class MyBcrypt {
    // 加密密码 (自动生成盐)
    public String hashPassword(String plainPassword) {
        // 强度因子12
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
    }

    // 验证密码
    public boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
