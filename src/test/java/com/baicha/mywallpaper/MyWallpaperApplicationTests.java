package com.baicha.mywallpaper;

import com.baicha.mywallpaper.mapper.UsersMapper;
import com.baicha.mywallpaper.tools.JwtTool;
import com.baicha.mywallpaper.tools.MyBcrypt;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class MyWallpaperApplicationTests {

    @Autowired
    private MyBcrypt myBcrypt;

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private JwtTool jwtTool;

    @Test
    void logTest() {
        log.info("这是一个测试日志！");
    }

    @Test
    void hash() {
        String s = myBcrypt.hashPassword("123456");
        System.out.println(s);
        System.out.println(myBcrypt.checkPassword("123456", s));
    }

//    @Test
//    void tokenTest() {
//        LambdaQueryWrapper<Users> queryWrapper = new LambdaQueryWrapper<>();
//        LambdaQueryWrapper<Users> eq = queryWrapper.eq(Users::getUserId, "300eac25-2673-4857-95b6-6790d1a0a1ff");
//        Users users = usersMapper.selectOne(eq);
//        String s = jwtTool.generateToken(users);
//        String b = jwtTool.validateToken(s);
//        System.out.println(b);
//    }
}
