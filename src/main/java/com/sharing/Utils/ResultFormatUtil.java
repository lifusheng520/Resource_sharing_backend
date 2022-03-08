package com.sharing.Utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

/**
 * 结果格式化工具类
 *
 * @author 李福生
 * @date 2022-2-25
 * @time 下午 12:45
 */

public class ResultFormatUtil implements Serializable {

    /**
     * 格式化响应状态码和对象
     *
     * @param code
     * @param data
     * @return
     */
    public static ApiResult format(ResponseCode code, Object data) {
        ApiResult result = ApiResult.build(code, data);
        return result;
    }

    public static ApiResult format(int code, String message, Object data) {
        return ApiResult.build(code, message, data);
    }

    /**
     * 将响应结果和结果对象data转换为json字符串反馈给前端
     *
     * @param request
     * @param response
     * @param data
     * @throws IOException
     */
    public static void writeJSON(HttpServletRequest request, HttpServletResponse response, Object data) throws IOException {
        //设置响应头，否则页面获取不到正常的JSON数据集
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "*");

        // 允许跨域请求
        response.setHeader("Access-Control-Allow-Method", "POST,GET");

        // 利用Jackson提供的objectmapper将对象转化为json字符串并反馈给前端
        PrintWriter out = response.getWriter();
        out.write(new ObjectMapper().writeValueAsString(data));
        out.flush();
        out.close();
    }

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
