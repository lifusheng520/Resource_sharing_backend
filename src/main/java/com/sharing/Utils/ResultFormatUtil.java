package com.sharing.Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

/**
 * 结果格式化工具类，将数据结果对象封装并格式化为json字符串
 *
 * @author 李福生
 * @date 2022-2-25
 * @time 下午 12:45
 */

public class ResultFormatUtil implements Serializable {

    public static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 格式化响应状态码和对象
     *
     * @param code 状态码枚举对象
     * @param data 数据对象
     * @return 封装的格式化json字符串
     */
    public static String format(ResponseCode code, Object data) {
        ApiResult result = ApiResult.build(code, data);
        String valueAsString = null;
        try {
            valueAsString = ResultFormatUtil.objectMapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return valueAsString;
    }

    public static String format(int code, String message, Object data) {
        ApiResult result = ApiResult.build(code, message, data);
        String valueAsString = null;
        try {
            valueAsString = ResultFormatUtil.objectMapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return valueAsString;
    }

    /**
     * 将响应结果和结果对象data转换为json字符串反馈给前端
     *
     * @param request  请求头
     * @param response 响应头
     * @param data     需要转换的数据对象
     * @throws IOException 中间可能抛出IO异常
     */
    public static void writeJSON(HttpServletRequest request, HttpServletResponse response, Object data) throws IOException {
        //设置响应内容类型
        response.setContentType("application/json;charset=UTF-8");
        // 设置响应头跨域参数，否则页面获取不到正常的JSON数据集
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        // 允许跨域请求
        response.setHeader("Access-Control-Allow-Method", "POST,GET");

        // 利用Jackson提供的objectmapper将对象转化为json字符串并反馈给前端
        PrintWriter out = response.getWriter();
        out.write(new ObjectMapper().writeValueAsString(data));
        out.flush();
        out.close();
    }

    /**
     * 结果创建内部类
     */
    @Data
    @AllArgsConstructor
    public static class ApiResult {
        // 结果状态码    200:成功  233:异常
        private int code;

        // 结果消息
        private String message;

        // 结果的数据
        private Object data;

        public static ApiResult build(ResponseCode code, Object data) {
            return new ApiResult(code.getCode(), code.getMessage(), data);
        }

        public static ApiResult build(int code, String message, Object data) {
            return new ApiResult(code, message, data);
        }
    }

}
