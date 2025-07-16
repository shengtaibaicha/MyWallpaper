package com.baicha.mywallpaper.tool;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class RequestContextHolder {
    // 使用 ThreadLocal 存储请求上下文数据
    private static final ThreadLocal<Map<String, Object>> contextHolder = ThreadLocal.withInitial(HashMap::new);

    // 存储数据
    public static void set(String key, Object value) {
        contextHolder.get().put(key, value);
    }

    // 获取数据
    public static Object get(String key) {
        return contextHolder.get().get(key);
    }

    // 清除数据（重要！避免内存泄漏）
    public static void clear() {
        contextHolder.remove();
    }
}