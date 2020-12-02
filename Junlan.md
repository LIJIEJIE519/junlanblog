# Junlan

# 开始步骤

## 1. Swagger



## 2. JWT

## 3. Shiro

## 4. yaml配置

## 5. ApiResult

### 5.1 APICode

- 枚举类

  ```java
  package com.junlan.common.result;
  
  /**
   *
   * REST API 响应码
   *
   */
  public enum ApiCode {
  
      /**
       * 操作成功
       **/
      SUCCESS(200, "操作成功"),
      /**
       * 非法访问
       **/
      UNAUTHORIZED(401, "非法访问"),
      /**
       * 没有权限
       **/
      NOT_PERMISSION(403, "没有权限"),
      /**
       * 你请求的资源不存在
       **/
      NOT_FOUND(404, "你请求的资源不存在"),
      /**
       * 操作失败
       **/
      FAIL(500, "操作失败"),
      /**
       * 登录失败
       **/
      LOGIN_EXCEPTION(4000, "登录失败"),
  
      ;
  
      // 枚举实例具有属性，所以必须提供构造器与域
      private final int code;
      private final String message;
  
      ApiCode(final int code, final String message) {
          this.code = code;
          this.message = message;
      }
  
      // 通过code获取message
      public static ApiCode getApiCode(int code) {
          // 获取所有实例
          ApiCode[] ecase = ApiCode.values();
          for (ApiCode ec : ecase) {
              if (ec.getCode() == code) {
                  return ec;
              }
          }
          return SUCCESS;
      }
  
      public int getCode() {
          return code;
      }
  
      public String getMessage() {
          return message;
      }
  
  }
  ```

### 5.2. ApiResult

- 封装类

  ```java
  package com.junlan.common.result;
  
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
  ```
  
  


## 6. 登录完善

<img src="Junlan.assets/image-20201202194654155.png" alt="image-20201202194654155" style="zoom:50%;" />

### 概念

#### entity

- entity 里的每一个字段，与数据库相对应

#### VO

- value object 值对象 / view object 表现层对象
- 主要对应页面显示【HTML】的数据对象。
- 可以和表对应，也可以不，这根据业务的需要。



## 7. 异常处理

```java
/**
 * @Author LJ
 * @Date 2020/12/2
 * msg
 */

@RestControllerAdvice       // 异常处理类
public class MyExceptionHandler {
    /**
     * 登录授权异常处理
     * @param exception
     * @return
     */
    @ExceptionHandler(value = AuthenticationException.class)	// 捕捉异常
    @ResponseStatus(HttpStatus.OK)      //  标记方法或异常类，用应返回的状态
    public ApiResult<String> authenticationExceptionHandler(AuthenticationException exception) {

        return ApiResult.fail(ApiCode.AUTHENTICATION_EXCEPTION, exception.getMessage());
    }
}
```

