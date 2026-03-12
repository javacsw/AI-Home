package org.example.backend.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;

import jakarta.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

/**
 * @author shenxianfang
 * @Description:ReqJsonUtils
 * @date 2025/5/26
 */

public class ReqJsonUtils {

    /**
     * 解析JSON请求体并映射到指定类
     *
     * @param request     HTTP请求
     * @param targetClass 目标类Class对象
     * @param <T>        泛型类型参数
     * @return           映射后的目标类对象
     * @throws IOException 如果读取请求或解析JSON失败
     */
    public static <T> T parseJsonRequest(HttpServletRequest request, Class<T> targetClass) throws IOException {
        String jsonBody = readRequestBody(request);
        return JSON.parseObject(jsonBody, targetClass);
    }

    /**
     * 带配置选项的JSON解析
     *
     * @param request     HTTP请求
     * @param targetClass 目标类Class对象
     * @param features    FastJSON2特性配置
     * @param <T>        泛型类型参数
     * @return           映射后的目标类对象
     * @throws IOException 如果读取请求或解析JSON失败
     */
    public static <T> T parseJsonRequest(HttpServletRequest request, Class<T> targetClass, JSONReader.Feature... features)
            throws IOException {
        String jsonBody = readRequestBody(request);
        return JSON.parseObject(jsonBody, targetClass, features);
    }

    /**
     * 读取请求体内容
     *
     * @param request HTTP请求
     * @return       请求体字符串
     * @throws IOException 如果读取失败
     */
    private static String readRequestBody(HttpServletRequest request) throws IOException {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining());
        }
    }

    /**
     * 安全解析JSON，出错时返回null而不是抛出异常
     *
     * @param request     HTTP请求
     * @param targetClass 目标类Class对象
     * @param <T>        泛型类型参数
     * @return           解析成功返回对象，失败返回null
     */
    public static <T> T parseJsonRequestSafely(HttpServletRequest request, Class<T> targetClass) {
        try {
            return parseJsonRequest(request, targetClass);
        } catch (Exception e) {
            return null;
        }
    }
}
