package com.jdw.sys.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.hc.core5.http.HttpStatus;

import java.io.Serializable;

/**
 * 接口返回数据格式
 *
 * @author scott
 * @email jeecgos@163.com
 * @date 2019年1月19日
 */
@Data
@Schema(name = "接口返回对象", description = "接口返回对象")
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 成功标志
     */
    @Schema(name = "成功标志")
    private boolean success = true;

    /**
     * 返回处理消息
     */
    @Schema(name = "返回处理消息")
    private String message = "";

    /**
     * 返回代码
     */
    @Schema(name = "返回代码")
    private Integer code = 0;

    /**
     * 返回数据对象 data
     */
    @Schema(name = "返回数据对象")
    private T result;

    /**
     * 时间戳
     */
    @Schema(name = "时间戳")
    private long timestamp = System.currentTimeMillis();

    public Result() {
    }

    /**
     * 兼容VUE3版token失效不跳转登录页面
     *
     * @param code
     * @param message
     */
    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static <T> Result<T> ok() {
        Result<T> r = new Result<T>();
        r.setSuccess(true);
        r.setCode(HttpStatus.SC_OK);
        return r;
    }

    public static <T> Result<T> ok(String msg) {
        Result<T> r = new Result<T>();
        r.setSuccess(true);
        r.setCode(HttpStatus.SC_OK);
        //Result OK(String msg)方法会造成兼容性问题 issues/I4IP3D
        r.setResult((T) msg);
        r.setMessage(msg);
        return r;
    }

    public static <T> Result<T> ok(T data) {
        Result<T> r = new Result<T>();
        r.setSuccess(true);
        r.setCode(HttpStatus.SC_OK);
        r.setResult(data);
        return r;
    }

    public static <T> Result<T> error(String msg, T data) {
        Result<T> r = new Result<T>();
        r.setSuccess(false);
        r.setCode(HttpStatus.SC_SERVER_ERROR);
        r.setMessage(msg);
        r.setResult(data);
        return r;
    }

    public static <T> Result<T> error(int code, String msg) {
        Result<T> r = new Result<T>();
        r.setCode(code);
        r.setMessage(msg);
        r.setSuccess(false);
        return r;
    }

    public Result<T> success(String message) {
        this.message = message;
        this.code = HttpStatus.SC_OK;
        this.success = true;
        return this;
    }

    public Result<T> error500(String message) {
        this.message = message;
        this.code = HttpStatus.SC_SERVER_ERROR;
        this.success = false;
        return this;
    }

}