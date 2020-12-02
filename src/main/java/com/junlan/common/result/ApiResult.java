package com.junlan.common.result;

//import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * REST API 返回结果
 * REST --> Spring Jackson 自动将结果转换为 json
 */
@Data
@Accessors(chain = true)        // lombok支持链式构造
@Builder                        // builder().xxx.xxx.build()
@AllArgsConstructor
public class ApiResult<T> implements Serializable {
	private static final long serialVersionUID = 8004487252556526569L;

	private static final Logger logger = LoggerFactory.getLogger(ApiResult.class);

	/**
     * 响应码
     */
    private int code;

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 响应时间, 时间格式化器
     */
//    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date time;

    public ApiResult() {
        time  = new Date();
    }


    // ================= 返回结果 ==============================
    public static ApiResult<Boolean> result(boolean flag){
        if (flag){
            return ok();
        }
        return fail();
    }

    public static ApiResult<Boolean> result(ApiCode apiCode){
        return result(apiCode,null);
    }

    public static <T> ApiResult<T> result(ApiCode apiCode, T data){
        return result(apiCode,null, data);
    }

    // =============== 总返回器 =====================
    @SuppressWarnings("unchecked")
    public static <T> ApiResult<T> result(ApiCode apiCode, String message, T data){
        boolean success = false;
        if (apiCode.getCode() == ApiCode.SUCCESS.getCode()){
            success = true;
        }
        String apiMessage = apiCode.getMessage();
        if (StringUtils.isNotBlank(apiMessage)){
            message = apiMessage;
        }

        // lombok, 时间初始化, builder().xxx.xxx.build()
        return (ApiResult<T>) ApiResult.builder()
                .code(apiCode.getCode())
                .message(message)
                .data(data)
                .success(success)
                .time(new Date())
                .build();
    }


    // ================ 成功，调用result返回结果 =================
    public static ApiResult<Boolean> ok(){
        return ok(null);
    }

    public static <T> ApiResult<T> ok(T data){
        return result(ApiCode.SUCCESS, data);
    }

    public static <T> ApiResult<T> ok(T data,String message){
        return result(ApiCode.SUCCESS, message, data);
    }

    public static ApiResult<Map<String,Object>> okMap(String key, Object value){
        Map<String, Object> map = new HashMap<>(1);
        map.put(key, value);
        return ok(map);
    }


    // =============== 失败，返回结果 =========================
    public static ApiResult<Boolean> fail(ApiCode apiCode){
        return result(apiCode,null);
    }

    public static ApiResult<String> fail(String message){
        return result(ApiCode.FAIL, message,null);

    }

    public static <T> ApiResult<T> fail(ApiCode apiCode, T data){
        if (ApiCode.SUCCESS == apiCode){
            throw new RuntimeException("失败结果状态码不能为" + ApiCode.SUCCESS.getCode());
        }
        return result(apiCode, data);
    }

    public static  ApiResult<String> fail(Integer errorCode, String message){
        // lombok 支持链式构造-build
        return new ApiResult<String>()
                .setSuccess(false)
                .setCode(errorCode)
                .setMessage(message);
    }

    public static ApiResult<Map<String, Object>> fail(String key, Object value){
        Map<String,Object> map = new HashMap<>(1);
        map.put(key, value);
        return result(ApiCode.FAIL, map);
    }

    public static ApiResult<Boolean> fail() {
        return fail(ApiCode.FAIL);
    }
}