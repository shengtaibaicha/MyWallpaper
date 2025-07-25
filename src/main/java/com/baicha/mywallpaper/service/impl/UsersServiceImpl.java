package com.baicha.mywallpaper.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baicha.mywallpaper.model.Respons;
import com.baicha.mywallpaper.status.HttpStatus;
import com.baicha.mywallpaper.tools.JwtTool;
import com.baicha.mywallpaper.tools.MyBcrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baicha.mywallpaper.entity.Users;
import com.baicha.mywallpaper.service.UsersService;
import com.baicha.mywallpaper.mapper.UsersMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
* @author 12793
* @description 针对表【users】的数据库操作Service实现
* @createDate 2025-07-14 22:20:42
*/
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users>
    implements UsersService{

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private JwtTool jwtTool;

    @Autowired
    private MyBcrypt  myBcrypt;

    String key;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Respons register(String username, String password, String userEmail, String captchaCode,  HttpServletRequest request) {
        Respons respons = checkCaptchaCode(captchaCode, request);
        if (respons.getCode().equals(HttpStatus.BAD_REQUEST)) {
            return respons;
        }
        // 验证用户名和密码格式是否正确
        if (username ==  null || username.isEmpty()) {
            return Respons.error(HttpStatus.BAD_REQUEST, "用户名格式错误！");
        }
        if (password ==  null || password.isEmpty()) {
            return Respons.error(HttpStatus.BAD_REQUEST, "密码格式错误错误！");
        }
        // 验证用户名是否已经存在
        LambdaQueryWrapper<Users> queryWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<Users> eq = queryWrapper.eq(Users::getUserName, username);
        Users one = usersMapper.selectOne(eq);
        if (one != null) {
            return Respons.error("该用户名已经被注册！");
        }
        // 创建一个用户对象
        Users users = new Users();
        // 生成uuid作为用户id
        users.setUserId(IdUtil.randomUUID());
        users.setUserName(username);
        users.setUserEmail(userEmail);
        // 将用户的密码加密后存储
        users.setUserPassword(myBcrypt.hashPassword(password));
        int insert = usersMapper.insert(users);
        if (insert == 1) {
            redisTemplate.delete(key);
            return Respons.ok();
        }
        return Respons.error("未知错误，请稍后重试！");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Respons login(String usersname, String password, String captchaCode,  HttpServletRequest request) {
        Respons respons = checkCaptchaCode(captchaCode, request);
        if (respons.getCode().equals(HttpStatus.BAD_REQUEST)) {
            return respons;
        }
        // 验证接收到的用户名和密码是否和数据库相同
        if (usersname ==  null || usersname.isEmpty()) {
            return Respons.error("用户名不能为空！");
        }
        if (password ==  null || password.isEmpty()) {
            return Respons.error("密码不能为空！");
        }
        LambdaQueryWrapper<Users> eq = new LambdaQueryWrapper<Users>()
                .eq(Users::getUserName, usersname);
        Users one = usersMapper.selectOne(eq);
        // 对比数据库的密码和前端接收到的密码是否相同
        if (one == null) {
            return Respons.error("用户名还未注册！");
        }
        boolean b = myBcrypt.checkPassword(password, one.getUserPassword());
        if (b) {
            // 验证成功生成Token并返回
            String token = jwtTool.generateToken(one);
            Map<String, String> map = new HashMap<>();
            map.put("token", token);
            redisTemplate.delete(key);
            return Respons.ok(map);
        }
        return Respons.error("用户名或密码错误！");
    }

    public Respons checkCaptchaCode(String captchaCode, HttpServletRequest request) {
        // 验证验证码是否有效
        key = request.getHeader("redisKey");
        if (key == null){
            return Respons.error("请先获取验证码！");
        }
        String s = (String)redisTemplate.opsForValue().get(key);
        if (s == null || s.isEmpty()) {
            return Respons.error("验证码已过期！");
        }
        if (!s.equals(captchaCode)) {
            return Respons.error("验证码错误！");
        }
        return Respons.ok();
    }
}




